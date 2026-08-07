package io.github.jeniths006.runtimeguard.platform.windows.nativeapi.structures;

import com.sun.jna.Union;

public class HeaderTypeUnion extends Union {
    public short fieldTypeFlags;
    public HeaderTypeFlags headerTypeFlags = new HeaderTypeFlags();
}
