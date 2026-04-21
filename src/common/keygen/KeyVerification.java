package common.keygen;

import common.keygen.key.KeyPair;
import common.math.Math;
import common.prime.Random;

import java.math.BigInteger;

public class KeyVerification {
    public static boolean verify(KeyPair keyPair)
    {
        BigInteger test = Random.generate(64);
        BigInteger encrypted = Math.modExp(test, keyPair.publicKey.e, keyPair.publicKey.n);
        BigInteger decrypted = Math.modExp(encrypted, keyPair.privateKey.d, keyPair.privateKey.n);

        return test.equals(decrypted);
    }
}
