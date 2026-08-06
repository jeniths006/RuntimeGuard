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
import io.github.jeniths006.runtimeguard.platform.windows.nativeapi.structures.EventTraceLogFile;
import io.github.jeniths006.runtimeguard.service.interceptor.ProcessActionListener;
import io.github.jeniths006.runtimeguard.platform.windows.nativeapi.ETWConstants;


public class ETWSession {


    private final BaseTSD.ULONG_PTRByReference sessionHandle= new BaseTSD.ULONG_PTRByReference();
    private final WString sessionName = new WString("RuntimeGuardSession_" + System.currentTimeMillis());
    private final EventTracePropertiesBuilder propertiesBuilder = new EventTracePropertiesBuilder();
    private WinNT.HANDLE traceHandle;
    private EventTraceLogFile logFile;
    private EventRecordCallback callback;
    private Memory loggerNameMemory;


    public void start(long pid, ProcessActionListener processActionListener) {
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

        enableKernelProviders();


        this.logFile = createTraceLogFile(pid, processActionListener);
        traceHandle = Advapi32DLL.INSTANCE.OpenTraceW(logFile);

        System.out.println("OpenTrace handle: " + traceHandle);

        if (traceHandle == null || Pointer.nativeValue(traceHandle.getPointer()) == -1) {
            int error = Native.getLastError();
            throw new RuntimeException("OpenTraceW failed, last error: " + error);
        }

        System.out.println("Trace Handle: " + traceHandle);

        Thread traceThread = new Thread(() -> {
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

    public void stop() {

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
            System.out.println("ETW EVENT RECEIVED");
        };

        logFile.eventRecordCallback = callback;

        logFile.isKernelTrace = 0;
        logFile.context = Pointer.NULL;

        logFile.write();

        return logFile;
    }

    private void enableKernelProviders() {

        int result = Advapi32DLL.INSTANCE.EnableTraceEx2(
                sessionHandle.getValue(),
                KernelProviders.SYSTEM_TRACE_PROVIDER,
                ETWConstants.EVENT_CONTROL_CODE_ENABLE_PROVIDER,
                ETWConstants.TRACE_LEVEL_INFORMATION,
                0,
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
