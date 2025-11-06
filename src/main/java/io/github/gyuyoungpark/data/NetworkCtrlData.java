package io.github.gyuyoungpark.data;

import io.github.gyuyoungpark.parser.byteprotocol.annotation.ByteProtocolField;
import io.github.gyuyoungpark.parser.byteprotocol.annotation.ByteProtocolMessage;
import io.github.gyuyoungpark.parser.bytestring.annotation.ByteStringField;

/*
0102 000A 00000001 00000002 1F 00000010 2025122514500000
* */

@ByteProtocolMessage
public class NetworkCtrlData {
    @ByteStringField(length = 4)
    @ByteProtocolField(start = 0, length = 2)
    public int messageType; // 메시지 타입, 2 bytes

    @ByteStringField(length = 4)
    @ByteProtocolField(start = 2, length = 2)
    public int sequenceNumber; // 메시지 순서 번호, 2 bytes

    @ByteStringField(length = 8)
    @ByteProtocolField(start = 4, length = 4)
    public int sourceNodeId; // 송신 노드 ID, 4 bytes

    @ByteStringField(length = 8)
    @ByteProtocolField(start = 8, length = 4)
    public int destinationNodeId; // 수신 노드 ID, 4 bytes

    @ByteStringField(length = 2)
    @ByteProtocolField(start = 12, length = 1)
    public byte controlFlag; // 제어 플래그, 1 byte

    @ByteStringField(length = 8)
    @ByteProtocolField(start = 13, length = 4)
    public int payloadLength; // 페이로드 길이, 4 bytes

    @ByteStringField(length = 16)
    @ByteProtocolField(start = 17, length = 8)
    public String timestamp; // 패킷 생성 시간, 8 bytes

    @Override
    public String toString() {
        return "NetworkCtrlData{" +
                "messageType=" + messageType +
                ", sequenceNumber=" + sequenceNumber +
                ", sourceNodeId=" + sourceNodeId +
                ", destinationNodeId=" + destinationNodeId +
                ", controlFlag=" + controlFlag +
                ", payloadLength=" + payloadLength +
                ", timestamp=" + timestamp +
                '}';
    }
}

