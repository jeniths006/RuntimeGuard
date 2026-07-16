package io.github.jeniths006.runtimeguard.service;

import io.github.jeniths006.runtimeguard.exception.ProcessMonitorException;
import io.github.jeniths006.runtimeguard.model.ExecutionReport;

import java.time.Duration;
import java.time.Instant;

public class ProcessMonitor {

    public ExecutionReport monitor(Process process, String program) {

        int exitCode;

        Instant start = Instant.now();

        try {
            exitCode = process.waitFor(); //Returns exit code by default
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ProcessMonitorException(
                    "Process monitoring was interrupted",
                    e
            );
        }

        Instant end = Instant.now();

        Duration executionTime = Duration.between(start, end);

        return new ExecutionReport(process.pid(), program, exitCode, process.info(), executionTime);

    }


}
