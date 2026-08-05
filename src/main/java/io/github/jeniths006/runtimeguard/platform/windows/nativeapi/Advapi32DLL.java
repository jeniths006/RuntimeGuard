package io.github.jeniths006.runtimeguard.platform.windows.nativeapi;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.WString;
import com.sun.jna.platform.win32.BaseTSD;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.win32.StdCallLibrary;
import io.github.jeniths006.runtimeguard.platform.windows.nativeapi.structures.EventTrace;
import io.github.jeniths006.runtimeguard.platform.windows.nativeapi.structures.EventTraceLogFile;
import io.github.jeniths006.runtimeguard.platform.windows.nativeapi.structures.EventTraceProperties;

public interface Advapi32DLL extends StdCallLibrary {

    Advapi32DLL INSTANCE = Native.load("Advapi32", Advapi32DLL.class);

    WinNT.HANDLE OpenTraceW(EventTraceLogFile logfile);

    int ProcessTrace(
        WinNT.HANDLE[] handles,
        int handleCount,
        Pointer startTime,
        Pointer endTime
    );

    int CloseTrace(WinNT.HANDLE handle);

    int StartTraceW(
            BaseTSD.ULONG_PTRByReference sessionHandle,
            WString sessionName,
            EventTraceProperties properties
    );
}
