package io.github.jeniths006.runtimeguard.model;


public record ProcessRequest(ExecutionCommand action, String target, String policyPath) {

}
