package io.github.jeniths006.runtimeguard.platform.windows.etw;

import com.sun.jna.Memory;
import io.github.jeniths006.runtimeguard.platform.windows.nativeapi.structures.EventTraceProperties;

public class ETWSessionProperties {
    public Memory memory;
    public EventTraceProperties eventTraceProperties;

    public ETWSessionProperties(Memory memory, EventTraceProperties eventTraceProperties) {
        this.memory = memory;
        this.eventTraceProperties = eventTraceProperties;
    }
}
