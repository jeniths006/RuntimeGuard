package io.github.jeniths006.runtimeguard.exception;

public class ProcessStartException extends RuntimeException{
    public ProcessStartException(String message) {
        super(message);
    }

    public ProcessStartException(String message, Throwable cause) {
        super(message, cause);

    }
}
