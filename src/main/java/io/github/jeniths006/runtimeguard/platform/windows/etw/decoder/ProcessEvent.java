package io.github.jeniths006.runtimeguard.platform.windows.etw.decoder;


public record ProcessEvent(
        long pid,
        ProcessEventType processEventType
) {
}
