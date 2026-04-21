import common.keygen.KeyGen;
import common.keygen.KeyVerification;
import common.keygen.key.KeyPair;
import common.keygen.key.PrivateKey;
import common.keygen.key.PublicKey;
import rsa.cipher.CipherService;
import rsa.cipher.Decrypt;
import rsa.cipher.Encrypt;
import rsa.signature.Signature;
import rsa.signature.SignatureService;
import rsa.signature.Verification;

import java.io.*;
import java.math.BigInteger;
import java.util.Scanner;

public class Main
{
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final Scanner scanner = new Scanner(System.in);

    private static int bitLen = 1024;
    private static KeyPair keyPair = KeyGen.generate(bitLen);


    public static void main(String[] args)
    {
        System.out.println("Welcome to Rosenau...\n");

        while (true)
        {
            menu();

            System.out.print("\n%user: ");
            int option = Integer.parseInt(scanner.next());
            System.out.println();

            menuAction(option);
        }
    }

    private static void menu()
    {
        System.out.print("""
               
               |------------------------|
               |          Menu          |
               |------------------------|
               |  1 |          Key info |
               |  2 |   Choose key size |
               |  3 | Generate key pair |
               |  4 | Validate key pair |
               |------------------------|
               |  5 |   Encrypt message |
               |  6 |   Decrypt message |
               |------------------------|
               |  7 |  Create signature |
               |  8 |  Verify signature |
               |------------------------|
               |  9 |             About |
               | 10 |              Exit |
               |------------------------|
               
               """);
    }

    private static void showKeyPair()
    {
        System.out.printf("""
                
                Public key:
                    n:
                        %s
                    e:
                        %s
                        
                Private key:
                    n:
                        %s
                    d:
                        %s
                        
                Bit length:
                    %s
                
                
                """,
                keyPair.publicKey.n, keyPair.publicKey.e, keyPair.privateKey.n, keyPair.privateKey.d, bitLen);
    }

    private static void chooseKeySizeMenu()
    {
        int option;

        while (true)
        {
            System.out.println("""
                |---------------|
                |   Bit Length  |
                |---------------|
                | 1 | 1024 bits |
                | 2 | 2048 bits |
                | 3 | 3072 bits |
                | 4 | 4096 bits |
                |---------------|
                """);

            try
            {
                System.out.print("\n%user: ");
                option = Integer.parseInt(scanner.next());

                if (option >= 1 && option <= 4)
                {
                    break;
                }
            }
            catch (Exception ignored) {}
        }

        switch (option)
        {
            case 1 -> bitLen = 1024;
            case 2 -> bitLen = 2048;
            case 3 -> bitLen = 3072;
            case 4 -> bitLen = 4096;
        }

        keyPair = KeyGen.generate(bitLen);
    }

    private static void keyValidationMenu()
    {
        PublicKey publicKey = null;
        PrivateKey privateKey = null;

        try
        {
            System.out.print("%n: ");
            BigInteger n = new BigInteger(reader.readLine());

            System.out.print("\n%e: ");
            BigInteger e = new BigInteger(reader.readLine());

            System.out.println("\n%d: ");
            BigInteger d = new BigInteger(reader.readLine());



            publicKey = new PublicKey(n, e);
            privateKey = new PrivateKey(n, d);
        }
        catch (Exception ignored) {}

        if (KeyVerification.verify(new KeyPair(publicKey, privateKey)))
        {
            System.out.println("Status: Valid");
        }
        else
        {
            System.out.println("Status: Invalid");
        }
    }

    private static void encryptMenu()
    {
        String message = "";
        int padding = 1;
        PublicKey publicKey = keyPair.publicKey;

        int option;
        option:
        while (true)
        {
            System.out.println("""
                
                |------------------------------------|
                |              Encrypt               |
                |------------------------------------|
                | 1 | Using pre-generated public key |
                | 2 |            Using my public key |
                |------------------------------------|
                | 3 |                 Padding PKCS#1 |
                | 4 |                   Padding OAEP |
                |------------------------------------|
                | 5 |                  Enter message |
                |------------------------------------|
                | 6 |                        Proceed |
                |------------------------------------|
                
                """);

            try {
                System.out.print("\n%user: ");
                option = Integer.parseInt(scanner.next());

                switch (option)
                {
                    case 1 -> publicKey = keyPair.publicKey;
                    case 2 ->
                    {
                        try {
                            System.out.print("%n: ");
                            BigInteger n = new BigInteger(reader.readLine());

                            System.out.print("\n%e: ");
                            BigInteger e = new BigInteger(reader.readLine());

                            publicKey = new PublicKey(n, e);
                        }
                        catch (Exception ignored) {}
                    }
                    case 3 -> padding = 1;
                    case 4 -> padding = 2;
                    case 5->
                    {
                        System.out.print("%message: ");
                        message = reader.readLine();
                    }
                    case 6 ->
                    {
                        if (!message.isEmpty())
                        {
                            System.out.printf("""
                                    
                                    Encrypted message:
                                        %s
                                        
                                    """, CipherService.BigIntArrayToString(Encrypt.encrypt(message, publicKey)));
                            break option;
                        }
                    }
                }
            }
            catch (Exception ignored) {}
        }
    }

    private static void decryptMenu()
    {
        PrivateKey privateKey = keyPair.privateKey;
        String message = "";

        int option;
        option:
        while (true)
        {
            System.out.println("""
                
                |-------------------------------------|
                |               Decrypt               |
                |-------------------------------------|
                | 1 | Using pre-generated private key |
                | 2 |            Using my private key |
                |-------------------------------------|
                | 3 |                   Enter message |
                |-------------------------------------|
                | 4 |                         Proceed |
                |-------------------------------------|
                
                """);

            try {
                System.out.print("\n%user: ");
                option = Integer.parseInt(scanner.next());

                switch (option)
                {
                    case 1 -> privateKey = keyPair.privateKey;
                    case 2 ->
                    {
                        try {
                            System.out.print("%n: ");
                            BigInteger n = new BigInteger(reader.readLine());

                            System.out.print("\n%d: ");
                            BigInteger d = new BigInteger(reader.readLine());

                            privateKey = new PrivateKey(n, d);
                        }
                        catch (Exception ignored) {}
                    }
                    case 3->
                    {
                        System.out.print("%message: ");
                        message = reader.readLine();
                    }
                    case 4 ->
                    {
                        if (!message.isEmpty())
                        {
                            System.out.printf("""
                                    
                                    Decrypted message:
                                        %s
                                        
                                    """, Decrypt.decrypt(CipherService.StringToBigInt(message, privateKey.n.bitLength()), privateKey));
                            break option;
                        }
                    }
                }
            }
            catch (Exception ignored) {}
        }
    }

    private static void signatureMenu()
    {
        String message = "";
        PrivateKey privateKey = keyPair.privateKey;

        int option;
        option:
        while (true)
        {
            System.out.println("""
                
                |-------------------------------------|
                |                Sign                 |
                |-------------------------------------|
                | 1 | Using pre-generated private key |
                | 2 |            Using my private key |
                |-------------------------------------|
                | 3 |                   Enter message |
                |-------------------------------------|
                | 4 |                         Proceed |
                |-------------------------------------|
                
                """);

            try {
                System.out.print("\n%user: ");
                option = Integer.parseInt(scanner.next());

                switch (option)
                {
                    case 1 -> privateKey = keyPair.privateKey;
                    case 2 ->
                    {
                        try {
                            System.out.print("%n: ");
                            BigInteger n = new BigInteger(reader.readLine());

                            System.out.print("\n%d: ");
                            BigInteger d = new BigInteger(reader.readLine());

                            privateKey = new PrivateKey(n, d);
                        }
                        catch (Exception ignored) {}
                    }
                    case 3 ->
                    {
                        System.out.print("%message: ");
                        message = reader.readLine();
                    }
                    case 4 ->
                    {
                        if (!message.isEmpty())
                        {
                            System.out.printf("""
                                    
                                    Signature:
                                        %s
                                        
                                    """, SignatureService.BigIntToHex(Signature.sign(message, privateKey)));
                            break option;
                        }
                    }
                }
            }
            catch (Exception ignored) {}
        }
    }

    private static void verifyMenu()
    {
        String message = "";
        String signature = "";
        PublicKey publicKey = keyPair.publicKey;

        int option;
        option:
        while (true) {
            System.out.println("""
                                    
                    |------------------------------------|
                    |              Verify                |
                    |------------------------------------|
                    | 1 | Using pre-generated public key |
                    | 2 |            Using my public key |
                    |------------------------------------|
                    | 3 |                  Enter message |
                    | 4 |                Enter signature |
                    |------------------------------------|
                    | 5 |                        Proceed |
                    |------------------------------------|
                                    
                    """);

            try {
                System.out.print("\n%user: ");
                option = Integer.parseInt(scanner.next());

                switch (option) {
                    case 1 -> publicKey = keyPair.publicKey;
                    case 2 -> {
                        try {
                            System.out.print("%n: ");
                            BigInteger n = new BigInteger(reader.readLine());

                            System.out.print("\n%e: ");
                            BigInteger e = new BigInteger(reader.readLine());

                            publicKey = new PublicKey(n, e);
                        } catch (Exception ignored) {
                        }
                    }
                    case 3 -> {
                        System.out.print("%message: ");
                        message = reader.readLine();
                    }
                    case 4 -> {
                        System.out.print("%signature: ");
                        signature = reader.readLine();
                    }
                    case 5 -> {
                        if (!message.isEmpty() && !signature.isEmpty()) {
                            System.out.printf("""
                                                                        
                                    Status: %s
                                        
                                    """, Verification.verify(message, SignatureService.HexToBigInt(signature), publicKey));
                            break option;
                        }
                    }
                }
            } catch (Exception ignored) {
            }
        }

    }

    private static void menuAction(int option)
    {
        switch (option)
        {
            case 1 -> showKeyPair();
            case 2 -> chooseKeySizeMenu();
            case 3 -> keyPair = KeyGen.generate(bitLen);
            case 4 -> keyValidationMenu();
            case 5 -> encryptMenu();
            case 6 -> decryptMenu();
            case 7 -> signatureMenu();
            case 8 -> verifyMenu();
            case 9 -> System.out.println("LidLowe, 2026");
            case 10 -> System.exit(0);
        }
    }
}
