package io.github.gyuyoungpark.parser.byteprotocol.helper;

public class ByteStringToBytesHelper {
    public static byte[] hexStringToBytes(String hex) {
        int len = hex.length();
        if ((len & 1) != 0) {
            throw new IllegalArgumentException("Hex string must have even length");
        }

        byte[] result = new byte[len >> 1];
        char[] chars = hex.toCharArray();

        for (int i = 0, j = 0; j < result.length; i += 2, j++) {
            int hi = hexDigit(chars[i]);
            int lo = hexDigit(chars[i + 1]);
            result[j] = (byte) ((hi << 4) | lo);
        }

        return result;
    }

    private static int hexDigit(char ch) {
        if (ch >= '0' && ch <= '9') return ch - '0';
        if (ch >= 'A' && ch <= 'F') return ch - 'A' + 10;
        if (ch >= 'a' && ch <= 'f') return ch - 'a' + 10;
        throw new IllegalArgumentException("Invalid hex char: " + ch);
    }
}
