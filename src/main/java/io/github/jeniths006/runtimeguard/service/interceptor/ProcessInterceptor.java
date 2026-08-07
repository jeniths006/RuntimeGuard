package io.github.jeniths006.runtimeguard.service.interceptor;

public interface ProcessInterceptor {

    void observe(Process process, ProcessActionListener processActionListener);

    void stop();
}
