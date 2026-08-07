package io.github.jeniths006.runtimeguard.platform.windows;

import io.github.jeniths006.runtimeguard.platform.windows.etw.ETWSession;
import io.github.jeniths006.runtimeguard.service.interceptor.ProcessActionListener;
import io.github.jeniths006.runtimeguard.service.interceptor.ProcessInterceptor;

public class WindowsETWInterceptor implements ProcessInterceptor {

    private final ETWSession etwSession = new ETWSession();

    private long targetPid;

    @Override
    public void observe(Process process, ProcessActionListener processActionListener) {

        targetPid = process.pid();

        etwSession.start(targetPid, processActionListener);

    }
    @Override
    public void stop() {
        etwSession.stop();
    }
}
