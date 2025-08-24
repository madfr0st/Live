package ENC;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class CleanRubyInjection {

    public static void main(String[] args) {
        System.out.println("=== Getting CLEAN 'puts #{FLAG}' Output ===");

        // SOLUTION 1: Use Ruby comment syntax to hide corruption
        demonstrateCommentSolution();

        // SOLUTION 2: Use multiple statements where first gets corrupted
        demonstrateMultiStatementSolution();

        // SOLUTION 3: Use specific corruption that still works
        demonstrateWorkingCorruption();

        // Your actual encrypted data analysis
        String yourHex = "e8ef072bf414a34c106b448cce7587648cf21b9ab95bb1459670651a3ef8c031b762ff546c139a0971102eeee76fc9f9ad4f18173d9a133f6a9c5890408a9808";
        analyzeYourData(yourHex);
    }

    public static void demonstrateCommentSolution() {
        System.out.println("\n=== SOLUTION 1: Ruby Comment Trick ===");

        // Put target in second block, use # comment to hide first block corruption
        String original = "whatever garbage" + "puts.FLAG.hello"; // 32 chars
        String target   = "#corrupted_stuff" + "puts '#{FLAG}'  "; // 32 chars, # makes first block a comment!

        System.out.println("Strategy: Make first block a Ruby comment");
        System.out.println("Original: \"" + original + "\"");
        System.out.println("Target:   \"" + target + "\"");
        System.out.println();
        System.out.println("When decrypted:");
        System.out.println("Block 0: [corrupted garbage] (but starts with # so it's a comment!)");
        System.out.println("Block 1: puts '#{FLAG}'       (clean executable code)");
        System.out.println();
        System.out.println("Ruby executes: # [garbage]\\nputs '#{FLAG}'");
        System.out.println("Result: Only the puts command runs!");

        showBitFlipCalculation(original, target, "comment");
    }

    public static void demonstrateMultiStatementSolution() {
        System.out.println("\n=== SOLUTION 2: Multiple Statements ===");

        // Use semicolon and newline to separate statements
        String original = "x.eq.1.semicolon" + "puts.FLAG.hello"; // 32 chars
        String target   = "x=1;            " + "puts '#{FLAG}'  "; // 32 chars

        System.out.println("Strategy: First statement gets corrupted, second executes cleanly");
        System.out.println("Original: \"" + original + "\"");
        System.out.println("Target:   \"" + target + "\"");
        System.out.println();
        System.out.println("When decrypted:");
        System.out.println("Block 0: [corrupted]; (syntax error but continues)");
        System.out.println("Block 1: puts '#{FLAG}' (executes successfully)");

        showBitFlipCalculation(original, target, "multistatement");
    }

    public static void demonstrateWorkingCorruption() {
        System.out.println("\n=== SOLUTION 3: Controlled Corruption ===");

        // The key insight: some corruption patterns still produce valid Ruby
        String original = "begin.rescue.end" + "puts.FLAG.hello"; // 32 chars
        String target   = "begin;rescue;end" + "puts '#{FLAG}'  "; // 32 chars

        System.out.println("Strategy: Design corruption to still be valid Ruby syntax");
        System.out.println("Original: \"" + original + "\"");
        System.out.println("Target:   \"" + target + "\"");
        System.out.println();
        System.out.println("Even if first block gets corrupted:");
        System.out.println("- begin/rescue/end is valid Ruby exception handling");
        System.out.println("- Second block executes the puts command cleanly");

        showBitFlipCalculation(original, target, "controlled");
    }

    public static void analyzeYourData(String hex) {
        System.out.println("\n=== ANALYSIS OF YOUR DATA ===");

        String ivHex = hex.substring(0, 32);
        String cipherHex = hex.substring(32);
        int cipherBytes = cipherHex.length() / 2;
        int numBlocks = cipherBytes / 16;

        System.out.println("Your encrypted data has " + numBlocks + " ciphertext blocks");
        System.out.println("Total plaintext capacity: " + (numBlocks * 16) + " characters");

        if (numBlocks >= 2) {
            System.out.println("\n✓ Good! You have enough blocks for the comment solution");
            System.out.println("Recommended approach:");
            System.out.println("1. Original should be ~32 chars: 'ValidTextPassesRegex123456Hello.World123'");
            System.out.println("2. Target should be:             '#garbledtexthere puts \\#{FLAG}'");
            System.out.println("3. Apply bit flip to first ciphertext block");
            System.out.println("4. Result: # [corruption]\\nputs '#{FLAG}' ← Only puts runs!");
        } else {
            System.out.println("\n⚠ Warning: Only " + numBlocks + " blocks available");
            System.out.println("You need at least 2 blocks for clean injection");
        }
    }

    public static void showBitFlipCalculation(String original, String target, String method) {
        System.out.println("\nBit flip calculation for " + method + " method:");

        byte[] origBytes = original.getBytes(StandardCharsets.UTF_8);
        byte[] targBytes = target.getBytes(StandardCharsets.UTF_8);

        System.out.println("Modifications needed for second block (chars 16-31):");

        for (int i = 16; i < Math.min(original.length(), target.length()); i++) {
            if (origBytes[i] != targBytes[i]) {
                byte diff = (byte)(origBytes[i] ^ targBytes[i]);
                int cipherPos = i - 16; // Position in first ciphertext block

                System.out.println("  Pos " + i + ": '" + (char)origBytes[i] +
                        "' → '" + (char)targBytes[i] +
                        "' (XOR first_cipher[" + cipherPos + "] with 0x" +
                        String.format("%02X", diff & 0xFF) + ")");
            }
        }
    }

    // Practical implementation for your case
    public static String generateCleanInjection() {
        System.out.println("\n=== PRACTICAL SOLUTION FOR YOUR CASE ===");

        // Design strings that work with your constraints
        String original = "HelloWorld123456" + "TestMessage1234"; // 32 chars, passes regex
        String target   = "#corrupted12345" + "puts '#{FLAG}' "; // 32 chars, # hides corruption

        System.out.println("Original to encrypt: \"" + original + "\"");
        System.out.println("After bit flip:      \"" + target + "\"");
        System.out.println();
        System.out.println("Steps:");
        System.out.println("1. Encrypt: \"" + original + "\" (passes regex validation)");
        System.out.println("2. Apply bit flips to first ciphertext block");
        System.out.println("3. Decrypted result: \"" + target + "\"");
        System.out.println("4. Ruby evaluates: # [corruption]\\nputs '#{FLAG}' ");
        System.out.println("5. Only puts '#{FLAG}' executes → FLAG printed!");

        return generateModifiedHex(original, target);
    }

    public static String generateModifiedHex(String original, String target) {
        // This would calculate the actual hex modifications needed
        System.out.println("\nTo calculate modified hex:");
        System.out.println("1. Take your original ciphertext");
        System.out.println("2. For each different character in positions 16-31:");
        System.out.println("3. XOR the corresponding byte in first ciphertext block");

        byte[] origBytes = original.getBytes(StandardCharsets.UTF_8);
        byte[] targBytes = target.getBytes(StandardCharsets.UTF_8);

        System.out.println("\nXOR operations needed:");
        for (int i = 16; i < 32 && i < Math.min(original.length(), target.length()); i++) {
            if (origBytes[i] != targBytes[i]) {
                byte xor = (byte)(origBytes[i] ^ targBytes[i]);
                System.out.println("Cipher[" + (i-16) + "] ^= 0x" + String.format("%02X", xor & 0xFF));
            }
        }

        return "Apply these XOR operations to your ciphertext";
    }

    // Helper methods
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