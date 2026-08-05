package io.github.jeniths006.runtimeguard.platform.windows.nativeapi.structures;

import com.sun.jna.Union;
import com.sun.jna.platform.win32.WinNT;

public class KernelTimeStampUnion extends Union {
    public WinNT.HANDLE kernelHandle;
    public WinNT.LARGE_INTEGER kernelTimeStamp;
}
