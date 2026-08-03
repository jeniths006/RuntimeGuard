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
import io.github.jeniths006.runtimeguard.service.interceptor.ProcessActionListener;

public class SandboxController implements ProcessActionListener{

    private PolicyEngine policyEngine;
    private List<ExecutionEvent> executionEvents;

    private final PolicyLoader policyLoader = new PolicyLoader();
    private final PolicyValidator policyValidator = new PolicyValidator();
    private final ProcessRunner processRunner = new ProcessRunner();
    private final ProcessMonitor processMonitor = new ProcessMonitor();

    @Override
    public PolicyDecision onAction(ProcessAction processAction) {
        PolicyDecision policyDecison = policyEngine.evaluate(processAction);
        ExecutionEvent event = new ExecutionEvent(
                processAction,
                policyDecison,
                Instant.now()
        );
        executionEvents.add(event);
        return policyDecison;
    }

    public void execute(ProcessRequest request) {
        System.out.println("Executing " + request.action() + " on " + request.target());
        System.out.println("Loading policy file: " + request.policyPath());
        this.executionEvents = new ArrayList<>();


        Path policyPath = Path.of(request.policyPath());

        try {

            //Loading policy
            Policy policy = policyLoader.load(policyPath);
            System.out.println("Policy loaded successfully");

            //Validate Policy file
            policyValidator.validate(policy);
            System.out.println("Policy validated successfully");



            //Create a new Process Action with type of process action and target
            ProcessAction processAction = new ProcessAction(ActionType.PROCESS_SPAWN, request.target());


            //Evaluate policy to come to a decision ALLOW/DENY
            PolicyDecision policyDecision = onAction(processAction);


            //Terminate process if DENY
            if(policyDecision == PolicyDecision.DENY) {
                System.out.println("Policy denied process execution.");
                return;
            }

            //Create Policy Engine
            this.policyEngine = new PolicyEngine(policy);

            //Test Data
            ProcessAction fileReadAction = new ProcessAction(ActionType.FILE_READ, "C:\\temp\\secret.txt");
            onAction(fileReadAction);


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

}
