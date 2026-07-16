package io.github.jeniths006.runtimeguard.cli.commands;
import io.github.jeniths006.runtimeguard.controller.SandboxController;
import io.github.jeniths006.runtimeguard.model.ExecutionCommand;
import io.github.jeniths006.runtimeguard.model.ProcessRequest;
import picocli.CommandLine;

@CommandLine.Command(
        name = "run",
        description = "Execute a program inside the RuntimeGuard sandbox."
)
public class RunCommand implements Runnable{
    @Override
    public void run() {
        ProcessRequest request = new ProcessRequest(
                ExecutionCommand.RUN,
                program,
                policyFile
        );

        SandboxController controller = new SandboxController();
        controller.execute(request);
    }

    @CommandLine.Parameters(index = "0", description = "Program to run")
    private String program;

    @CommandLine.Option(names = {"--policy"}, description = "Policy to use")
    private String policyFile;

}
