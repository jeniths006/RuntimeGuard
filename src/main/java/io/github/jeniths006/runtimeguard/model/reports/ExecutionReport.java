package io.github.jeniths006.runtimeguard.model.reports;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public record ExecutionReport(
        Long pid,
        String program,
        int exitCode,
        ProcessHandle.Info processInfo,
        Duration executionTime,
        List<ExecutionEvent> executionEvents
) {
    @Override
    public String toString() {
        String events = executionEvents.stream()
                .map(ExecutionEvent::toString)
                .collect(Collectors.joining("\n"));

        return """
           ========== Execution Report ==========
           Program: %s
           PID: %d
           Exit Code: %d
           Execution Time: %s
           
           Execution Events: 
           %s
           ======================================
           """.formatted(
                program,
                pid,
                exitCode,
                executionTime,
                events

        );
    }
}
