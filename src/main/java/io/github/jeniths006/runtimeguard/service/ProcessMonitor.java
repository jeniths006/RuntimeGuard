package io.github.jeniths006.runtimeguard.service;

import io.github.jeniths006.runtimeguard.exception.ProcessMonitorException;

public class ProcessMonitor {

    public int waitFor(Process process) {
        try {
            return process.waitFor();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ProcessMonitorException(
                    "Process monitoring was interrupted",
                    e
            );
        }
    }

    public void monitor(Process process) {
        System.out.println("===== Process Information =====");
        System.out.println("PID: " + process.pid());
        System.out.println("Information: " + process.info());
        System.out.println("Alive: " + process.isAlive());
        System.out.println("===============================");
    }
}
