package io.github.jeniths006.runtimeguard.platform.windows.nativeapi.structures;

import com.sun.jna.Structure;
import com.sun.jna.platform.win32.Guid;

import java.util.List;

public class EventTraceHeader extends Structure {

    public short size;

    public HeaderTypeUnion headerType = new HeaderTypeUnion();

    public ClassTypeUnion classType = new ClassTypeUnion();

    public int threadId;
    public int processId;

    public long timeStamp;

    public Guid.GUID guid = new Guid.GUID();

    public EventHeaderTimeUnion eventHeaderTime = new EventHeaderTimeUnion();

    @Override
    protected List<String> getFieldOrder() {
        return List.of(
                "size",
                "headerType",
                "classType",
                "threadId",
                "processId",
                "timeStamp",
                "guid",
                "eventHeaderTime"
        );
    }
}