package io.github.jeniths006.runtimeguard.controller;

import java.nio.file.Path;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import io.github.jeniths006.runtimeguard.exception.PolicyLoadException;
import io.github.jeniths006.runtimeguard.exception.PolicyValidationException;
import io.github.jeniths006.runtimeguard.model.*;
import io.github.jeniths006.runtimeguard.model.reports.ExecutionEvent;
import io.github.jeniths006.runtimeguard.model.reports.ExecutionReport;
import io.github.jeniths006.runtimeguard.model.action.ActionType;
import io.github.jeniths006.runtimeguard.model.action.ProcessAction;
import io.github.jeniths006.runtimeguard.model.reports.ProcessMonitorResult;
import io.github.jeniths006.runtimeguard.service.*;

public class SandboxController {

    private final PolicyLoader policyLoader = new PolicyLoader();
    private final PolicyValidator policyValidator = new PolicyValidator();
    private final ProcessRunner processRunner = new ProcessRunner();
    private final ProcessMonitor processMonitor = new ProcessMonitor();

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
            ProcessAction processAction = new ProcessAction(ActionType.PROCESS_SPAWN, request.target());
            PolicyDecision policyDecision = policyEngine.evaluate(processAction);
            if(policyDecision == PolicyDecision.DENY) {
                System.out.println("Policy denied process execution.");
                return;
            }

            ExecutionEvent executionEvent = new ExecutionEvent(
                    processAction,
                    policyDecision,
                    Instant.now()
            );

            List<ExecutionEvent> executionEvents = new ArrayList<>();
            executionEvents.add(executionEvent);

            Process process = processRunner.start(request.target());
            ProcessMonitorResult processMonitorResult = processMonitor.monitor(process);

            ExecutionReport executionReport = new ExecutionReport(
                    processMonitorResult.pid(),
                    request.target(),
                    processMonitorResult.exitCode(),
                    processMonitorResult.processInfo(),
                    processMonitorResult.executionTime(),
                    executionEvents
            );

            System.out.println(executionReport);



        } catch (PolicyLoadException | PolicyValidationException e) {
           e.printStackTrace();
        }



    }
}
