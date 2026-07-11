package io.github.jeniths006.runtimeguard.service;

import io.github.jeniths006.runtimeguard.exception.PolicyNotFoundException;
import io.github.jeniths006.runtimeguard.model.Policy;

import java.nio.file.Files;
import java.nio.file.Path;

public class PolicyLoader {

    public Policy load(Path path) {
        if(!Files.exists(path)) {
            throw new PolicyNotFoundException("Policy file not found: " + path.toAbsolutePath());
        }

        System.out.println("Loading policy from: " + path.toAbsolutePath());

        throw new UnsupportedOperationException("Policy parsing not implemented yet");
    }
}
