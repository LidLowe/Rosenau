package common.prime;

import java.math.BigInteger;

import common.math.Math;

public class PrimeRandom
{
    public static BigInteger generate(int bitLen)
    {
        BigInteger n = Random.generate(bitLen);
        n = n.setBit(bitLen - 1);
        n = n.setBit(0);

        while ( !millerRabin(n, bitLen) )
        {
            n = Random.generate(bitLen);
            n = n.setBit(bitLen - 1);
            n = n.setBit(0);
        }

        return n;
    }

    private static boolean millerRabin(BigInteger n, int bitLen)
    {
        BigInteger r = prep(n)[0];
        BigInteger d = prep(n)[1];

        int iter;
        switch (bitLen)
        {
            case 512 -> iter = 40;
            default -> iter = 64;
        }

        start:
        for (int k = 0; k < iter; k++)
        {
            BigInteger a = Random.generate(bitLen);
            while (!(a.compareTo(BigInteger.TWO) >= 0 && a.compareTo(n.subtract(BigInteger.TWO)) <= 0))
            {
                a = Random.generate(bitLen);
            }

            BigInteger x = Math.modExp(a, d, n);
            if (x.equals(BigInteger.ONE) || x.equals(n.subtract(BigInteger.ONE)))
            {
                continue;
            }

            for (BigInteger i = BigInteger.ZERO; i.compareTo(r.subtract(BigInteger.ONE)) < 0; i = i.add(BigInteger.ONE))
            {
                x = x.pow(2).mod(n);

                if (x.equals(n.subtract(BigInteger.ONE)))
                {
                    continue start;
                }
            }

            return false;
        }

        return true;
    }

    private static BigInteger[] prep(BigInteger n)
    {
        n = n.subtract(BigInteger.ONE);
        BigInteger r = BigInteger.ZERO;

        while ( n.mod(BigInteger.TWO).equals(BigInteger.ZERO) )
        {
            n = n.divide(BigInteger.TWO);
            r = r.add(BigInteger.ONE);
        }

        return new BigInteger[]{r, n};
    }
}
