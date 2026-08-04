package io.github.jeniths006.runtimeguard.service.interceptor.factory;

import io.github.jeniths006.runtimeguard.platform.windows.WindowsETWInterceptor;
import io.github.jeniths006.runtimeguard.service.interceptor.ProcessInterceptor;

public final class PlatformInterceptorFactory {

    private PlatformInterceptorFactory() {

    }

    public static ProcessInterceptor create() {
        return new WindowsETWInterceptor();
    }
}
