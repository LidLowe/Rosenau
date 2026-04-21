package common.keygen;

import common.keygen.key.KeyPair;
import common.keygen.key.PrivateKey;
import common.keygen.key.PublicKey;
import common.prime.PrimeRandom;
import common.math.Math;

import java.math.BigInteger;

public class KeyGen
{
    public static KeyPair generate(int bitLen)
    {
        BigInteger p;
        BigInteger q;
        BigInteger n;
        BigInteger phi;
        BigInteger e = new BigInteger("65537");

        do
        {
            do
            {
                p = PrimeRandom.generate(bitLen / 2);
                q = PrimeRandom.generate(bitLen / 2);

            } while (p.equals(q));

            n = p.multiply(q);
            phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        }
        while (!Math.gcd(e, phi).equals(BigInteger.ONE));

        BigInteger d = Math.modInv(e, phi);

        System.out.println(e.multiply(d).mod(phi));

        return new KeyPair(
                new PublicKey(n ,e),
                new PrivateKey(n ,d)
        );
    }
}
