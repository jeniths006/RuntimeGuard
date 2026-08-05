package io.github.jeniths006.runtimeguard.platform.windows.nativeapi.structures;

import com.sun.jna.Union;

public class AgeLimitFlushThresholdUnion extends Union {
    public int ageLimit;
    public int flushThreshold;
}
