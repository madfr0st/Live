package ENC;

import java.util.Arrays;

public class UniversalCBCBitFlip {

    public static void main(String[] args) {
        // INPUT: Your data here
        String originalText = "puts....FLAG..";
        String targetText   = "puts '#{FLAG}'"; // Same length!
        String enc = "08527bf022e1c87cf308c7d4ccd006123a3ea955760f8d09fa0de3ffc834371bb49bd1e635b650e275f8d85a7a0d5cfe";

        String ivHex = enc.substring(0,32);
        System.out.println(ivHex.length());
        String encryptedHex = enc.substring(32);

        System.out.println("=== Universal CBC Bit Flip Tool ===");
        System.out.println("Original: \"" + originalText + "\"");
        System.out.println("Target:   \"" + targetText + "\"");
        System.out.println("Length check: " + originalText.length() + " -> " + targetText.length());

        if (originalText.length() != targetText.length()) {
            System.out.println("ERROR: Strings must be same length!");
            return;
        }

        // Convert inputs
        byte[] iv = hexToBytes(ivHex);
        byte[] encrypted = hexToBytes(encryptedHex);

        // Show block analysis
        showBlockAnalysis(originalText, targetText);

        // Perform the universal bit flip
        BitFlipResult result = performUniversalBitFlip(originalText, targetText, iv, encrypted);

        System.out.println("\n=== RESULTS ===");

        System.out.println("\nOriginal Encrypted:        " + ivHex+encryptedHex);
        System.out.println("Modified Encrypted:        " + bytesToHex(result.modifiedIV)+bytesToHex(result.modifiedEncrypted));

    }

    /**
     * Universal bit flip function - changes any characters at any positions
     * keeping the same total length
     */
    public static BitFlipResult performUniversalBitFlip(String original, String target,
                                                        byte[] iv, byte[] encrypted) {

        if (original.length() != target.length()) {
            throw new IllegalArgumentException("Strings must be same length");
        }

        byte[] modifiedIV = Arrays.copyOf(iv, iv.length);
        byte[] modifiedEncrypted = Arrays.copyOf(encrypted, encrypted.length);
        byte[] originalBytes = original.getBytes();
        byte[] targetBytes = target.getBytes();

        System.out.println("\n=== CALCULATING MODIFICATIONS ===");

        // Process each character position
        for (int pos = 0; pos < original.length(); pos++) {
            if (originalBytes[pos] != targetBytes[pos]) {

                byte diff = (byte)(originalBytes[pos] ^ targetBytes[pos]);
                int blockNum = pos / 16;
                int posInBlock = pos % 16;

                System.out.println("\nPosition " + pos + " (Block " + blockNum + ", offset " + posInBlock + "):");
                System.out.println("  Change: '" + (char)originalBytes[pos] + "' -> '" +
                        (char)targetBytes[pos] + "' (XOR: 0x" +
                        String.format("%02X", diff & 0xFF) + ")");

                if (blockNum == 0) {
                    // First block - modify IV
                    modifiedIV[posInBlock] ^= diff;
                    System.out.println("  Action: Modify IV[" + posInBlock + "] = 0x" +
                            String.format("%02X", iv[posInBlock] & 0xFF) +
                            " -> 0x" + String.format("%02X", modifiedIV[posInBlock] & 0xFF));

                } else {
                    // Later blocks - modify previous ciphertext block
                    int cipherBlockStart = (blockNum - 1) * 16;
                    int cipherPos = cipherBlockStart + posInBlock;

                    if (cipherPos < modifiedEncrypted.length) {
                        modifiedEncrypted[cipherPos] ^= diff;
                        System.out.println("  Action: Modify Ciphertext[" + cipherPos + "] = 0x" +
                                String.format("%02X", encrypted[cipherPos] & 0xFF) +
                                " -> 0x" + String.format("%02X", modifiedEncrypted[cipherPos] & 0xFF));
                    } else {
                        System.out.println("  ERROR: Cipher position " + cipherPos + " out of bounds");
                    }
                }
            }
        }

        return new BitFlipResult(modifiedIV, modifiedEncrypted);
    }

    /**
     * Show which characters belong to which blocks
     */
    public static void showBlockAnalysis(String original, String target) {
        System.out.println("\n=== BLOCK ANALYSIS ===");

        int totalBlocks = (original.length() + 15) / 16; // Round up

        for (int block = 0; block < totalBlocks; block++) {
            int start = block * 16;
            int end = Math.min(start + 16, original.length());

            String origBlock = original.substring(start, end);
            String targBlock = target.substring(start, end);

            System.out.println("Block " + block + " (positions " + start + "-" + (end-1) + "):");
            System.out.println("  Original: \"" + String.format("%-16s", origBlock) + "\"");
            System.out.println("  Target:   \"" + String.format("%-16s", targBlock) + "\"");

            // Show differences in this block
            boolean hasDiff = false;
            for (int i = 0; i < origBlock.length(); i++) {
                if (origBlock.charAt(i) != targBlock.charAt(i)) {
                    if (!hasDiff) {
                        System.out.println("  Changes:");
                        hasDiff = true;
                    }
                    System.out.println("    Pos " + (start + i) + ": '" +
                            origBlock.charAt(i) + "' -> '" + targBlock.charAt(i) + "'");
                }
            }

            if (!hasDiff) {
                System.out.println("  No changes in this block");
            }

            // Explain what needs to be modified
            if (hasDiff) {
                if (block == 0) {
                    System.out.println("  → Will modify: IV");
                } else {
                    System.out.println("  → Will modify: Ciphertext block " + (block-1));
                }
            }

            System.out.println();
        }
    }


    /**
     * Utility: Change a substring at specific position
     */
    public static BitFlipResult changeSubstring(String original,
                                                int startPos,
                                                String newSubstring,
                                                byte[] iv,
                                                byte[] encrypted) {

        if (startPos < 0 || startPos + newSubstring.length() > original.length()) {
            throw new IllegalArgumentException("Substring replacement out of bounds");
        }

        // Build target string
        StringBuilder targetBuilder = new StringBuilder(original);
        for (int i = 0; i < newSubstring.length(); i++) {
            targetBuilder.setCharAt(startPos + i, newSubstring.charAt(i));
        }

        String target = targetBuilder.toString();
        System.out.println("Built target string: \"" + target + "\"");

        return performUniversalBitFlip(original, target, iv, encrypted);
    }

    // Result holder class
    public static class BitFlipResult {
        public final byte[] modifiedIV;
        public final byte[] modifiedEncrypted;

        public BitFlipResult(byte[] iv, byte[] encrypted) {
            this.modifiedIV = iv;
            this.modifiedEncrypted = encrypted;
        }
    }

    // Utility methods
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