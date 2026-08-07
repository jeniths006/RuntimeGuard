package io.github.jeniths006.runtimeguard.platform.windows.etw.decoder;

import io.github.jeniths006.runtimeguard.platform.windows.nativeapi.structures.EventRecord;

public class ProcessEventDecoder {

    public ProcessEvent decode(EventRecord record) {

        int opcode = Byte.toUnsignedInt(record.eventHeader.eventDescriptor.opcode);

        long pid = extractPid(record, opcode);

        return switch (opcode) {
            case 2 -> new ProcessEvent(pid, extractImageName(record), ProcessEventType.START);
            case 11 -> new ProcessEvent(pid, extractImageName(record), ProcessEventType.STOP);
            default -> null;
        };
    }

    private long extractPid(EventRecord record, int opcode) {

        if(record.userData == null) {
            return -1;
        }

        return switch (opcode) {
            case 2 -> Integer.toUnsignedLong(record.userData.getInt(8));
            case 11 -> Integer.toUnsignedLong(record.userData.getInt(0));
            default -> -1;
        };
    }

    private String extractImageName(EventRecord record) {

        if(record.userData == null) {
            return "unknown";
        }

        //Image offset at 0x5c
        byte[] bytes = record.userData.getByteArray(0x5c, 256);

        String value = new String(bytes, java.nio.charset.StandardCharsets.UTF_16LE);

        int nullIndex = value.indexOf('\0');

        if (nullIndex != 1) {
            return value.substring(0, nullIndex);
        }

        return value;
    }
}
