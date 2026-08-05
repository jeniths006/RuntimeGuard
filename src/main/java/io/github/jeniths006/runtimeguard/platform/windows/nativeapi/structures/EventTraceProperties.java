package io.github.jeniths006.runtimeguard.platform.windows.nativeapi.structures;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.platform.win32.WinNT;

import java.util.List;


public class EventTraceProperties extends Structure {
    public NodeHeader wnode = new NodeHeader();

    public int bufferSize;
    public int minimumBuffers;
    public int maximumBuffers;
    public int maximumFileSize;
    public int logFileMode;
    public int flushTimer;
    public int enableFlags;

    public AgeLimitFlushThresholdUnion ageLimitFlushThresholdUnion = new AgeLimitFlushThresholdUnion();

    public int numberOfBuffers;
    public int freeBuffers;
    public int eventsLost;
    public int buffersWritten;
    public int logBuffersLost;
    public int realTimeBuffersLost;
    public WinNT.HANDLE loggerThreadId;
    public int logFileNameOffset;
    public int loggerNameOffset;

    public EventTraceProperties(Pointer pointer) {
        super(pointer);
    }

    public EventTraceProperties() {}


    @Override
    protected List<String> getFieldOrder() {
        return List.of(
                "wnode",
                "bufferSize",
                "minimumBuffers",
                "maximumBuffers",
                "maximumFileSize",
                "logFileMode",
                "flushTimer",
                "enableFlags",
                "ageLimitFlushThresholdUnion",
                "numberOfBuffers",
                "freeBuffers",
                "eventsLost",
                "buffersWritten",
                "logBuffersLost",
                "realTimeBuffersLost",
                "loggerThreadId",
                "logFileNameOffset",
                "loggerNameOffset"
        );
    }
}
