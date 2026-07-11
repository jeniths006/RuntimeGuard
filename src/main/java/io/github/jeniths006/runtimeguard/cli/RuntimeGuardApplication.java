package io.github.jeniths006.runtimeguard.cli;

import io.github.jeniths006.runtimeguard.controller.SandboxController;
import io.github.jeniths006.runtimeguard.model.ExecutionAction;
import io.github.jeniths006.runtimeguard.model.ExecutionRequest;

public class RuntimeGuardApplication {

    public static void main(String[] args) {
        System.out.println("RuntimeGuard starting...");

        ExecutionRequest request = new ExecutionRequest(
                ExecutionAction.RUN,
                "malware.exe",
                "policy.json"
        );

        SandboxController controller = new SandboxController();
        controller.execute(request);


    }
}
