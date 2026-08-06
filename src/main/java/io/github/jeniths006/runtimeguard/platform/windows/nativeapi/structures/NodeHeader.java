package io.github.jeniths006.runtimeguard.platform.windows.nativeapi.structures;

import com.sun.jna.Structure;
import com.sun.jna.platform.win32.Guid.GUID;

import java.util.List;

public class NodeHeader extends Structure {

    public int bufferSize;
    public int providerId;

    public HistoricalContextUnion historicalContextUnion = new HistoricalContextUnion();
    public KernelTimeStampUnion kernelTimeStampUnion = new KernelTimeStampUnion();
    public GUID guid = new GUID() ;
    public int clientContext;
    public int flags;

    @Override
    protected List<String> getFieldOrder() {
        return List.of(
                "bufferSize",
                "providerId",
                "historicalContextUnion",
                "kernelTimeStampUnion",
                "guid",
                "clientContext",
                "flags"
        );
    }
}
