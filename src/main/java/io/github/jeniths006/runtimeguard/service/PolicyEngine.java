package io.github.jeniths006.runtimeguard.service;

import io.github.jeniths006.runtimeguard.model.Policy;
import io.github.jeniths006.runtimeguard.model.PolicyDecision;
import io.github.jeniths006.runtimeguard.model.action.ProcessAction;
import io.github.jeniths006.runtimeguard.model.policy.FileSystemMode;
import io.github.jeniths006.runtimeguard.model.policy.NetworkMode;
import io.github.jeniths006.runtimeguard.model.policy.ProcessMode;

public class PolicyEngine {

    private final Policy policy;

    public PolicyEngine(Policy policy) {
        this.policy = policy;
    }

    public boolean canReadFiles() {
        return policy.filesystem().mode() != FileSystemMode.DENY;
    }

    public boolean canWriteFiles() {
        return policy.filesystem().mode() == FileSystemMode.READ_WRITE;
    }

    public boolean canAccessNetwork() {
        return policy.network().mode() == NetworkMode.ALLOW;
    }

    public boolean canSpawnProcesses() {
        return policy.process().mode() == ProcessMode.ALLOW;
    }

    public PolicyDecision evaluate(ProcessAction action) {
        return switch(action.action()) {
            case FILE_READ -> canReadFiles() ? PolicyDecision.ALLOW: PolicyDecision.DENY;
            case FILE_WRITE -> canWriteFiles()  ? PolicyDecision.ALLOW: PolicyDecision.DENY;
            case NETWORK_CONNECT -> canAccessNetwork()  ? PolicyDecision.ALLOW: PolicyDecision.DENY;
            case PROCESS_SPAWN ->  canSpawnProcesses()  ? PolicyDecision.ALLOW: PolicyDecision.DENY;
        };
    }
}
