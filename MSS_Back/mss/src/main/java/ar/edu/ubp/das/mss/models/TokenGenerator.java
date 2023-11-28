package ar.edu.ubp.das.mss.models;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class TokenGenerator {
    public static String generateToken(String content) throws NoSuchAlgorithmException {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[16];
        secureRandom.nextBytes(randomBytes);
        String contentWithRandom = content + bytesToHexString(randomBytes);
        byte[] hashedBytes = hashSHA256(contentWithRandom);
        return bytesToHexString(hashedBytes);
    }

    private static byte[] hashSHA256(String content) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return digest.digest(content.getBytes(StandardCharsets.UTF_8));
    }

    private static String bytesToHexString(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static String generateTransactionId() {
        SecureRandom secureRandom = new SecureRandom();
        final int TRANSACTION_ID_LENGTH = 16;
        final String ALLOWED_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder(TRANSACTION_ID_LENGTH);
        for (int i = 0; i < TRANSACTION_ID_LENGTH; i++) {
            int randomIndex = secureRandom.nextInt(ALLOWED_CHARS.length());
            char randomChar = ALLOWED_CHARS.charAt(randomIndex);
            sb.append(randomChar);
        }
        return sb.toString();
    }
}