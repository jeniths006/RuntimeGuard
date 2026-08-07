package io.github.jeniths006.runtimeguard.platform.windows.nativeapi.structures;

import com.sun.jna.Structure;

import java.util.List;

public class Version extends Structure {

    public byte majorVersion;
    public byte minorVersion;
    public byte subVersion;
    public byte subMinorVersion;

    @Override
    protected List<String> getFieldOrder() {
        return List.of(
                "majorVersion",
                "minorVersion",
                "subVersion",
                "subMinorVersion"
        );
    }
}