package io.github.gyuyoungpark.ByteProtocolParser.parser;

import io.github.gyuyoungpark.ByteProtocolParser.annotation.ByteProtocolField;
import io.github.gyuyoungpark.ByteProtocolParser.annotation.ByteProtocolMessage;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;

public class ByteProtocolParser implements IByteProtocolParser {
    public static void Parse(byte[] bytes, Object instance) {
        Class<?> clazz = instance.getClass();
        ByteProtocolMessage byteProtocolMessage = clazz.getAnnotation(ByteProtocolMessage.class);
        if (byteProtocolMessage == null) throw new IllegalArgumentException(clazz.getName() + " does not have byte protocol message annotation");

        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);;
        for (Field field: clazz.getFields()) {
            ByteProtocolField byteProtocolField = field.getAnnotation(ByteProtocolField.class);
            if (byteProtocolField == null) continue;
            field.setAccessible(true);

            int start = byteProtocolField.start();
            int length = byteProtocolField.length();
            byte[] buffer = new byte[length];
            byteBuffer.get(buffer, start, length);


        }
    }

}
