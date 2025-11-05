package io.github.gyuyoungpark.ByteProtocolParser.parser;

public interface IByteProtocolParser {
    void parse(byte[] bytes, Object instance);
}
