package io.github.jeniths006.runtimeguard.platform.windows.etw;

public class ETWKernelFlags {

    private ETWKernelFlags() {}

    public static final int EVENT_TRACE_FLAG_PROCESS = 0x00000001;
    public static final int EVENT_TRACE_FLAG_FILE_IO = 0x02000000;
    public static final int EVENT_TRACE_FLAG_NETWORK_TCPIP = 0x00010000;
    public static final int EVENT_TRACE_FLAG_REGISTRY = 0x00020000;
}
