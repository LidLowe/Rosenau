package common.keygen.key;

import java.math.BigInteger;

public class PublicKey
{
    public final BigInteger n;
    public final BigInteger e;

    public PublicKey(BigInteger n, BigInteger e)
    {
        this.n = n;
        this.e = e;
    }
}
