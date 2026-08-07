package io.github.jeniths006.runtimeguard.platform.windows.nativeapi.structures;

import com.sun.jna.Structure;

import java.util.List;

public class LargeInteger extends Structure {

    public long quadPart;

    @Override
    protected List<String> getFieldOrder() {
        return List.of("quadPart");
    }
}