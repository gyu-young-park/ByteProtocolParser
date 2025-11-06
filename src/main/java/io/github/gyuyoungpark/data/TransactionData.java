package io.github.gyuyoungpark.data;

import io.github.gyuyoungpark.parser.byteprotocol.annotation.ByteProtocolField;
import io.github.gyuyoungpark.parser.byteprotocol.annotation.ByteProtocolMessage;

/*
* FFFFFFFF 0A EB01 FFFA 1 202512251647
*
* */
@ByteProtocolMessage
public class TransactionData {
    @ByteProtocolField(start = 0, length = 8)
    private String tid; // Transaction ID, 8 bytes

    @ByteProtocolField(start = 8, length = 2)
    private int sequenceNumber; // 메시지 순서 번호, 2 bytes

    @ByteProtocolField(start = 10, length = 4)
    private int amount; // 거래 금액, 4 bytes

    @ByteProtocolField(start = 14, length = 4)
    private int currencyCode; // 통화 코드, 4 bytes (예: USD=840)

    @ByteProtocolField(start = 18, length = 1)
    private byte status; // 상태 플래그, 1 byte

    @ByteProtocolField(start = 19, length = 8)
    private long timestamp; // 거래 시각, 8 bytes (epoch millis)
}
