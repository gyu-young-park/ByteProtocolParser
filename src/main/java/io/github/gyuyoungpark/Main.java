package io.github.gyuyoungpark;

import io.github.gyuyoungpark.parser.byteprotocol.helper.ByteStringToBytesHelper;
import io.github.gyuyoungpark.parser.byteprotocol.parser.ByteProtocolParser;
import io.github.gyuyoungpark.parser.bytestring.parser.ByteStringParser;
import io.github.gyuyoungpark.parser.IByteParser;
import io.github.gyuyoungpark.data.NetworkCtrlData;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws Exception {
        String byteStringData = "0102000A00000001000000021F000000102025122514500000";
        NetworkCtrlData networkCtrlData = new NetworkCtrlData();

        IByteParser protocolParser = new ByteProtocolParser();
        protocolParser.parse(byteStringData, networkCtrlData);
        System.out.println(networkCtrlData);

        IByteParser byteStringParser = new ByteStringParser();
        byteStringParser.parse(byteStringData, networkCtrlData);
        System.out.println(networkCtrlData);
    }
}