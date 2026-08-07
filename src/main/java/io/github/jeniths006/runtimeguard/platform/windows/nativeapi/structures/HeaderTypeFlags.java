package io.github.jeniths006.runtimeguard.platform.windows.nativeapi.structures;

import com.sun.jna.Structure;

import java.util.List;

public class HeaderTypeFlags extends Structure {

    public byte headerType;
    public byte markerFlags;

    @Override
    protected List<String> getFieldOrder() {
        return List.of(
                "headerType",
                "markerFlags"
        );
    }
}