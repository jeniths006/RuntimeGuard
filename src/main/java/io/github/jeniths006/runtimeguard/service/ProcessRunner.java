package io.github.jeniths006.runtimeguard.service;

import io.github.jeniths006.runtimeguard.exception.ProcessStartException;

import java.io.IOException;

public class ProcessRunner {

    public Process start(String processName) {
        ProcessBuilder pb = new ProcessBuilder(processName);
        try {
            return pb.start();
        } catch (IOException e) {
            throw new ProcessStartException(
                    "Failed to start process " + processName,
                    e
            );
        }

    }
}
