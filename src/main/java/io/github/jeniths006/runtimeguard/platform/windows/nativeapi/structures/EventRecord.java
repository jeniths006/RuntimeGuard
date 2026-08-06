package io.github.jeniths006.runtimeguard.platform.windows.nativeapi.structures;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

import java.util.List;

public class EventRecord extends Structure {

    public EventHeader eventHeader = new EventHeader();

    public ETWBufferContext bufferContext = new ETWBufferContext();

    public short extendedDataCount;
    public short userDataLength;

    public Pointer extendedData;
    public Pointer userData;

    public Pointer userContext;

    @Override
    protected List<String> getFieldOrder() {
        return List.of(
                "eventHeader",
                "bufferContext",
                "extendedDataCount",
                "userDataLength",
                "extendedData",
                "userData",
                "userContext"
        );
    }
}