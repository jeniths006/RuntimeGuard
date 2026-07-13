package io.github.jeniths006.runtimeguard.model;

import io.github.jeniths006.runtimeguard.model.policy.FileSystemPolicy;
import io.github.jeniths006.runtimeguard.model.policy.NetworkPolicy;
import io.github.jeniths006.runtimeguard.model.policy.ProcessPolicy;

public record Policy(
        FileSystemPolicy filesystem,
        NetworkPolicy network,
        ProcessPolicy process
) {
}
