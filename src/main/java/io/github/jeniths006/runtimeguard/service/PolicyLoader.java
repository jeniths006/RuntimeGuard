package io.github.jeniths006.runtimeguard.service;
import io.github.jeniths006.runtimeguard.exception.PolicyLoadException;
import io.github.jeniths006.runtimeguard.exception.PolicyNotFoundException;
import io.github.jeniths006.runtimeguard.model.Policy;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class PolicyLoader {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public Policy load(Path path) {
        if(!Files.exists(path)) {
            throw new PolicyNotFoundException("Policy file not found: " + path.toAbsolutePath());
        }

        System.out.println("Loading policy from: " + path.toAbsolutePath());
        try {
            Policy policy = objectMapper.readValue(path.toFile(), Policy.class);
            return policy;
        } catch (IOException e) {
            throw new PolicyLoadException(
                    "Failed to load policy: " + path.toAbsolutePath(),
                    e
            );

        }

    }
}
