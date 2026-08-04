package io.github.jeniths006.runtimeguard.platform.windows.nativeapi;

import com.sun.jna.Library;
import com.sun.jna.Native;
import io.github.jeniths006.runtimeguard.platform.windows.nativeapi.structures.EventTraceLogFiles;

public interface Advapi32DLL extends Library {

    Advapi32DLL INSTANCE = Native.load("Advapi32", Advapi32DLL.class);

    long OpenTraceW(EventTraceLogFiles logfile);
}
