package io.github.jeniths006.runtimeguard.platform.windows.nativeapi.structures;

import com.sun.jna.Structure;

import java.util.List;

public class EventClass extends Structure {

    public byte type;
    public byte level;
    public short version;

    @Override
    protected List<String> getFieldOrder() {
        return List.of(
                "type",
                "level",
                "version"
        );
    }
}
