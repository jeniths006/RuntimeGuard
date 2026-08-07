package io.github.jeniths006.runtimeguard.platform.windows.nativeapi;

public final class ETWConstants {

    private ETWConstants() {};

    public static final int WNODE_FLAG_TRACED_GUID = 0x00020000;
    public static final int EVENT_TRACE_REAL_TIME_MODE = 0x00000100;
    public static final int EVENT_TRACE_SYSTEM_LOGGER_MODE = 0x02000000;
    public static final int PROCESS_TRACE_MODE_REAL_TIME = 0x00000100;
    public static final int PROCESS_TRACE_MODE_EVENT_RECORD = 0x10000000;

    public static final long EVENT_TRACE_FLAG_PROCESS    = 0x00000001L;
    public static final long EVENT_TRACE_FLAG_THREAD     = 0x00000002L;
    public static final long EVENT_TRACE_FLAG_IMAGE_LOAD = 0x00000004L;
    public static final long EVENT_TRACE_FLAG_DISK_IO    = 0x00000100L;
    public static final long EVENT_TRACE_FLAG_FILE_IO    = 0x02000000L;

    public static final int EVENT_CONTROL_CODE_ENABLE_PROVIDER = 1;
    public static final int EVENT_CONTROL_CODE_DISABLE_PROVIDER = 0;
    public static final int EVENT_TRACE_CONTROL_STOP = 1;

    public static final byte TRACE_LEVEL_NONE = 0;
    public static final byte TRACE_LEVEL_CRITICAL = 1;
    public static final byte TRACE_LEVEL_ERROR = 2;
    public static final byte TRACE_LEVEL_WARNING = 3;
    public static final byte TRACE_LEVEL_INFORMATION = 4;
    public static final byte TRACE_LEVEL_VERBOSE = 5;
}
