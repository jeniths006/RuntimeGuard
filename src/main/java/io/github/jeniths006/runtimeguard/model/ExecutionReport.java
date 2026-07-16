package io.github.jeniths006.runtimeguard.model;

import java.time.Duration;

public record ExecutionReport(
        Long pid,
        String program,
        int exitCode,
        ProcessHandle.Info processInfo,
        Duration executionTime
) {
    @Override
    public String toString() {
        return """
           ========== Execution Report ==========
           Program: %s
           PID: %d
           Exit Code: %d
           Execution Time: %s
           ======================================
           """.formatted(
                program,
                pid,
                exitCode,
                executionTime
        );
    }
}
