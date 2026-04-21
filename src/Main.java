import common.keygen.KeyGen;
import common.keygen.key.KeyPair;

import java.io.*;
import java.util.Scanner;
import java.util.Timer;

public class Main
{
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter writer = new PrintWriter(new OutputStreamWriter(System.out));
    private static final Scanner scanner = new Scanner(System.in);

    private static int bitLen = 1024;
    private static KeyPair keyPair = KeyGen.generate(bitLen);


    public static void main(String[] args) throws IOException
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
               |----|-------------------|
               |  1 |          Key info |
               |  2 |   Choose key size |
               |  3 | Generate key pair |
               |  4 | Validate key pair |
               |----|-------------------|
               |  5 |   Encrypt message |
               |  6 |   Decrypt message |
               |----|-------------------|
               |  7 |  Create signature |
               |  8 |  Verify signature |
               |----|-------------------|
               |  9 |             About |
               | 10 |              Exit |
               |----|-------------------|
               """);
    }

    private static void keySizeMenu()
    {
        int option;

        while (true)
        {
            System.out.println("""
                |---------------|
                |   Bit Length  |
                |---|-----------|
                | 1 | 1024 bits |
                | 2 | 2048 bits |
                | 3 | 3072 bits |
                | 4 | 4096 bits |
                |---|-----------|
                """);


            try {
                System.out.print("\n%user: ");
                option = Integer.parseInt(scanner.next());

                if (option >= 1 && option <= 4)
                {
                    break;
                }
            }
            catch (Exception ignored)
            {
            }
        }

        switch (option)
        {
            case 1 -> bitLen = 1024;
            case 2 -> bitLen = 2048;
            case 3 -> bitLen = 3072;
            case 4 -> bitLen = 4096;
        }
    }

    private static void keyValidationMenu()
    {

    }

    private static void encryptMenu()
    {
        System.out.println("""
                |------------------------------------|
                |              Encrypt               |
                |---|--------------------------------|
                | 1 | Using pre-generated public key |
                | 2 |            Using my public key |
                |---|--------------------------------
                | 3 | 
                | 4 | 
                
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



    private static void menuAction(int option)
    {
        switch (option)
        {
            case 1 -> showKeyPair();
            case 2 -> keySizeMenu();
            case 3 -> keyPair = KeyGen.generate(bitLen);
            case 4 -> keyValidationMenu();
            case 5 ->
            case 10 -> System.exit(0);
        }
    }
}
