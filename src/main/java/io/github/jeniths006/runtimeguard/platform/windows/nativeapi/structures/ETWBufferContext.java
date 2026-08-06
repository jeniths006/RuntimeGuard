package io.github.jeniths006.runtimeguard.platform.windows.nativeapi.structures;

import com.sun.jna.Structure;

import java.util.List;

public class ETWBufferContext extends Structure {

    public byte processorNumber;
    public byte alignment;
    public short loggerId;

    @Override
    protected List<String> getFieldOrder() {
        return List.of(
                "processorNumber",
                "alignment",
                "loggerId"
        );
    }
}