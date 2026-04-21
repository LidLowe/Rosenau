package rsa.cipher;

import java.math.BigInteger;

public class CipherService
{
    public static String BigIntArrayToString(BigInteger[] input)
    {
        StringBuilder output = new StringBuilder();

        for (BigInteger block : input)
        {
            output.append(block.toString(16));
        }

        return output.toString();
    }

    public static BigInteger[] StringToBigInt(String input, int bitLen)
    {
        int bitHexLen = bitLen / 4;
        BigInteger[] output = new BigInteger[input.length() / bitHexLen];

        for (int r = 0; r < output.length; r++)
        {
            output[r] = new BigInteger(input.substring(r * bitHexLen, r * bitHexLen + bitHexLen), 16);
        }

        return output;
    }
}
