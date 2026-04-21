package rsa.cipher;

import common.keygen.key.PublicKey;
import common.math.Math;
import rsa.cipher.core.padding.Padding;

import java.math.BigInteger;

public class Encrypt
{
    public static BigInteger[] encrypt(String p, PublicKey pk, int bitLen)
    {
        BigInteger[] m = Padding.generatePKCS(p, bitLen);

        BigInteger[] c = new BigInteger[m.length];
        for (int r = 0; r < m.length; r++)
        {
            c[r] = Math.modExp(m[r], pk.e, pk.n);
        }

        return c;
    }
}
