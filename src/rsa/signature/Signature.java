package rsa.signature;

import common.math.Math;
import common.keygen.key.PrivateKey;
import rsa.signature.core.Padding;
import rsa.signature.core.SHA256.SHA256;

import java.math.BigInteger;

public class Signature
{
    public static BigInteger sign(String message, PrivateKey pk)
    {
        byte[] hash = SHA256.hash(message);
        BigInteger paddedMessage = new BigInteger(1, Padding.generatePKCS(hash, pk.n.bitLength()));

        return Math.modExp(paddedMessage, pk.d, pk.n);
    }
}
