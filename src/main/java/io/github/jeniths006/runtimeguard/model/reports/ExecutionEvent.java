package io.github.jeniths006.runtimeguard.model.reports;

import io.github.jeniths006.runtimeguard.model.PolicyDecision;
import io.github.jeniths006.runtimeguard.model.action.ProcessAction;

import java.time.Instant;

public record ExecutionEvent(
        ProcessAction processAction,
        PolicyDecision policyDecision,
        Instant instant
) {

    @Override
    public String toString() {
        return """
            Action: %s
            Decision: %s
            Time: %s
            """.formatted(
                    processAction,
                policyDecision,
                instant
        );
    }
}
