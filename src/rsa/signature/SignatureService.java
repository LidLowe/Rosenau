package rsa.signature;

import java.math.BigInteger;

public class SignatureService {
    public static BigInteger HexToBigInt(String input)
    {
        return new BigInteger(input, 16);
    }

    public static String BigIntToHex(BigInteger input)
    {
        return input.toString(16);
    }
}
