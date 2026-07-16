package io.github.jeniths006.runtimeguard.controller;

import java.nio.file.Path;

import io.github.jeniths006.runtimeguard.exception.PolicyLoadException;
import io.github.jeniths006.runtimeguard.exception.PolicyValidationException;
import io.github.jeniths006.runtimeguard.model.ExecutionReport;
import io.github.jeniths006.runtimeguard.model.ProcessRequest;
import io.github.jeniths006.runtimeguard.model.Policy;
import io.github.jeniths006.runtimeguard.model.action.ActionType;
import io.github.jeniths006.runtimeguard.model.action.ProcessAction;
import io.github.jeniths006.runtimeguard.service.*;

public class SandboxController {

    PolicyLoader policyLoader = new PolicyLoader();
    PolicyValidator policyValidator = new PolicyValidator();
    ProcessRunner processRunner = new ProcessRunner();
    ProcessMonitor processMonitor = new ProcessMonitor();

    public void execute(ProcessRequest request) {
        System.out.println("Executing " + request.action() + " on " + request.target());
        System.out.println("Loading policy file: " + request.policyPath());

        Path policyPath = Path.of(request.policyPath());

        try {
            Policy policy = policyLoader.load(policyPath);
            System.out.println("Policy loaded successfully");

            policyValidator.validate(policy);
            System.out.println("Policy validated successfully");

            PolicyEngine policyEngine = new PolicyEngine(policy);
            ProcessAction action = new ProcessAction(ActionType.PROCESS_SPAWN, request.target());
            if(!policyEngine.isAllowed(action)) {
                System.out.println("Policy denied process execution.");
                return;
            }

            Process process = processRunner.start(request.target());
            ExecutionReport report = processMonitor.monitor(process, request.target());
            System.out.println(report);


        } catch (PolicyLoadException | PolicyValidationException e) {
           e.printStackTrace();
        }



    }
}
