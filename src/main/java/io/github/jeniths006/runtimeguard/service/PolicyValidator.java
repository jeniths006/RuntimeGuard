package io.github.jeniths006.runtimeguard.service;

import io.github.jeniths006.runtimeguard.exception.PolicyValidationException;
import io.github.jeniths006.runtimeguard.model.Policy;

public class PolicyValidator {

    public void validate(Policy policy) {

        if(policy == null) {
            throw new PolicyValidationException(
                    "Policy cannot be null"
            );
        }

        if(policy.filesystem() == null) {
            throw new PolicyValidationException(
                    "Missing filesystem policy"
            );
        }

        if(policy.network() == null) {
            throw new PolicyValidationException(
                    "Missing network policy"
            );
        }

        if(policy.process() == null) {
            throw new PolicyValidationException(
                    "Missing process policy"
            );
        }

    }
}
