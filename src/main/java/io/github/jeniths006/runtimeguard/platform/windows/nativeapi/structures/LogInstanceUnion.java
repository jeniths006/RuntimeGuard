package io.github.jeniths006.runtimeguard.platform.windows.nativeapi.structures;

import com.sun.jna.Union;
import com.sun.jna.platform.win32.Guid;

public class LogInstanceUnion extends Union {
    public Guid.GUID logInstanceGuid;
    public LogFileParams logFileParams = new LogFileParams();
}
