package io.github.jeniths006.runtimeguard.exception;

public class PolicyLoadException extends RuntimeException{
    public PolicyLoadException(String message) {
        super(message);
    }

    public PolicyLoadException(String message, Throwable cause) {
        super(message, cause);
    }
}
