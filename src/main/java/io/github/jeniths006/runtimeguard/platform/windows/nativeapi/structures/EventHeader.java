package io.github.jeniths006.runtimeguard.platform.windows.nativeapi.structures;

import com.sun.jna.Structure;

import java.util.List;
import com.sun.jna.platform.win32.Guid.GUID;

public class EventHeader extends Structure {

    public short size;
    public short headerType;
    public short flags;
    public short eventProperty;

    public int threadId;
    public int processId;

    public long timeStamp;

    public GUID providerId = new GUID();

    public EventDescriptor eventDescriptor = new EventDescriptor();

    public EventTimeUnion eventTime = new EventTimeUnion();
    public GUID activityId = new GUID();
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
