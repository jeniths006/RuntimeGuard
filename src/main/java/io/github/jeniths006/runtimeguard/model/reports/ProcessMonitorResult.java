package io.github.jeniths006.runtimeguard.model.reports;

import java.time.Duration;

public record ProcessMonitorResult(
        Long pid,
        int exitCode,
        ProcessHandle.Info processInfo,
        Duration executionTime

) {
}
