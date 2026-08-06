package io.github.jeniths006.runtimeguard.platform.windows.nativeapi.structures;

import com.sun.jna.Structure;

import java.util.List;

public class EventFilterDescriptor extends Structure {
    public long pointer;
    public int size;
    public int type;

    @Override
    protected List<String> getFieldOrder() {
        return List.of(
                "pointer",
                "size",
                "type"
        );
    }

}
