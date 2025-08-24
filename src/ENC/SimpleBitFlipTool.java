package ENC;

import java.util.Arrays;
import java.util.Base64;

public class SimpleBitFlipTool {

    public static void main(String[] args) {
        // INPUT: Provide your IV, original text, and encrypted text here
        String originalText = "Hello World 1234";
        String ivHex = "0123456789abcdef0123456789abcdef";  // 32 hex chars = 16 bytes
        String encryptedHex = "a1b2c3d4e5f6071819202122232425267890abcdef123456789012345678901234";  // Example encrypted data

        System.out.println("=== Simple AES CBC Bit Flip Tool ===");
        System.out.println("Original text: " + originalText);
        System.out.println("IV (hex): " + ivHex);
        System.out.println("Encrypted (hex): " + encryptedHex);

        // Convert inputs
        byte[] iv = hexToBytes(ivHex);
        byte[] encrypted = hexToBytes(encryptedHex);

        System.out.println("\n=== Bit Flipping Examples ===");

//        // Example 1: Flip single bit
//        byte[] modified1 = flipBitInCiphertext(encrypted, 0, 0);  // Flip first bit of first byte
//        System.out.println("1. Flipped bit 0 of byte 0:");
//        System.out.println("   Modified encrypted: " + bytesToHex(modified1));
//
//        // Example 2: Flip multiple bits
//        byte[] modified2 = Arrays.copyOf(encrypted, encrypted.length);
//        modified2 = flipBitInCiphertext(modified2, 1, 3);  // Flip bit 3 of byte 1
//        modified2 = flipBitInCiphertext(modified2, 5, 7);  // Flip bit 7 of byte 5
//        System.out.println("2. Flipped multiple bits:");
//        System.out.println("   Modified encrypted: " + bytesToHex(modified2));

        // Example 3: Target specific plaintext change (requires knowing original)
        String targetText = "suman suman suma";  // Change 'H' to 'J'
        byte[] modified3 = createTargetedFlip(originalText, targetText, iv, encrypted);
        if (modified3 != null) {
            System.out.println("3. Targeted flip to change 'Hello' to 'Jello':");
            System.out.println("   Modified encrypted: " + bytesToHex(modified3));
        }

//        // Example 4: Byte-level modification
//        byte[] modified4 = modifyByteInCiphertext(encrypted, 2, (byte)0xFF);
//        System.out.println("4. Set byte 2 to 0xFF:");
//        System.out.println("   Modified encrypted: " + bytesToHex(modified4));
//
//        // Show what each modification would affect in plaintext
//        System.out.println("\n=== Understanding the Effects ===");
//        explainBitFlipEffect(originalText, 0, 0);
//        explainBitFlipEffect(originalText, 1, 3);
    }

    /**
     * Flip a specific bit in the ciphertext
     * @param ciphertext The encrypted data
     * @param byteIndex Which byte to modify (0-based)
     * @param bitIndex Which bit in that byte to flip (0-7, where 0 is LSB)
     * @return Modified ciphertext
     */
    public static byte[] flipBitInCiphertext(byte[] ciphertext, int byteIndex, int bitIndex) {
        byte[] result = Arrays.copyOf(ciphertext, ciphertext.length);

        if (byteIndex >= 0 && byteIndex < result.length && bitIndex >= 0 && bitIndex < 8) {
            result[byteIndex] ^= (1 << bitIndex);
            System.out.println("   Flipped bit " + bitIndex + " in byte " + byteIndex +
                    " (0x" + String.format("%02X", ciphertext[byteIndex]) +
                    " -> 0x" + String.format("%02X", result[byteIndex]) + ")");
        } else {
            System.out.println("   Invalid bit position: byte " + byteIndex + ", bit " + bitIndex);
        }

        return result;
    }

    /**
     * Modify a complete byte in the ciphertext
     * @param ciphertext The encrypted data
     * @param byteIndex Which byte to modify
     * @param newValue New value for the byte
     * @return Modified ciphertext
     */
    public static byte[] modifyByteInCiphertext(byte[] ciphertext, int byteIndex, byte newValue) {
        byte[] result = Arrays.copyOf(ciphertext, ciphertext.length);

        if (byteIndex >= 0 && byteIndex < result.length) {
            byte oldValue = result[byteIndex];
            result[byteIndex] = newValue;
            System.out.println("   Modified byte " + byteIndex +
                    " (0x" + String.format("%02X", oldValue) +
                    " -> 0x" + String.format("%02X", newValue) + ")");
        } else {
            System.out.println("   Invalid byte index: " + byteIndex);
        }

        return result;
    }

    /**
     * Create a targeted bit flip to change original text to target text
     * This works by XORing the difference between original and target with the IV or previous ciphertext block
     */
    public static byte[] createTargetedFlip(String originalText, String targetText, byte[] iv, byte[] ciphertext) {
        if (originalText.length() != targetText.length()) {
            System.out.println("   ERROR: Original and target text must be same length");
            return null;
        }

        byte[] result = Arrays.copyOf(ciphertext, ciphertext.length);
        byte[] original = originalText.getBytes();
        byte[] target = targetText.getBytes();

        System.out.println("   Calculating modifications needed:");

        // For each character difference, modify the corresponding position in IV or previous block
        for (int i = 0; i < original.length && i < 16; i++) {  // Only first block
            if (original[i] != target[i]) {
                byte diff = (byte)(original[i] ^ target[i]);

                // Modify IV to affect first plaintext block
                if (i < iv.length) {
                    // For demonstration, we show what IV modification would be needed
                    // (Note: in real attack, you'd need to control the IV or previous ciphertext block)
                    System.out.println("   Position " + i + ": '" + (char)original[i] +
                            "' -> '" + (char)target[i] +
                            "' requires IV[" + i + "] XOR 0x" + String.format("%02X", diff));
                }
            }
        }

        // For blocks after the first, modify the previous ciphertext block
        for (int i = 16; i < original.length && i < target.length; i++) {
            if (original[i] != target[i]) {
                byte diff = (byte)(original[i] ^ target[i]);
                int ciphertextPos = i - 16;  // Position in first ciphertext block

                if (ciphertextPos < result.length) {
                    result[ciphertextPos] ^= diff;
                    System.out.println("   Position " + i + ": '" + (char)original[i] +
                            "' -> '" + (char)target[i] +
                            "' modified ciphertext[" + ciphertextPos + "]");
                }
            }
        }

        return result;
    }

    /**
     * Explain what effect a bit flip would have on the plaintext
     */
    public static void explainBitFlipEffect(String originalText, int byteIndex, int bitIndex) {
        // In CBC mode, flipping a bit in ciphertext block N affects:
        // 1. The corresponding bit in plaintext block N+1
        // 2. Garbles the entire plaintext block N

        System.out.println("   Flipping bit " + bitIndex + " in ciphertext byte " + byteIndex + " will:");
        System.out.println("   - Garble the entire plaintext block containing byte " + byteIndex);
        System.out.println("   - Flip bit " + bitIndex + " in plaintext byte " + (byteIndex + 16) +
                " (if it exists)");

        if (byteIndex + 16 < originalText.length()) {
            char originalChar = originalText.charAt(byteIndex + 16);
            char modifiedChar = (char)(originalChar ^ (1 << bitIndex));
            System.out.println("   - Character at position " + (byteIndex + 16) +
                    " would change from '" + originalChar +
                    "' to '" + modifiedChar + "'");
        }
    }

    // Utility methods
    public static byte[] hexToBytes(String hex) {
        if (hex.length() % 2 != 0) {
            hex = "0" + hex;  // Add leading zero if odd length
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

    public static void printBytes(String label, byte[] bytes) {
        System.out.println(label + ": " + bytesToHex(bytes));
    }

    // Additional utility: XOR two byte arrays
    public static byte[] xorBytes(byte[] a, byte[] b) {
        int length = Math.min(a.length, b.length);
        byte[] result = new byte[length];

        for (int i = 0; i < length; i++) {
            result[i] = (byte)(a[i] ^ b[i]);
        }

        return result;
    }
}