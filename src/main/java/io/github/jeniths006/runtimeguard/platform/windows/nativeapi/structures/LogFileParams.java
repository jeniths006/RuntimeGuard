package io.github.jeniths006.runtimeguard.platform.windows.nativeapi.structures;

import com.sun.jna.Structure;

import java.util.List;


public class LogFileParams extends Structure {

    public int startBuffers;
    public int pointerSize;
    public int eventsLost;
    public int cpuSpeedInMHz;

    @Override
    protected List<String> getFieldOrder() {
        return List.of(
                "startBuffers",
                "pointerSize",
                "eventsLost",
                "cpuSpeedInMHz"
        );
    }

}
