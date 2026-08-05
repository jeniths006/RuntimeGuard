package io.github.jeniths006.runtimeguard.platform.windows.nativeapi.structures;

import com.sun.jna.Structure;

import java.util.List;

public class EventHeader extends Structure {

    public short size;
    public short headerType;
    public short flags;
    public short eventProperty;

    public int threadId;
    public int processId;

    public long timeStamp;

    public Guid providerId = new Guid();

    public EventDescriptor eventDescriptor = new EventDescriptor();

    public EventTimeUnion eventTime = new EventTimeUnion();
    public Guid activityId = new Guid();
    @Override
    protected List<String> getFieldOrder() {
        return List.of(
                "size",
                "headerType",
                "flags",
                "eventProperty",
                "threadId",
                "processId",
                "timeStamp",
                "providerId",
                "eventDescriptor",
                "eventTime",
                "activityId"
        );
    }
}
