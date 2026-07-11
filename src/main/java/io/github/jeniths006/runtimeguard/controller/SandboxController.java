package io.github.jeniths006.runtimeguard.controller;


import io.github.jeniths006.runtimeguard.model.ExecutionRequest;

public class SandboxController {

    public void execute(ExecutionRequest request) {
        System.out.println("Executing " + request.action() + " on " + request.target());
    }
}
