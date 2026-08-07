package io.github.jeniths006.runtimeguard.platform.windows.nativeapi.structures;

import com.sun.jna.Union;

public class EventTimeUnion extends Union {

    public int kernelTime;
    public int clientContext;
    public long processorTime;
}
