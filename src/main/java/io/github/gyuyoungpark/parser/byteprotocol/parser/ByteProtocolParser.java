package io.github.gyuyoungpark.parser.byteprotocol.parser;

import io.github.gyuyoungpark.parser.byteprotocol.annotation.ByteProtocolField;
import io.github.gyuyoungpark.parser.byteprotocol.annotation.ByteProtocolMessage;
import io.github.gyuyoungpark.parser.IByteParser;
import io.github.gyuyoungpark.parser.byteprotocol.helper.ByteStringToBytesHelper;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;

public class ByteProtocolParser implements IByteParser {

    public void parse(String data, Object instance) {
        byte[] bytes = ByteStringToBytesHelper.hexStringToBytes(data);
        Class<?> clazz = instance.getClass();

        if (!clazz.isAnnotationPresent(ByteProtocolMessage.class)) {
            throw new IllegalArgumentException(clazz.getName() + " is not a @ByteProtocolMessage");
        }

        for (Field field : clazz.getDeclaredFields()) {
            ByteProtocolField annotation = field.getAnnotation(ByteProtocolField.class);
            if (annotation == null) continue;

            int start = annotation.start();
            int length = annotation.length();

            if (start < 0 || start + length > bytes.length) {
                throw new IllegalArgumentException(String.format(
                        "Invalid range: field=%s start=%d length=%d (bytes len=%d)",
                        field.getName(), start, length, bytes.length
                ));
            }

            try {
                field.setAccessible(true);
                Object value = parseField(bytes, start, length, field.getType());
                field.set(instance, value);
            } catch (Exception e) {
                throw new RuntimeException("Failed to parse field " + field.getName(), e);
            }
        }
    }

    private Object parseField(byte[] bytes, int start, int length, Class<?> type) {
        if (type == String.class) {
            return new String(bytes, start, length, StandardCharsets.UTF_8).trim();
        }

        long numeric = 0;
        for (int i = 0; i < length; i++) {
            numeric = (numeric << 8) | (bytes[start + i] & 0xFF);
        }

        if (type == byte.class || type == Byte.class)
            return (byte) numeric;
        if (type == short.class || type == Short.class)
            return (short) numeric;
        if (type == int.class || type == Integer.class)
            return (int) numeric;
        if (type == long.class || type == Long.class)
            return numeric;
        if (type == boolean.class || type == Boolean.class)
            return numeric != 0;

        throw new IllegalArgumentException("Unsupported field type: " + type);
    }
}
