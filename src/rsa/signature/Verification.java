package rsa.signature;

import common.math.Math;
import common.keygen.key.PublicKey;
import rsa.signature.core.Padding;
import rsa.signature.core.SHA256.SHA256;

import java.math.BigInteger;
import java.util.Arrays;

public class Verification
{
    public static boolean verify(String message, BigInteger signature, PublicKey pk)
    {
        BigInteger pm = Math.modExp(signature, pk.e, pk.n);

        byte[] hash = Padding.removePKCS(pm, pk.n.bitLength());
        byte[] computedHash = SHA256.hash(message);

        return Arrays.equals(hash, computedHash);
    }
}
