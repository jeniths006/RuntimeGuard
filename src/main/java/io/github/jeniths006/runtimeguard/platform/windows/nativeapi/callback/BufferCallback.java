package io.github.jeniths006.runtimeguard.platform.windows.nativeapi.callback;

import com.sun.jna.win32.StdCallLibrary;
import io.github.jeniths006.runtimeguard.platform.windows.nativeapi.structures.EventTraceLogFile;

public interface BufferCallback extends StdCallLibrary.StdCallCallback {
    int invoke(EventTraceLogFile logFile);
}
