package io.github.jeniths006.runtimeguard.platform.windows.etw;

import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.WString;
import com.sun.jna.platform.win32.BaseTSD;
import com.sun.jna.platform.win32.WinNT;
import io.github.jeniths006.runtimeguard.platform.windows.nativeapi.Advapi32DLL;
import io.github.jeniths006.runtimeguard.platform.windows.nativeapi.callback.EventRecordCallback;
import io.github.jeniths006.runtimeguard.platform.windows.nativeapi.structures.EnableTraceParameters;
import io.github.jeniths006.runtimeguard.platform.windows.nativeapi.structures.EventHeader;
import io.github.jeniths006.runtimeguard.platform.windows.nativeapi.structures.EventTraceLogFile;
import io.github.jeniths006.runtimeguard.service.interceptor.ProcessActionListener;
import io.github.jeniths006.runtimeguard.platform.windows.nativeapi.ETWConstants;

import com.sun.jna.platform.win32.Guid;

import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;


public class ETWSession {


    private final BaseTSD.ULONG_PTRByReference sessionHandle= new BaseTSD.ULONG_PTRByReference();
    private final WString sessionName = new WString("RuntimeGuardSession_" + System.currentTimeMillis());
    private final EventTracePropertiesBuilder propertiesBuilder = new EventTracePropertiesBuilder();
    private WinNT.HANDLE traceHandle;
    private EventTraceLogFile logFile;
    private EventRecordCallback callback;
    private Memory loggerNameMemory;
    private Thread traceThread;
    private final Map<EventDescriptorKey, LongAdder> eventCounts = new ConcurrentHashMap<>();


    public synchronized void start(long pid, ProcessActionListener processActionListener) {
        eventCounts.clear();
        ETWSessionProperties sessionProperties = propertiesBuilder.build(sessionName);

        int result = Advapi32DLL.INSTANCE.StartTraceW(
                sessionHandle,
                sessionName,
                sessionProperties.eventTraceProperties
        );

        System.out.println("StartTrace result: " + result);
        System.out.println("Session handle: " + sessionHandle.getValue());

        if (result != 0 && result != 183) {
            throw new RuntimeException(
                    "StartTraceW failed. Error: " + result
            );
        }

        //enableKernelProviders();


        this.logFile = createTraceLogFile(pid, processActionListener);
        traceHandle = Advapi32DLL.INSTANCE.OpenTraceW(logFile);

        System.out.println("OpenTrace handle: " + traceHandle);

        if (traceHandle == null || Pointer.nativeValue(traceHandle.getPointer()) == -1) {
            int error = Native.getLastError();
            throw new RuntimeException("OpenTraceW failed, last error: " + error);
        }

        System.out.println("Trace Handle: " + traceHandle);

        traceThread = new Thread(() -> {
            WinNT.HANDLE[] handles = { traceHandle };

            int result2 = Advapi32DLL.INSTANCE.ProcessTrace(
                    handles,
                    1,
                    Pointer.NULL,
                    Pointer.NULL
            );

            System.out.println("ProcesssTrace exited: " + result2);
        });

        traceThread.setDaemon(true);
        traceThread.start();




        System.out.println("ETW Session started");

    }

    public synchronized void stop() {
        System.out.println("Stopping ETW session...");

        if (traceHandle != null) {
            Advapi32DLL.INSTANCE.CloseTrace(traceHandle);
        }

        if (sessionHandle.getValue() != null) {
            Advapi32DLL.INSTANCE.ControlTraceW(
                    sessionHandle.getValue(),
                    sessionName,
                    null,
                    ETWConstants.EVENT_TRACE_CONTROL_STOP
            );
        }

        if (traceThread != null) {
            try {
                traceThread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        printEventSummary();
    }


    EventTraceLogFile createTraceLogFile(
            long pid,
            ProcessActionListener processActionListener
    ) {
        EventTraceLogFile logFile = new EventTraceLogFile();

        loggerNameMemory = new Memory(
                (sessionName.toString().length() + 1) * 2
        );

        loggerNameMemory.setWideString(
                0,
                sessionName.toString()
        );

        logFile.loggerName = loggerNameMemory;

        logFile.fileProcessModeUnion.setType("ProcessTraceMode");
        logFile.fileProcessModeUnion.ProcessTraceMode =
                ETWConstants.PROCESS_TRACE_MODE_REAL_TIME |
                        ETWConstants.PROCESS_TRACE_MODE_EVENT_RECORD;

        callback = record -> {
            EventHeader header = record.eventHeader;

            if(header.processId != pid) {
                return;
            }

            EventDescriptorKey key = new EventDescriptorKey(
                    formatGuid(header.providerId),
                    Short.toUnsignedInt(header.eventDescriptor.id),
                    Byte.toUnsignedInt(header.eventDescriptor.version),
                    Byte.toUnsignedInt(header.eventDescriptor.opcode),
                    Short.toUnsignedInt(header.eventDescriptor.task),
                    header.eventDescriptor.keyword
            );
            eventCounts.computeIfAbsent(key, ignored -> new LongAdder()).increment();
        };

        /*callback = record -> {
            EventHeader header = record.eventHeader;

            System.out.println("========== ETW ==========");
            System.out.println("Provider : " + header.providerId);
            System.out.println("PID      : " + header.processId);
            System.out.println("Thread   : " + header.threadId);
            System.out.println("Opcode   :" + header.eventDescriptor.opcode);
            System.out.println("Task     :" + header.eventDescriptor.task);
            System.out.println("Level    :" + header.eventDescriptor.level);
            System.out.println("Keyword  :" + header.eventDescriptor.keyword);
        };*/

        logFile.eventRecordCallback = callback;

        logFile.isKernelTrace = 0;
        logFile.context = Pointer.NULL;

        logFile.write();

        return logFile;
    }

    private void printEventSummary() {
        if (eventCounts.isEmpty()) {
            return;
        }

        System.out.println("ETW event summary:");
        eventCounts.entrySet().stream()
                .sorted(Map.Entry.<EventDescriptorKey, LongAdder>comparingByValue(
                        Comparator.comparingLong(LongAdder::sum)).reversed())
                .forEach(entry -> System.out.printf("%d x %s%n", entry.getValue().sum(), entry.getKey()));
    }

    private static String formatGuid(Guid.GUID guid) {
        byte[] data4 = guid.Data4;
        return String.format(
                "%08x-%04x-%04x-%02x%02x-%02x%02x%02x%02x%02x%02x",
                guid.Data1,
                Short.toUnsignedInt(guid.Data2),
                Short.toUnsignedInt(guid.Data3),
                Byte.toUnsignedInt(data4[0]), Byte.toUnsignedInt(data4[1]),
                Byte.toUnsignedInt(data4[2]), Byte.toUnsignedInt(data4[3]),
                Byte.toUnsignedInt(data4[4]), Byte.toUnsignedInt(data4[5]),
                Byte.toUnsignedInt(data4[6]), Byte.toUnsignedInt(data4[7])
        );
    }

    private record EventDescriptorKey(
            String provider,
            int id,
            int version,
            int opcode,
            int task,
            long keyword
    ) {
    }

    private void enableKernelProviders() {

        int result = Advapi32DLL.INSTANCE.EnableTraceEx2(
                sessionHandle.getValue(),
                KernelProviders.SYSTEM_TRACE_PROVIDER,
                ETWConstants.EVENT_CONTROL_CODE_ENABLE_PROVIDER,
                ETWConstants.TRACE_LEVEL_INFORMATION,
                ETWConstants.EVENT_TRACE_FLAG_PROCESS,
                0,
                0,
                null
        );

        if (result != 0) {
            throw new RuntimeException(
                    "EnableTraceEx2 failed: " + result
            );
        }

        System.out.println("Kernel provider enabled");
    }






}
