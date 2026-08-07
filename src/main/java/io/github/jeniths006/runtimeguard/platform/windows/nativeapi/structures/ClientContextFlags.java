package io.github.jeniths006.runtimeguard.platform.windows.nativeapi.structures;

import com.sun.jna.Structure;

import java.util.List;

public class ClientContextFlags extends Structure {

    public int clientContext;
    public int flags;

    @Override
    protected List<String> getFieldOrder() {
        return List.of(
                "clientContext",
                "flags"
        );
    }
}
