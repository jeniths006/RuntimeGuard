package io.github.jeniths006.runtimeguard.controller;

import java.nio.file.Path;
import java.nio.file.Paths;

import io.github.jeniths006.runtimeguard.exception.PolicyLoadException;
import io.github.jeniths006.runtimeguard.model.ExecutionRequest;
import io.github.jeniths006.runtimeguard.model.Policy;
import io.github.jeniths006.runtimeguard.service.PolicyLoader;

public class SandboxController {

    public void execute(ExecutionRequest request) {
        System.out.println("Executing " + request.action() + " on " + request.target());
        System.out.println("Loading policy file: " + request.policyPath());

        PolicyLoader policyLoader = new PolicyLoader();

        Path policyPath = Paths.get(request.policyPath());

        try {
            Policy policy = policyLoader.load(policyPath);
            System.out.println("Policy loaded successfully");
        } catch (PolicyLoadException e) {
            e.printStackTrace();
        }



    }
}
