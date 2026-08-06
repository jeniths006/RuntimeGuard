package io.github.jeniths006.runtimeguard.platform.windows.nativeapi.callback;

import com.sun.jna.Callback;
import com.sun.jna.win32.StdCallLibrary;
import io.github.jeniths006.runtimeguard.platform.windows.nativeapi.structures.EventRecord;

public interface EventRecordCallback extends StdCallLibrary.StdCallCallback {

    void invoke(EventRecord eventRecord);
}
