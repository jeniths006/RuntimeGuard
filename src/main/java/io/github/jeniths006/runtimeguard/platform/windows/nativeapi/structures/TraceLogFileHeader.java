package io.github.jeniths006.runtimeguard.platform.windows.nativeapi.structures;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

import java.util.List;

public class TraceLogFileHeader extends Structure {

    public int bufferSize;
    public VersionUnion version = new VersionUnion();
    public int providerVersion;
    public int numberOfProcessors;
    public LargeInteger endTime = new LargeInteger();
    public int timerResolution;
    public int maximumFileSize;
    public int logFileMode;
    public int buffersWritten;
    public LogInstanceUnion logInstanceUnion = new LogInstanceUnion();
    public Pointer loggerName;
    public Pointer logFileName;
    public TimeZoneInformation timeZone = new TimeZoneInformation();
    public LargeInteger bootTime = new LargeInteger();
    public LargeInteger perfFreq = new LargeInteger();
    public LargeInteger startTime = new LargeInteger();
    public int reservedFlags;
    public int buffersLost;

    @Override
    protected List<String> getFieldOrder() {
        return List.of(
                "bufferSize",
                "version",
                "providerVersion",
                "numberOfProcessors",
                "endTime",
                "timerResolution",
                "maximumFileSize",
                "logFileMode",
                "buffersWritten",
                "logInstanceUnion",
                "loggerName",
                "logFileName",
                "timeZone",
                "bootTime",
                "perfFreq",
                "startTime",
                "reservedFlags",
                "buffersLost"
        );
    }


}