package io.github.jeniths006.runtimeguard.platform.windows.nativeapi.structures;

import com.sun.jna.Union;

public class FileProcessModeUnion extends Union {
    public int LogFileMode;
    public int ProcessTraceMode;
}
