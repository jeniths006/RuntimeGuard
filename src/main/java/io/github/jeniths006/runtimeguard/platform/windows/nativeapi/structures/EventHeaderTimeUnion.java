package io.github.jeniths006.runtimeguard.platform.windows.nativeapi.structures;

import com.sun.jna.Union;

public class EventHeaderTimeUnion extends Union {
    public KernelUserTime kernelUserTime = new KernelUserTime();
    public long processorTime;
    public ClientContextFlags clientContextFlags = new ClientContextFlags();
}
