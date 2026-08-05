package io.github.jeniths006.runtimeguard.platform.windows.nativeapi.structures;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.WString;
import io.github.jeniths006.runtimeguard.platform.windows.nativeapi.callback.BufferCallback;
import io.github.jeniths006.runtimeguard.platform.windows.nativeapi.callback.EventRecordCallback;

import java.util.List;

public class EventTraceLogFile extends Structure {

    public WString logFileName;
    public WString loggerName;

    public long currentTime;
    public int buffersRead;

    public FileProcessModeUnion fileProcessModeUnion = new FileProcessModeUnion();
    public EventTrace currentEvent = new EventTrace();
    public TraceLogFileHeader logFileHeader = new TraceLogFileHeader();
    public BufferCallback bufferCallback;

    public int bufferSizeFilled;
    public int filled;

    public int eventsLost;
    public EventRecordCallback eventRecordCallback;

    public int isKernelTrace;

    public Pointer context;

    @Override
    protected List<String> getFieldOrder() {
        return List.of(
                "logFileName",
                "loggerName",
                "currentTime",
                "buffersRead",
                "fileProcessModeUnion",
                "currentEvent",
                "logFileHeader",
                "bufferCallback",
                "bufferSizeFilled",
                "filled",
                "eventsLost",
                "eventRecordCallback",
                "isKernelTrace",
                "context"
        );
    }
}
