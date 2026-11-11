public class Main {

    public static void main(String[] args) {

        if (args.length == 0) {
            printHelp();
            return;
        }

        Encryptor encryptor = new Encryptor();
        KeyGenerator keyGenerator = new KeyGenerator();

        String command = args[0];

        try {

            if (command.equalsIgnoreCase("encrypt")) {

                if (args.length < 3) {
                    System.out.println(
                            "Usage: encrypt <input> <output> [password]"
                    );
                    return;
                }

                String password;

                if (args.length >= 4) {

                    password = args[3];

                } else {

                    password = keyGenerator.generatePassword();

                    System.out.println(
                            "Generated password: " + password
                    );

                    System.out.println(
                            "Save this password. It is required for decryption."
                    );
                }

                encryptor.encryptFile(
                        args[1],
                        args[2],
                        password
                );

                System.out.println(
                        "File encrypted successfully"
                );

            } else if (command.equalsIgnoreCase("decrypt")) {

                if (args.length < 4) {

                    System.out.println(
                            "Usage: decrypt <input> <output> <password>"
                    );

                    return;
                }

                encryptor.decryptFile(
                        args[1],
                        args[2],
                        args[3]
                );

                System.out.println(
                        "File decrypted successfully"
                );

            } else {

                System.out.println(
                        "Unknown command: " + command
                );

                printHelp();
            }

        } catch (RuntimeException e) {

            System.out.println(
                    "Operation failed: " + e.getMessage()
            );
        }
    }

    private static void printHelp() {

        System.out.println("Java File Crypto");
        System.out.println();

        System.out.println("Usage:");

        System.out.println(
                "  encrypt <input> <output> [password]"
        );

        System.out.println(
                "  decrypt <input> <output> <password>"
        );
    }
}