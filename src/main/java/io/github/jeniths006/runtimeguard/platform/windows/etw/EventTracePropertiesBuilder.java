package io.github.jeniths006.runtimeguard.platform.windows.etw;

import com.sun.jna.Memory;
import com.sun.jna.WString;
import com.sun.jna.platform.win32.Guid;
import io.github.jeniths006.runtimeguard.platform.windows.nativeapi.ETWConstants;
import io.github.jeniths006.runtimeguard.platform.windows.nativeapi.structures.EventTraceProperties;
import io.github.jeniths006.runtimeguard.platform.windows.nativeapi.structures.NodeHeader;


import java.util.UUID;

import static com.sun.jna.Native.WCHAR_SIZE;

public class EventTracePropertiesBuilder {

    public final ETWSessionProperties build(WString sessionName) {
        int sessionNameBytes = (sessionName.toString().length() + 1) * WCHAR_SIZE;

        int propertiesSize = new EventTraceProperties().size();

        Memory memory = new Memory(propertiesSize + sessionNameBytes);

        EventTraceProperties properties = new EventTraceProperties(memory);
        properties.wnode.bufferSize = propertiesSize + sessionNameBytes;
        properties.wnode.clientContext = 1;
        properties.wnode.flags = ETWConstants.WNODE_FLAG_TRACED_GUID;
        properties.bufferSize = 64;
        properties.minimumBuffers = 5;
        properties.maximumBuffers = 200;
        properties.logFileMode = ETWConstants.EVENT_TRACE_REAL_TIME_MODE;
        properties.logFileNameOffset = 0;
        properties.loggerNameOffset = propertiesSize;



        memory.setWideString(propertiesSize, sessionName.toString());

        properties.write();

        return new ETWSessionProperties(memory, properties);

    }
}
