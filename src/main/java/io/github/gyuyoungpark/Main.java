package io.github.gyuyoungpark;

import io.github.gyuyoungpark.ByteProtocolParser.helper.ByteStringToBytesHelper;
import io.github.gyuyoungpark.ByteProtocolParser.parser.ByteProtocolParser;
import io.github.gyuyoungpark.ByteProtocolParser.parser.IByteProtocolParser;
import io.github.gyuyoungpark.data.NetworkCtrlData;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        byte[] input = ByteStringToBytesHelper.hexStringToBytes("0102000A00000001000000021F000000102025122514500000");
        NetworkCtrlData networkCtrlData = new NetworkCtrlData();
        IByteProtocolParser protocolParser = new ByteProtocolParser();
        for(byte b: input) {
            System.out.println(b);
        }
        protocolParser.parse(input, networkCtrlData);
        System.out.println(networkCtrlData.toString());
    }
}