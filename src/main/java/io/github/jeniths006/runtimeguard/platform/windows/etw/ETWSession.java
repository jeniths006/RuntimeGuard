package io.github.jeniths006.runtimeguard.platform.windows.etw;

import com.sun.jna.WString;
import com.sun.jna.platform.win32.BaseTSD;
import io.github.jeniths006.runtimeguard.platform.windows.nativeapi.Advapi32DLL;
import io.github.jeniths006.runtimeguard.platform.windows.nativeapi.ETWConstants;
import io.github.jeniths006.runtimeguard.platform.windows.nativeapi.structures.EventTraceProperties;
import io.github.jeniths006.runtimeguard.platform.windows.nativeapi.structures.NodeHeader;

public class ETWSession {


    private final BaseTSD.ULONG_PTRByReference sessionHandle= new BaseTSD.ULONG_PTRByReference();
    private final WString sessionName = new WString("RuntimeGuardSession");
    private final EventTracePropertiesBuilder propertiesBuilder = new EventTracePropertiesBuilder();


    public void start() {
        ETWSessionProperties sessionProperties = propertiesBuilder.build(sessionName);

        int result = Advapi32DLL.INSTANCE.StartTraceW(
                sessionHandle,
                sessionName,
                sessionProperties.eventTraceProperties
        );

        if (result != 0) {
            throw new RuntimeException(
                    "StartTraceW failed. Error: " + result
            );
        }

        System.out.println("ETW Session started");


    }

    public void stop() {

    }
}
