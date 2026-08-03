package io.github.jeniths006.runtimeguard.service.interceptor;

import io.github.jeniths006.runtimeguard.model.PolicyDecision;
import io.github.jeniths006.runtimeguard.model.action.ProcessAction;

public interface ProcessActionListener {

    PolicyDecision onAction(ProcessAction action);
}
