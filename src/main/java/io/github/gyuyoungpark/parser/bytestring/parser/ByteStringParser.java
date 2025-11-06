package io.github.gyuyoungpark.parser.bytestring.parser;

import io.github.gyuyoungpark.parser.IByteParser;
import io.github.gyuyoungpark.parser.bytestring.annotation.ByteStringField;

import java.lang.reflect.Field;

public class ByteStringParser implements IByteParser {
    public void parse(String data, Object instance) {
        parseRecursive(data.toCharArray(), 0, instance);
    }

    private int parseRecursive(char[] data, int offset, Object instance){
        int current = offset;
        Class<?> clazz = instance.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            ByteStringField annotation = field.getAnnotation(ByteStringField.class);
            if (annotation == null) continue;

            field.setAccessible(true);

            if (annotation.nested()) {
                current = parseRecursive(data, current, field.getType());
            } else {
                int len = annotation.length();
                if (current + len > data.length) {
                    throw new IllegalArgumentException("Data too short for field: " + field.getName());
                }

                Object parsed = convertValue(data, current, len, field.getType());
                try {
                    field.set(instance, parsed);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                current += len;
            }
        }

        return current;
    }

    private Object convertValue(char[] data, int offset, int len, Class<?> type) {
        if (type == String.class) {
            // 문자열로 그대로 리턴
            return new String(data, offset, len);
        }

        // 16진수를 숫자로 변환
        long value = 0;
        for (int i = 0; i < len; i++) {
            char c = data[offset + i];
            int digit = Character.digit(c, 16);
            if (digit == -1) throw new IllegalArgumentException("Invalid hex char: " + c);
            value = (value << 4) | digit;
        }

        if (type == int.class || type == Integer.class) return (int) value;
        if (type == short.class || type == Short.class) return (short) value;
        if (type == byte.class || type == Byte.class) return (byte) value;
        if (type == long.class || type == Long.class) return value;

        throw new IllegalArgumentException("Unsupported field type: " + type);
    }

    public record ParseResult<T>(T result, int nextOffset) {
    }
}
