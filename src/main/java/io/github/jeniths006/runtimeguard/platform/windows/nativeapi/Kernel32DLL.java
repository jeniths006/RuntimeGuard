package io.github.jeniths006.runtimeguard.platform.windows.nativeapi;

import com.sun.jna.Library;
import com.sun.jna.Native;

public interface Kernel32DLL extends Library {
    
    Kernel32DLL INSTANCE = Native.load("kernel32", Kernel32DLL.class);
    
    int GetCurrentProcessId();
}
