package io.github.jeniths006.runtimeguard.platform.windows.nativeapi.structures;

import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;


public class TimeZoneInformation extends Structure {

    public int bias;
    public char[] standardName = new char[32]; // WCHAR StandardName[32]
    public SystemTime standardDate = new SystemTime();
    public int standardBias;
    public char[] daylightName = new char[32]; // WCHAR DaylightName[32]
    public SystemTime daylightDate = new SystemTime();
    public int daylightBias;

    public TimeZoneInformation() {
        super();
    }

    public TimeZoneInformation(com.sun.jna.Pointer peer) {
        super(peer);
        read();
    }

    public static class ByReference extends TimeZoneInformation implements Structure.ByReference {}
    public static class ByValue extends TimeZoneInformation implements Structure.ByValue {}

    @Override
    protected List<String> getFieldOrder() {
        return List.of(
                "bias",
                "standardName",
                "standardDate",
                "standardBias",
                "daylightName",
                "daylightDate",
                "daylightBias"
        );
    }
}