package io.github.jeniths006.runtimeguard.platform.windows.nativeapi.structures;

import com.sun.jna.Structure;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class Guid extends Structure {

    public int data1;
    public short data2;
    public short data3;
    public byte[] data4 = new byte[8];

    @Override
    protected List<String> getFieldOrder() {
        return List.of(
                "data1",
                "data2",
                "data3",
                "data4"
        );
    }
}
