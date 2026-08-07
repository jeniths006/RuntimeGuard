package io.github.jeniths006.runtimeguard.platform.windows.nativeapi.structures;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.platform.win32.Guid;

import java.util.List;

public class EventTrace extends Structure {

    public EventTraceHeader header = new EventTraceHeader();

    public int instanceId;
    public int parentInstanceId;

    public Guid.GUID parentGuid = new Guid.GUID();

    public Pointer mofData;

    public int mofLength;

    public int clientContext;

    @Override
    protected List<String> getFieldOrder() {
        return List.of(
                "header",
                "instanceId",
                "parentInstanceId",
                "parentGuid",
                "mofData",
                "mofLength",
                "clientContext"
        );
    }
}