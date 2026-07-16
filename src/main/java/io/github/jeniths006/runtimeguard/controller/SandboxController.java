package io.github.jeniths006.runtimeguard.controller;

import java.nio.file.Path;

import io.github.jeniths006.runtimeguard.exception.PolicyLoadException;
import io.github.jeniths006.runtimeguard.exception.PolicyValidationException;
import io.github.jeniths006.runtimeguard.model.ExecutionReport;
import io.github.jeniths006.runtimeguard.model.ExecutionRequest;
import io.github.jeniths006.runtimeguard.model.Policy;
import io.github.jeniths006.runtimeguard.service.PolicyLoader;
import io.github.jeniths006.runtimeguard.service.PolicyValidator;
import io.github.jeniths006.runtimeguard.service.ProcessMonitor;
import io.github.jeniths006.runtimeguard.service.ProcessRunner;

public class SandboxController {

    PolicyLoader policyLoader = new PolicyLoader();
    PolicyValidator policyValidator = new PolicyValidator();
    ProcessRunner processRunner = new ProcessRunner();
    ProcessMonitor processMonitor = new ProcessMonitor();

    public void execute(ExecutionRequest request) {
        System.out.println("Executing " + request.action() + " on " + request.target());
        System.out.println("Loading policy file: " + request.policyPath());

        Path policyPath = Path.of(request.policyPath());

        try {
            Policy policy = policyLoader.load(policyPath);
            System.out.println("Policy loaded successfully");
            policyValidator.validate(policy);
            System.out.println("Policy validated successfully");
            Process process = processRunner.start(request.target());
            ExecutionReport report = processMonitor.monitor(process, request.target());
            System.out.println(report);


        } catch (PolicyLoadException | PolicyValidationException e) {
           e.printStackTrace();
        }



    }
}
