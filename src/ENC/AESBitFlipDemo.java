package ENC;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

public class AESBitFlipDemo {

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";

    public static void main(String[] args) {
        try {
            // Example usage
            String originalText = "Hello World 12341234";

            // Generate key and IV
            SecretKey key = generateKey();
            byte[] iv = generateIV();

            System.out.println("=== AES-128 CBC Bit Flipping Demo ===");
            System.out.println("Original text: " + originalText);

            // Encrypt the original text
            byte[] encrypted = encrypt(originalText, key, iv);
            System.out.println("Encrypted (Base64): " + Base64.getEncoder().encodeToString(encrypted));

            // Decrypt to verify
            String decrypted = decrypt(encrypted, key, iv);
            System.out.println("Decrypted: " + decrypted);

            // Demonstrate bit flipping
            System.out.println("\n=== Bit Flipping Attack ===");

            // Let's flip some bits in the first block of ciphertext
            // This will affect the second block when decrypted
            byte[] modifiedEncrypted = Arrays.copyOf(encrypted, encrypted.length);

            // Flip bit at position 0 of the first ciphertext block (after IV)
            // This will modify the corresponding position in the second plaintext block
            if (modifiedEncrypted.length > 16) {
                modifiedEncrypted[16] ^= 0x01; // Flip the least significant bit
                System.out.println("Flipped bit at position 16 (first bit of first ciphertext block)");
            }

            // Decrypt the modified ciphertext
            String modifiedDecrypted = decrypt(modifiedEncrypted, key, iv);
            System.out.println("Modified decrypted: " + modifiedDecrypted);

            // Show the difference
            showDifference(originalText, modifiedDecrypted);

            // Interactive bit flipping
            System.out.println("\n=== Interactive Bit Flipping ===");
            System.out.println("You can use the methods below to perform custom bit flipping:");

            // Example of targeted bit flipping to change specific characters
            byte[] targetFlip = performTargetedBitFlip(originalText, "Hello World 56784545", key, iv);
            if (targetFlip != null) {
                String targetResult = decrypt(targetFlip, key, iv);
                System.out.println("Targeted flip result: " + targetResult);
            }

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static SecretKey generateKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        keyGenerator.init(128);
        return keyGenerator.generateKey();
    }

    public static byte[] generateIV() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return iv;
    }

    public static byte[] encrypt(String plaintext, SecretKey key, byte[] iv) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);

        byte[] plaintextBytes = plaintext.getBytes("UTF-8");
        byte[] encrypted = cipher.doFinal(plaintextBytes);

        // Prepend IV to encrypted data
        byte[] result = new byte[iv.length + encrypted.length];
        System.arraycopy(iv, 0, result, 0, iv.length);
        System.arraycopy(encrypted, 0, result, iv.length, encrypted.length);

        return result;
    }

    public static String decrypt(byte[] encryptedWithIV, SecretKey key, byte[] iv) throws Exception {
        // Extract IV and ciphertext
        byte[] actualIV = Arrays.copyOfRange(encryptedWithIV, 0, 16);
        byte[] ciphertext = Arrays.copyOfRange(encryptedWithIV, 16, encryptedWithIV.length);

        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        IvParameterSpec ivSpec = new IvParameterSpec(actualIV);
        cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);

        try {
            byte[] decrypted = cipher.doFinal(ciphertext);
            return new String(decrypted, "UTF-8");
        } catch (Exception e) {
            return "[DECRYPTION ERROR: " + e.getMessage() + "]";
        }
    }

    public static byte[] flipBit(byte[] data, int position, int bitIndex) {
        byte[] result = Arrays.copyOf(data, data.length);
        if (position < result.length && bitIndex >= 0 && bitIndex < 8) {
            result[position] ^= (1 << bitIndex);
        }
        return result;
    }

    public static byte[] performTargetedBitFlip(String original, String target, SecretKey key, byte[] iv) {
        try {
            if (original.length() != target.length()) {
                System.out.println("Original and target must be same length for targeted flip");
                return null;
            }

            // Encrypt original
            byte[] encrypted = encrypt(original, key, iv);
            byte[] modified = Arrays.copyOf(encrypted, encrypted.length);

            // Calculate XOR difference
            for (int i = 0; i < original.length() && i + 16 < modified.length; i++) {
                byte diff = (byte)(original.charAt(i) ^ target.charAt(i));
                modified[i + 16] ^= diff; // Apply to ciphertext (skip IV)
            }

            System.out.println("Attempting to change '" + original + "' to '" + target + "'");
            return modified;

        } catch (Exception e) {
            System.err.println("Error in targeted bit flip: " + e.getMessage());
            return null;
        }
    }

    private static void showDifference(String original, String modified) {
        System.out.println("\nCharacter-by-character comparison:");
        int maxLen = Math.max(original.length(), modified.length());

        for (int i = 0; i < maxLen; i++) {
            char origChar = i < original.length() ? original.charAt(i) : ' ';
            char modChar = i < modified.length() ? modified.charAt(i) : ' ';

            if (origChar != modChar) {
                System.out.println("Position " + i + ": '" + origChar + "' -> '" + modChar +
                        "' (0x" + Integer.toHexString(origChar) + " -> 0x" + Integer.toHexString(modChar) + ")");
            }
        }
    }

    // Utility method to convert hex string to bytes
    public static byte[] hexStringToBytes(String hex) {
        int len = hex.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i+1), 16));
        }
        return data;
    }

    // Utility method to convert bytes to hex string
    public static String bytesToHexString(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }
}