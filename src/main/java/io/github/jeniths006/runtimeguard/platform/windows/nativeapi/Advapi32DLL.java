package io.github.jeniths006.runtimeguard.platform.windows.nativeapi;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.WString;
import com.sun.jna.platform.win32.BaseTSD;
import com.sun.jna.platform.win32.Guid;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.win32.StdCallLibrary;
import io.github.jeniths006.runtimeguard.platform.windows.nativeapi.structures.*;

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

    int ControlTraceW(
            BaseTSD.ULONG_PTR sessionhandle,
            WString sessionName,
            EventTraceProperties properties,
            int controlCode
    );

    int EnableTraceEx2(
            BaseTSD.ULONG_PTR sessionHandle,
            Guid.GUID providerId,
            int controlCode,
            byte level,
            long matchAnyKeyword,
            long matchAllKeyword,
            int timeout,
            EnableTraceParameters enableTraceParameters
    );
}
