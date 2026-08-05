package io.github.jeniths006.runtimeguard.platform.windows.nativeapi.structures;

import com.sun.jna.Union;

public class HistoricalContextUnion extends Union {
    public long historicalContext;
    public VersionLinkage versionLinkage = new VersionLinkage();
}
