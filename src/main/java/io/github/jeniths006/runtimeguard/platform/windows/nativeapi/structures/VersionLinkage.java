package io.github.jeniths006.runtimeguard.platform.windows.nativeapi.structures;

import com.sun.jna.Structure;

import java.util.List;

public class VersionLinkage extends Structure {
    public int version;
    public int linkage;

    @Override
    protected List<String> getFieldOrder() {
        return List.of(
                "version",
                "linkage"
        );
    }
}
