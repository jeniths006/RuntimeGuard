package io.github.jeniths006.runtimeguard.platform.windows.nativeapi.structures;

import com.sun.jna.Structure;
import java.util.List;

public class SystemTime extends Structure {
    public short wYear;
    public short wMonth;
    public short wDayOfWeek;
    public short wDay;
    public short wHour;
    public short wMinute;
    public short wSecond;
    public short wMilliseconds;

    public static class ByReference extends SystemTime implements Structure.ByReference {}
    public static class ByValue extends SystemTime implements Structure.ByValue {}

    @Override
    protected List<String> getFieldOrder() {
        return List.of(
                "wYear",
                "wMonth",
                "wDayOfWeek",
                "wDay",
                "wHour",
                "wMinute",
                "wSecond",
                "wMilliseconds"
        );
    }
}