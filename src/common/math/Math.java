package common.math;

import java.math.BigInteger;

public class Math
{
    /**
     * Наибольший общии делитель
     */
    public static BigInteger gcd(BigInteger a, BigInteger b)
    {
        while ( !a.equals(BigInteger.ZERO) && !b.equals(BigInteger.ZERO) )
        {
            if ( a.compareTo(b) > 0 )
            {
                a = a.mod(b);
            }
            else
            {
                b = b.mod(a);
            }
        }

        return a.add(b);
    }


    public static BigInteger modExp(BigInteger base, BigInteger exponent, BigInteger modulus)
    {
        BigInteger result = BigInteger.ONE;
        base = base.mod(modulus);

        while ( exponent.compareTo( BigInteger.ZERO ) > 0)
        {
            if ( exponent.mod( BigInteger.TWO ).equals( BigInteger.ONE ) )
            {
                result = result.multiply(base).mod(modulus);
            }
            
            exponent = exponent.shiftRight(1);
            base = base.multiply(base).mod(modulus);
        }

        return result;
    }

    /**
     * Расширенная Евклидова формула
     * @param a
     * @param m
     * @return
     */
    public static BigInteger modInv(BigInteger a, BigInteger m)
    {
        BigInteger x = a;
        BigInteger x1 = m;

        BigInteger y = BigInteger.ONE;
        BigInteger y1 = BigInteger.ZERO;

        while ( !x1.equals(BigInteger.ZERO) )
        {
            BigInteger q = x.divide(x1);

            BigInteger temp = x1;
            x1 = x.subtract(q.multiply(x1));
            x = temp;

            temp = y1;
            y1 = y.subtract(q.multiply(y1));
            y = temp;
        }

        if (y.compareTo(BigInteger.ZERO) < 0)
        {
            y = y.add(m);
        }

        return y;
    }
}
