package io.github.jeniths006.runtimeguard.platform.windows.etw;

import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.WString;
import com.sun.jna.platform.win32.BaseTSD;
import com.sun.jna.platform.win32.WinNT;
import io.github.jeniths006.runtimeguard.model.PolicyDecision;
import io.github.jeniths006.runtimeguard.model.action.ActionType;
import io.github.jeniths006.runtimeguard.model.action.ProcessAction;
import io.github.jeniths006.runtimeguard.platform.windows.etw.decoder.ProcessEvent;
import io.github.jeniths006.runtimeguard.platform.windows.etw.decoder.ProcessEventDecoder;
import io.github.jeniths006.runtimeguard.platform.windows.etw.decoder.ProcessEventType;
import io.github.jeniths006.runtimeguard.platform.windows.nativeapi.Advapi32DLL;
import io.github.jeniths006.runtimeguard.platform.windows.nativeapi.callback.EventRecordCallback;
import io.github.jeniths006.runtimeguard.platform.windows.nativeapi.structures.EnableTraceParameters;
import io.github.jeniths006.runtimeguard.platform.windows.nativeapi.structures.EventHeader;
import io.github.jeniths006.runtimeguard.platform.windows.nativeapi.structures.EventTraceLogFile;
import io.github.jeniths006.runtimeguard.service.interceptor.ProcessActionListener;
import io.github.jeniths006.runtimeguard.platform.windows.nativeapi.ETWConstants;

import com.sun.jna.platform.win32.Guid;

public class ETWSession {

    private static final String NT_KERNEL_PROCESS_PROVIDER_GUID =
            "3d6fa8d0-fe05-11d0-9dda-00c04fd7ba7c";

    private final BaseTSD.ULONG_PTRByReference sessionHandle= new BaseTSD.ULONG_PTRByReference();
    private final WString sessionName = new WString("RuntimeGuardSession_" + System.currentTimeMillis());
    private final EventTracePropertiesBuilder propertiesBuilder = new EventTracePropertiesBuilder();
    private WinNT.HANDLE traceHandle;
    private EventTraceLogFile logFile;
    private EventRecordCallback callback;
    private Memory loggerNameMemory;
    private Thread traceThread;
    private ETWSessionProperties sessionProperties;
    private boolean sessionStarted;

    public synchronized void start(long pid, ProcessActionListener processActionListener) {
        sessionProperties = propertiesBuilder.build(sessionName);

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
        sessionStarted = result == 0;

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

        if (sessionStarted) {
            int result = Advapi32DLL.INSTANCE.ControlTraceW(
                    sessionHandle.getValue(),
                    sessionName,
                    sessionProperties.eventTraceProperties,
                    ETWConstants.EVENT_TRACE_CONTROL_STOP
            );
            System.out.println("ControlTrace stop result: " + result);
            sessionStarted = false;
        }

        if (traceHandle != null) {
            int result = Advapi32DLL.INSTANCE.CloseTrace(traceHandle);
            System.out.println("CloseTrace result: " + result);
            traceHandle = null;
        }

        if (traceThread != null) {
            try {
                traceThread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

    }


    private EventTraceLogFile createTraceLogFile(
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

            if (header.processId != pid ||
                    !NT_KERNEL_PROCESS_PROVIDER_GUID.equalsIgnoreCase(formatGuid(header.providerId))) {
                return;
            }

            int userDataLength = Short.toUnsignedInt(record.userDataLength);
            System.out.println("Event ID       : " + Short.toUnsignedInt(header.eventDescriptor.id));
            System.out.println("Opcode         : " + Byte.toUnsignedInt(header.eventDescriptor.opcode));
            System.out.println("Version        : " + Byte.toUnsignedInt(header.eventDescriptor.version));
            System.out.println("userDataLength : " + userDataLength);

            /*if (userDataLength > 0 && record.userData != null &&
                    Pointer.nativeValue(record.userData) != 0) {
                byte[] userData = record.userData.getByteArray(0, Math.min(userDataLength, 64));
                for (int offset = 0; offset < userData.length; offset += 16) {
                    StringBuilder line = new StringBuilder(String.format("%04x: ", offset));
                    for (int index = offset; index < Math.min(offset + 16, userData.length); index++) {
                        line.append(String.format("%02x ", Byte.toUnsignedInt(userData[index])));
                    }
                    System.out.println(line);
                }
            }*/
            ProcessEventDecoder decoder = new ProcessEventDecoder();

            ProcessEvent event = decoder.decode(record);

            if (event != null) {

                if (event.processEventType() == ProcessEventType.START) {

                    ProcessAction action = new ProcessAction(
                            ActionType.PROCESS_SPAWN,
                            String.valueOf(event.pid())
                    );

                    PolicyDecision decision =
                            processActionListener.onAction(action);

                    System.out.println(
                            "PROCESS ACTION: "
                                    + action.action()
                                    + " TARGET="
                                    + action.target()
                                    + " DECISION="
                                    + decision
                    );
                }
            }
        };


        logFile.eventRecordCallback = callback;

        logFile.isKernelTrace = 0;
        logFile.context = Pointer.NULL;

        logFile.write();

        return logFile;
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
