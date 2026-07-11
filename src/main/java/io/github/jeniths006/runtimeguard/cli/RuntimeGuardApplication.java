package io.github.jeniths006.runtimeguard.cli;

import picocli.CommandLine;

public class RuntimeGuardApplication {

    public static void main(String[] args) {
        System.out.println("RuntimeGuard starting...");

        new CommandLine(new RuntimeGuardCommand()).execute(args);


    }
}
