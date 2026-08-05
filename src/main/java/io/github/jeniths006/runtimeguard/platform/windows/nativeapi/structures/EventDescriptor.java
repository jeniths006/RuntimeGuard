package io.github.jeniths006.runtimeguard.platform.windows.nativeapi.structures;

import com.sun.jna.Structure;

import java.util.List;

public class EventDescriptor extends Structure {
    public short id;
    public byte version;
    public byte channel;
    public byte level;
    public byte opcode;
    public short task;
    public long keyword;

    @Override
    protected List<String> getFieldOrder() {
        return List.of(
                "id",
                "version",
                "channel",
                "level",
                "opcode",
                "task",
                "keyword"
        );
    }

}
