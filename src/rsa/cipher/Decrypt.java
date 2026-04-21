package rsa.cipher;

import common.keygen.key.PrivateKey;
import common.math.Math;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Decrypt
{
    public static String decrypt(BigInteger[] c, PrivateKey pk)
    {
        int bitLen = pk.n.bitLength();
        StringBuilder m = new StringBuilder();

        for (int r = 0; r < c.length; r++)
        {
            byte[] block = Math.modExp(c[r], pk.d, pk.n).toByteArray();

            int separator = 2;
            for (int i = 2; i < block.length; i++)
            {
                if (block[i] == 0x00)
                {
                    separator = i;
                    break;
                }
            }

            byte[] message = Arrays.copyOfRange(block, separator + 1, block.length - 1);

            int end = message.length;
            while (end > 0 && message[end - 1] == 0x00)
            {
                end--;
            }

            m.append(new String(Arrays.copyOfRange(message, 0, end), StandardCharsets.UTF_8));
        }

        return m.toString();
    }
}
