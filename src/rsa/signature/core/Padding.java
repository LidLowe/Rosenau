package rsa.signature.core;

import java.math.BigInteger;
import java.util.Arrays;

public class Padding
{
    private static final byte[] digestInfo = new byte[]{
            0x30, 0x31, 0x30, 0x0d, 0x06, 0x09, 0x60,
            (byte) 0x86, 0x48, 0x01, 0x65, 0x03, 0x04,
            0x02, 0x01, 0x05, 0x00, 0x04, 0x20
    };

    public static byte[] generatePKCS(byte[] hash, int bitLen)
    {
        byte[] pm = new byte[bitLen / 8];

        pm[0] = 0x00;
        pm[1] = 0x01;

        int xffLen = (bitLen / 8 - 3 - digestInfo.length - hash.length) + 2;
        for (int i = 2; i < xffLen; i++)
        {
            pm[i] = (byte) 0xFF;
        }

        pm[xffLen] = 0x00;

        System.arraycopy(digestInfo, 0, pm, xffLen + 1, digestInfo.length);
        System.arraycopy(hash, 0, pm, xffLen + 1 + digestInfo.length, hash.length);

        return pm;
    }

    public static byte[] removePKCS(BigInteger input, int bitLen)
    {
        byte[] pm = input.toByteArray();

        int indicator = 0;
        for (int i = 2; i < pm.length; i++)
        {
            if (pm[i] == 0x00)
            {
                indicator = i + 1;
                break;
            }
        }

        for (int i = 0; i < digestInfo.length; i++)
        {
            if (pm[i + indicator] != digestInfo[i])
            {
                return new byte[]{};
            }
        }

        return Arrays.copyOfRange(pm, indicator + digestInfo.length, pm.length);
    }
}
