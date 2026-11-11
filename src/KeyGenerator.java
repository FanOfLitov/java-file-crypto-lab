import java.security.SecureRandom;

public class KeyGenerator {

    private static final String UPPERCASE =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static final String LOWERCASE =
            "abcdefghijklmnopqrstuvwxyz";

    private static final String DIGITS =
            "0123456789";

    private static final String SYMBOLS =
            "!@#$%^&*()-_=+";

    private static final String ALL_CHARACTERS =
            UPPERCASE
                    + LOWERCASE
                    + DIGITS
                    + SYMBOLS;

    private static final int PASSWORD_LENGTH = 16;

    private final SecureRandom secureRandom =
            new SecureRandom();

    public String generatePassword() {

        StringBuilder password = new StringBuilder();
        password.append(randomCharacter(UPPERCASE));
        password.append(randomCharacter(LOWERCASE));

        password.append(randomCharacter(DIGITS));

        password.append(randomCharacter(SYMBOLS));

        while (password.length() < PASSWORD_LENGTH) {

            password.append(randomCharacter(ALL_CHARACTERS));
        }
        return password.toString();
    }

    private char randomCharacter(String characters) {

        int index = secureRandom.nextInt(characters.length());
        return characters.charAt(index);
    }
}