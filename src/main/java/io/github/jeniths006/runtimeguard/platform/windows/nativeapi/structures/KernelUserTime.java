package io.github.jeniths006.runtimeguard.platform.windows.nativeapi.structures;

import com.sun.jna.Structure;

import java.util.List;

public class KernelUserTime extends Structure {
    public int kernelTime;
    public int userTime;

    @Override
    protected List<String> getFieldOrder() {
        return List.of(
                "kernelTime",
                "userTime"
        );
    }
}
