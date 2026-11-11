import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;

public class Encryptor {

    private static final int IV_LENGTH = 12;
    private static final int TAG_LENGTH = 128;

    public void encryptFile(
            String inputFile,
            String outputFile,
            String password
    ) {

        try {
            byte[] fileData = Files.readAllBytes(
                    Path.of(inputFile)
            );

            SecretKey key = createKey(password);

            byte[] iv = new byte[IV_LENGTH];

            SecureRandom secureRandom = new SecureRandom();
            secureRandom.nextBytes(iv);

            Cipher cipher =
                    Cipher.getInstance("AES/GCM/NoPadding");

            GCMParameterSpec gcmSpec =
                    new GCMParameterSpec(TAG_LENGTH, iv);

            cipher.init(
                    Cipher.ENCRYPT_MODE,
                    key,
                    gcmSpec
            );

            byte[] encryptedData =
                    cipher.doFinal(fileData);

            byte[] result =
                    new byte[iv.length + encryptedData.length];

            System.arraycopy(
                    iv,
                    0,
                    result,
                    0,
                    iv.length
            );

            System.arraycopy(
                    encryptedData,
                    0,
                    result,
                    iv.length,
                    encryptedData.length
            );

            Files.write(
                    Path.of(outputFile),
                    result
            );

        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException(
                    "Encryption failed",
                    e
            );
        }
    }

    public void decryptFile(
            String inputFile,
            String outputFile,
            String password
    ) {

        try {
            byte[] fileData =
                    Files.readAllBytes(
                            Path.of(inputFile)
                    );

            if (fileData.length <= IV_LENGTH) {
                throw new IllegalArgumentException(
                        "Invalid encrypted file"
                );
            }

            byte[] iv = new byte[IV_LENGTH];

            byte[] encryptedData =
                    new byte[fileData.length - IV_LENGTH];

            System.arraycopy(
                    fileData,
                    0,
                    iv,
                    0,
                    IV_LENGTH
            );

            System.arraycopy(
                    fileData,
                    IV_LENGTH,
                    encryptedData,
                    0,
                    encryptedData.length
            );

            SecretKey key = createKey(password);

            Cipher cipher =
                    Cipher.getInstance("AES/GCM/NoPadding");

            GCMParameterSpec gcmSpec =
                    new GCMParameterSpec(TAG_LENGTH, iv);

            cipher.init(
                    Cipher.DECRYPT_MODE,
                    key,
                    gcmSpec
            );

            byte[] decryptedData =
                    cipher.doFinal(encryptedData);

            Files.write(
                    Path.of(outputFile),
                    decryptedData
            );

        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException(
                    "Decryption failed",
                    e
            );
        }
    }

    private SecretKey createKey(String password)
            throws GeneralSecurityException {

        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        byte[] keyBytes = digest.digest(password.getBytes(
                                StandardCharsets.UTF_8
                        )
                );

        return new SecretKeySpec(
                keyBytes,
                "AES"
        );
    }
}