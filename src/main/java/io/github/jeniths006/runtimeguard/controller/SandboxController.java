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
import io.github.jeniths006.runtimeguard.platform.windows.WindowsETWInterceptor;
import io.github.jeniths006.runtimeguard.platform.windows.nativeapi.Kernel32DLL;
import io.github.jeniths006.runtimeguard.service.*;
import io.github.jeniths006.runtimeguard.service.interceptor.ProcessActionListener;
import io.github.jeniths006.runtimeguard.service.interceptor.ProcessInterceptor;
import io.github.jeniths006.runtimeguard.service.interceptor.factory.PlatformInterceptorFactory;

public class SandboxController implements ProcessActionListener{

    private PolicyEngine policyEngine;
    private List<ExecutionEvent> executionEvents;

    private final PolicyLoader policyLoader = new PolicyLoader();
    private final PolicyValidator policyValidator = new PolicyValidator();
    private final ProcessRunner processRunner = new ProcessRunner();
    private final ProcessMonitor processMonitor = new ProcessMonitor();
    private final ProcessInterceptor processInterceptor = PlatformInterceptorFactory.create();

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

            //Create Policy Engine
            this.policyEngine = new PolicyEngine(policy);

            //Evaluate policy to come to a decision ALLOW/DENY
            PolicyDecision policyDecision = onAction(processAction);

            //Terminate process if DENY
            if(policyDecision == PolicyDecision.DENY) {
                System.out.println("Policy denied process execution.");
                return;
            }

            //Test Data
            ProcessAction fileReadAction = new ProcessAction(ActionType.FILE_READ, "C:\\temp\\secret.txt");
            onAction(fileReadAction);

            //Start process
            Process process = processRunner.start(request.target());

            Thread interceptorThread = new Thread(() ->
                    processInterceptor.observe(process, this)
            );

            interceptorThread.start();

            //Start monitoring process
            ProcessMonitorResult processMonitorResult = processMonitor.monitor(process);

            processInterceptor.stop();

            //Create execution report
            ExecutionReport executionReport = new ExecutionReport(
                    processMonitorResult.pid(),
                    request.target(),
                    processMonitorResult.exitCode(),
                    processMonitorResult.processInfo(),
                    processMonitorResult.executionTime(),
                    executionEvents
            );

            //Print execution report
            System.out.println(executionReport);

            System.out.println("Current JVM PID: " + Kernel32DLL.INSTANCE.GetCurrentProcessId());

        } catch (PolicyLoadException | PolicyValidationException e) {
           e.printStackTrace();
        }
    }

}
