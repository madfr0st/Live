package ENC;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class UniversalCBCBitFlip1 {

    public static void main(String[] args) {
        // Example: two equal-length strings
        String originalText = "puts....FLAG..";   // length 14
        String targetText   = "puts '#{FLAG}'";   // length 14 (matches)

        // Example ciphertext (dummy hex, 16-byte IV + ciphertext)
        String enc = "7928a20006fa697124fa37b12a42638dee4ca4ca0c0f3e5ecb95e824efde25de1d4077689e2a0a2b4cf89e572ebd3b72";

        String ivHex = enc.substring(0, 32);    // first 16 bytes (32 hex chars)
        String encryptedHex = enc.substring(32); // rest is ciphertext

        System.out.println("=== Universal CBC Bit Flip Tool ===");
        System.out.println("Original: \"" + originalText + "\" (" + originalText.length() + ")");
        System.out.println("Target:   \"" + targetText + "\" (" + targetText.length() + ")");

        if (originalText.length() != targetText.length()) {
            System.out.println("ERROR: Strings must be same length!");
            return;
        }

        // Convert
        byte[] iv = hexToBytes(ivHex);
        byte[] encrypted = hexToBytes(encryptedHex);

        // Run calculation
        BitFlipResult result = performUniversalBitFlip(originalText, targetText, iv, encrypted);

        // Show results
        System.out.println("\n=== RESULTS ===");
        System.out.println("\nOriginal Encrypted:        " + ivHex+encryptedHex);
        System.out.println("Modified Encrypted:        " + bytesToHex(result.modifiedIV)+bytesToHex(result.modifiedEncrypted));

    }

    public static BitFlipResult performUniversalBitFlip(String original, String target,
                                                        byte[] iv, byte[] encrypted) {
        byte[] modifiedIV = Arrays.copyOf(iv, iv.length);
        byte[] modifiedEncrypted = Arrays.copyOf(encrypted, encrypted.length);

        byte[] originalBytes = original.getBytes(StandardCharsets.UTF_8);
        byte[] targetBytes   = target.getBytes(StandardCharsets.UTF_8);

        for (int pos = 0; pos < original.length(); pos++) {
            if (originalBytes[pos] != targetBytes[pos]) {
                byte diff = (byte) (originalBytes[pos] ^ targetBytes[pos]);
                int blockNum = pos / 16;
                int posInBlock = pos % 16;

                if (blockNum == 0) {
                    modifiedIV[posInBlock] ^= diff;
                } else {
                    int cipherBlockStart = (blockNum - 1) * 16;
                    int cipherPos = cipherBlockStart + posInBlock;
                    if (cipherPos < modifiedEncrypted.length) {
                        modifiedEncrypted[cipherPos] ^= diff;
                    }
                }
            }
        }
        return new BitFlipResult(modifiedIV, modifiedEncrypted);
    }

    // Result holder
    public static class BitFlipResult {
        public final byte[] modifiedIV;
        public final byte[] modifiedEncrypted;
        public BitFlipResult(byte[] iv, byte[] encrypted) {
            this.modifiedIV = iv;
            this.modifiedEncrypted = encrypted;
        }
    }

    // Helpers
    public static byte[] hexToBytes(String hex) {
        byte[] result = new byte[hex.length() / 2];
        for (int i = 0; i < result.length; i++) {
            result[i] = (byte) Integer.parseInt(hex.substring(i * 2, i * 2 + 2), 16);
        }
        return result;
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02X", b));
        }
        return result.toString();
    }
}