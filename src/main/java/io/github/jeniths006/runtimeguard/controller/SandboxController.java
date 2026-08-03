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

        List<ExecutionEvent> executionEvents = new ArrayList<>();

        Path policyPath = Path.of(request.policyPath());

        try {

            //Loading policy
            Policy policy = policyLoader.load(policyPath);
            System.out.println("Policy loaded successfully");

            //Validate Policy file
            policyValidator.validate(policy);
            System.out.println("Policy validated successfully");

            //Create Policy Engine
            PolicyEngine policyEngine = new PolicyEngine(policy);

            //Create a new Process Action with type of process action and target
            ProcessAction processAction = new ProcessAction(ActionType.PROCESS_SPAWN, request.target());


            //Evaluate policy to come to a decision ALLOW/DENY
            PolicyDecision policyDecision = recordEvent(processAction, policyEngine, executionEvents);

            //Terminate process if DENY
            if(policyDecision == PolicyDecision.DENY) {
                System.out.println("Policy denied process execution.");
                return;
            }

            //Test Data
            ProcessAction fileReadAction = new ProcessAction(ActionType.FILE_READ, "C:\\temp\\secret.txt");
            recordEvent(fileReadAction, policyEngine, executionEvents);


            Process process = processRunner.start(request.target());
            ProcessMonitorResult processMonitorResult = processMonitor.monitor(process);

            //Create execution report
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

    //Record execution event and evalaute policy and record execution event
    private PolicyDecision recordEvent(ProcessAction action, PolicyEngine engine, List<ExecutionEvent> executionEvents) {
        PolicyDecision decision = engine.evaluate(action);
        ExecutionEvent event = new ExecutionEvent(
                action,
                decision,
                Instant.now()
        );
        executionEvents.add(event);
        return decision;
    }
}
