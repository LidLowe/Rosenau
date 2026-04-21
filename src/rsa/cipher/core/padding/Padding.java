package rsa.cipher.core.padding;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

import common.prime.Random;

public class Padding
{
    /**
     * Генерация паддинга для увличение энтропии шифртекста
     * Генерирует двумерный массив байтов для всего сообщения независимо от его длинны
     * @param plaintext
     * @param bitLen
     */
    public static BigInteger[] generatePKCS(String plaintext, int bitLen)
    {
        byte[] message = plaintext.getBytes(StandardCharsets.UTF_8);

        int blockLen = bitLen / 8 - 11;
        int blockNum = message.length / blockLen;

        if (message.length % blockLen != 0)
        {
            blockNum++;
        }

        byte[][] paddedMessage = new byte[blockNum][blockLen + 11];

        for (int r = 0; r < blockNum; r++)
        {
            /*
             * Заполняем первые 11 ячеек
             */
            paddedMessage[r][0] = 0x00;
            paddedMessage[r][1] = 0x02;

            for (int c = 2; c < 10; c++)
            {
                byte b;
                do {
                    b = Random.generate(64).byteValue();
                } while (b == 0);

                paddedMessage[r][c] = b;
            }

            paddedMessage[r][10] = 0x00;

            /*
             * Копируем часть сообщения в блок
             */
            for (int c = 0; c < blockLen; c++)
            {
                if (r * blockLen + c< message.length)
                {
                    paddedMessage[r][c + 11] = message[r * blockLen + c];
                }
                else
                {
                    paddedMessage[r][c + 11] = 0x00;
                }
            }
        }

        return ByteDoubleArrayToBigIntArray(paddedMessage);
    }

    private static BigInteger[] ByteDoubleArrayToBigIntArray(byte[][] input)
    {
        BigInteger[] output = new BigInteger[input.length];

        for (int r = 0; r < input.length; r++)
        {
            output[r] = new BigInteger(1, input[r]);
        }

        return output;
    }
}
