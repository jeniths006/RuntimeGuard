package io.github.jeniths006.runtimeguard.model;

public record ExecutionRequest(ExecutionAction action, String target, String policyPath) {

}
