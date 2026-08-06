package io.github.jeniths006.runtimeguard.platform.windows.nativeapi.structures;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

import java.util.List;

public class EnableTraceParameters extends Structure {
    public int version;
    public int enableProperty;
    public int controlFlags;
    public com.sun.jna.platform.win32.Guid.GUID sourceId = new com.sun.jna.platform.win32.Guid.GUID();
    public Pointer enableFilterDesc = Pointer.NULL;
    public int filterDescCount;

    @Override
    protected List<String> getFieldOrder() {
        return List.of(
                "version",
                "enableProperty",
                "controlFlags",
                "sourceId",
                "enableFilterDesc",
                "filterDescCount"
        );
    }

}
