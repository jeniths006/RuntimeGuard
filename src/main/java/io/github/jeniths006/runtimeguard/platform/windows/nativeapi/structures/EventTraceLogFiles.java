package io.github.jeniths006.runtimeguard.platform.windows.nativeapi.structures;

import com.sun.jna.Structure;
import com.sun.jna.WString;
import io.github.jeniths006.runtimeguard.platform.windows.nativeapi.callback.EventRecordCallback;

import java.util.List;

public class EventTraceLogFiles extends Structure {

    public WString loggerName;
    public EventRecordCallback eventRecordCallback;

    @Override
    protected List<String> getFieldOrder() {
        return List.of();
    }
}
