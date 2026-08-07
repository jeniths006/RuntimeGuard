package io.github.jeniths006.runtimeguard.platform.windows.nativeapi.structures;

import com.sun.jna.Union;

public class ClassTypeUnion extends Union {
    public int version;

    public EventClass eventClass = new EventClass();
}
