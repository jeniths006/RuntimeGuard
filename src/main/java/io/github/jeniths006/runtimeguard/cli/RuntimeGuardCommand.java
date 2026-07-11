package io.github.jeniths006.runtimeguard.cli;

import io.github.jeniths006.runtimeguard.cli.commands.RunCommand;
import picocli.CommandLine;

@CommandLine.Command(
        name = "runtimeguard",
        description = "Policy-Based Execution Sandbox",
        mixinStandardHelpOptions = true,
        subcommands = {
                RunCommand.class
        }
)
public class RuntimeGuardCommand implements Runnable{
    @Override
    public void run() {
        System.out.println("Welcome to RuntimeGuard");
    }
}
