package io.github.jeniths006.runtimeguard.cli.commands;
import picocli.CommandLine;

@CommandLine.Command(
        name = "run",
        description = "Execute a program inside the RuntimeGuard sandbox."
)
public class RunCommand implements Runnable{
    @Override
    public void run() {
        System.out.println("Target: " + program);
        System.out.println("Policy File: " + policyFile);
    }

    @CommandLine.Parameters(index = "0", description = "Program to run")
    private String program;

    @CommandLine.Option(names = {"--policy"}, description = "Policy to use")
    private String policyFile;

}
