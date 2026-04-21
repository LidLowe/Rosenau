package common.prime;

import java.math.BigInteger;

/**
 * Генерация случайного числа без гарантии праймовости
 */
public class Random
{
    private static long seed;

    static {
        long seedN1 = System.nanoTime();
        long seedN2 = Thread.currentThread().getId();
        long seedN3 = Runtime.getRuntime().freeMemory();
        seed = seedN1 ^ seedN2 ^ seedN3;

        for (int i = 0; i < 99; i++)
        {
            seed ^= (seed << 13);
            seed ^= (seed >>> 17);
            seed ^= (seed << 5);
        }
    }

    public static BigInteger generate(int bitLen)
    {
        byte[] result = new byte[bitLen / 8];

        for (int i = 0; i < result.length; i++)
        {
            seed ^= (seed << 13);
            seed ^= (seed >>> 17);
            seed ^= (seed << 5);

            result[i] = (byte) (seed & 0xFF);
        }

        return new BigInteger(1, result);
    }
}