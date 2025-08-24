package ENC;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class CBCBitFlipCalculator {

    public static void main(String[] args) {
        // INPUT: Your encrypted data
        String encryptedHex = "7454aa1a16428fdbfc9d2b6135c7b7fdb777fa15b7f447f23da1d553c1ff28c2101aa3b2929fe44b74fb72166bba5fca7e2c87c780ba1569ac245491986c39ba";

        // What you need to know: the original plaintext that was encrypted
        // This MUST be exactly what was encrypted to produce the above ciphertext
        String originalPlaintext = "x.eq.1.semicolonputs.FLAG.helloo"; // 32 chars - REPLACE WITH ACTUAL ORIGINAL!

        // What we want it to decrypt to
        String targetPlaintext = "x=1;            puts '#{FLAG}'  "; // 32 chars - comment hides corruption

        System.out.println("=== CBC Bit Flip Calculator ===");
        System.out.println("Input encrypted hex: " + encryptedHex);
        System.out.println("Original plaintext:  \"" + originalPlaintext + "\" (" + originalPlaintext.length() + " chars)");
        System.out.println("Target plaintext:    \"" + targetPlaintext + "\" (" + targetPlaintext.length() + " chars)");

        if (originalPlaintext.length() != targetPlaintext.length()) {
            System.out.println("ERROR: Original and target must be same length!");
            return;
        }

        // Calculate the modified encrypted hex
        String modifiedHex = calculateModifiedEncryption(encryptedHex, originalPlaintext, targetPlaintext);

        System.out.println("\n=== RESULTS ===");
        System.out.println("Original encrypted: " + encryptedHex);
        System.out.println("Modified encrypted: " + modifiedHex);
        System.out.println("\nUse the modified encrypted hex in your attack!");
    }

    /**
     * Calculate the modified encrypted data needed to change plaintext
     */
    public static String calculateModifiedEncryption(String encryptedHex, String original, String target) {
        // Parse the encrypted data
        if (encryptedHex.length() < 64) { // At least IV (32) + one block (32) = 64 hex chars
            throw new IllegalArgumentException("Encrypted data too short");
        }

        String ivHex = encryptedHex.substring(0, 32);      // First 16 bytes (32 hex chars)
        String cipherHex = encryptedHex.substring(32);     // Rest is ciphertext

        byte[] iv = hexToBytes(ivHex);
        byte[] ciphertext = hexToBytes(cipherHex);

        System.out.println("\nParsing encrypted data:");
        System.out.println("IV:         " + ivHex + " (" + iv.length + " bytes)");
        System.out.println("Ciphertext: " + cipherHex + " (" + ciphertext.length + " bytes)");
        System.out.println("Blocks:     " + (ciphertext.length / 16) + " ciphertext blocks");

        // Convert strings to bytes
        byte[] originalBytes = original.getBytes(StandardCharsets.UTF_8);
        byte[] targetBytes = target.getBytes(StandardCharsets.UTF_8);

        // Create modified versions
        byte[] modifiedIV = Arrays.copyOf(iv, iv.length);
        byte[] modifiedCiphertext = Arrays.copyOf(ciphertext, ciphertext.length);

        System.out.println("\n=== Calculating Bit Flips ===");

        // Process each character position
        for (int pos = 0; pos < original.length(); pos++) {
            if (originalBytes[pos] != targetBytes[pos]) {
                byte diff = (byte)(originalBytes[pos] ^ targetBytes[pos]);
                int blockNum = pos / 16;
                int posInBlock = pos % 16;

                char origChar = (char)originalBytes[pos];
                char targChar = (char)targetBytes[pos];

                System.out.println("Position " + pos + " (Block " + blockNum + ", offset " + posInBlock + "):");
                System.out.println("  '" + origChar + "' (0x" + String.format("%02X", originalBytes[pos] & 0xFF) +
                        ") → '" + targChar + "' (0x" + String.format("%02X", targetBytes[pos] & 0xFF) +
                        ") XOR: 0x" + String.format("%02X", diff & 0xFF));

                if (blockNum == 0) {
                    // Modify IV to affect first plaintext block
                    byte oldVal = modifiedIV[posInBlock];
                    modifiedIV[posInBlock] ^= diff;
                    System.out.println("  → Modify IV[" + posInBlock + "]: 0x" +
                            String.format("%02X", oldVal & 0xFF) + " → 0x" +
                            String.format("%02X", modifiedIV[posInBlock] & 0xFF));

                } else if (blockNum <= ciphertext.length / 16) {
                    // Modify previous ciphertext block to affect this plaintext block
                    int cipherBlockStart = (blockNum - 1) * 16;
                    int cipherPos = cipherBlockStart + posInBlock;

                    if (cipherPos < modifiedCiphertext.length) {
                        byte oldVal = modifiedCiphertext[cipherPos];
                        modifiedCiphertext[cipherPos] ^= diff;
                        System.out.println("  → Modify Cipher[" + cipherPos + "]: 0x" +
                                String.format("%02X", oldVal & 0xFF) + " → 0x" +
                                String.format("%02X", modifiedCiphertext[cipherPos] & 0xFF));
                    } else {
                        System.out.println("  → ERROR: Cipher position " + cipherPos + " out of bounds!");
                    }
                } else {
                    System.out.println("  → ERROR: Not enough ciphertext blocks for position " + pos);
                }
            }
        }

        // Combine modified IV and ciphertext
        String modifiedIVHex = bytesToHex(modifiedIV);
        String modifiedCipherHex = bytesToHex(modifiedCiphertext);
        String result = modifiedIVHex + modifiedCipherHex;

        System.out.println("\n=== Modification Summary ===");
        System.out.println("Original IV:         " + ivHex);
        System.out.println("Modified IV:         " + modifiedIVHex);
        System.out.println("Original Ciphertext: " + cipherHex);
        System.out.println("Modified Ciphertext: " + modifiedCipherHex);

        return result;
    }

    /**
     * Easy-to-use function - just provide your encrypted hex and desired target
     */
    public static void quickCalculation() {
        System.out.println("\n=== QUICK CALCULATION EXAMPLE ===");

        // Example usage - replace with your actual values
        String yourEncrypted = "7928A20008F3642424FA37B1794B638DEE4CA4CA0C0F3E5ECB95E824EFDE25DE1D4077689E2A0A2B4CF89E572EBD3B72";

        // Common scenarios for original plaintext (you need to know what was actually encrypted)
        String[] possibleOriginals = {
                "Hello.World123456789TestMessage123", // 32 chars
                "Message123456789.TestData.123456789", // 32 chars
                "ValidText123456789.HelloWorld123456", // 32 chars
                "TestString123456789.Message12345678"  // 32 chars
        };

        String target = "#xxxxxxxxxxxxxx puts '#{FLAG}' "; // 32 chars

        System.out.println("Your encrypted: " + yourEncrypted);
        System.out.println("Target result:  \"" + target + "\"");
        System.out.println();
        System.out.println("Try each possible original until you find the right one:");

        for (int i = 0; i < possibleOriginals.length; i++) {
            String orig = possibleOriginals[i];
            System.out.println("\n" + (i+1) + ". If original was: \"" + orig + "\"");

            try {
                String modified = calculateModifiedEncryption(yourEncrypted, orig, target);
                System.out.println("   Modified encrypted: " + modified);
            } catch (Exception e) {
                System.out.println("   Error: " + e.getMessage());
            }
        }
    }

    /**
     * Interactive mode - input your own values
     */
    public static void interactiveMode(String encrypted, String original, String target) {
        System.out.println("\n=== INTERACTIVE MODE ===");
        System.out.println("Encrypted: " + encrypted);
        System.out.println("Original:  \"" + original + "\"");
        System.out.println("Target:    \"" + target + "\"");

        if (original.length() != target.length()) {
            System.out.println("ERROR: Original and target must be same length!");
            return;
        }

        String result = calculateModifiedEncryption(encrypted, original, target);
        System.out.println("\nFinal Result: " + result);
        System.out.println("\nCopy this hex and use it in your attack!");
    }

    // Utility methods
    public static byte[] hexToBytes(String hex) {
        if (hex.length() % 2 != 0) {
            throw new IllegalArgumentException("Hex string must have even length");
        }

        byte[] result = new byte[hex.length() / 2];
        for (int i = 0; i < result.length; i++) {
            int index = i * 2;
            result[i] = (byte) Integer.parseInt(hex.substring(index, index + 2), 16);
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