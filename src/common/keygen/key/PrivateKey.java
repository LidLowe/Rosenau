package common.keygen.key;

import java.math.BigInteger;

public class PrivateKey
{
    public final BigInteger n;
    public final BigInteger d;

    public PrivateKey(BigInteger n, BigInteger d)
    {
        this.n = n;
        this.d = d;
    }


}
