package io.github.jeniths006.runtimeguard.exception;

public class ProcessMonitorException extends RuntimeException{

    public ProcessMonitorException(String message) {
        super(message);
    }

    public ProcessMonitorException(String message, Throwable cause) {
        super(message, cause);
    }
}
