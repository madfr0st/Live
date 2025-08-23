package ENC;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AdvanceCBCAttack {
    private static final String HOST = "host5.metaproblems.com";
    private static final int PORT = 7040;
    private static final int BLOCK = 16; // AES block size

    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;

    public AdvanceCBCAttack() throws IOException {
        this.socket = new Socket();
        this.socket.setSoTimeout(12000); // 12 second timeout
        this.socket.connect(new InetSocketAddress(HOST, PORT), 12000);
        this.inputStream = socket.getInputStream();
        this.outputStream = socket.getOutputStream();
    }

    // ---------------- helpers ----------------

    /**
     * Receive until marker appears anywhere in the buffer.
     */
    private byte[] recvUntil(byte[] marker) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] temp = new byte[1024];

        while (true) {
            int bytesRead = inputStream.read(temp);
            if (bytesRead == -1) break;

            buffer.write(temp, 0, bytesRead);
            byte[] data = buffer.toByteArray();

            // Check if marker is present
            if (contains(data, marker)) {
                break;
            }
        }

        return buffer.toByteArray();
    }

    /**
     * Receive exactly one newline-terminated line.
     */
    private byte[] recvLine() throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int b;

        while ((b = inputStream.read()) != -1) {
            buffer.write(b);
            if (b == '\n') {
                break;
            }
        }

        return buffer.toByteArray();
    }

    /**
     * Check if haystack contains needle.
     */
    private boolean contains(byte[] haystack, byte[] needle) {
        if (needle.length > haystack.length) return false;

        outer:
        for (int i = 0; i <= haystack.length - needle.length; i++) {
            for (int j = 0; j < needle.length; j++) {
                if (haystack[i + j] != needle[j]) {
                    continue outer;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Dump blocks in hex format for debugging.
     */
    private void dumpBlocks(byte[] buffer, String title) {
        System.out.println("[*] " + title + " length: " + buffer.length);
        for (int i = 0; i < buffer.length; i += BLOCK) {
            int end = Math.min(i + BLOCK, buffer.length);
            byte[] block = new byte[end - i];
            System.arraycopy(buffer, i, block, 0, end - i);

            StringBuilder hex = new StringBuilder();
            StringBuilder ascii = new StringBuilder();

            for (byte b : block) {
                hex.append(String.format("%02x", b & 0xFF));
                char c = (char) (b & 0xFF);
                ascii.append((c >= 32 && c < 127) ? c : '.');
            }

            System.out.printf("  Block %02d: %-32s %s%n",
                    i / BLOCK, hex.toString(), ascii.toString());
        }
    }

    /**
     * Convert byte array to hex string.
     */
    private String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b & 0xFF));
        }
        return result.toString();
    }

    /**
     * Convert hex string to byte array.
     */
    private byte[] hexToBytes(String hex) {
        int len = hex.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }
        return data;
    }

    // ---------------- oracle wrappers ----------------

    /**
     * Request encryption for a message.
     */
    public byte[] requestEncrypt(String message) throws IOException {
        System.out.println("[*] Requesting encryption for: " + message);

        recvUntil("> ".getBytes());
        outputStream.write("1\n".getBytes());
        outputStream.flush();

        recvUntil("Enter a message: ".getBytes());
        outputStream.write((message + "\n").getBytes());
        outputStream.flush();

        // Read exactly the ciphertext line
        byte[] lineBytes = recvLine();
        String line = new String(lineBytes, StandardCharsets.UTF_8).trim();
        System.out.println("[DEBUG] Ciphertext line: " + line);

        if (!line.startsWith("Encrypted message:")) {
            throw new RuntimeException("Unexpected response line: " + line);
        }

        String hexCt = line.split("Encrypted message:")[1].trim();
        byte[] ct = hexToBytes(hexCt);
        dumpBlocks(ct, "Ciphertext (IV||C1||C2...)");
        return ct;
    }

    /**
     * Execute a command with given ciphertext.
     */
    public String executeCommand(byte[] ctBytes, boolean needPrompt) throws IOException {
        System.out.println("[*] Executing ciphertext, length: " + ctBytes.length);
        dumpBlocks(ctBytes, "Ciphertext to Execute");

        // Only wait for a menu prompt the FIRST time
        if (needPrompt) {
            recvUntil("> ".getBytes());
        }

        outputStream.write("2\n".getBytes());
        outputStream.flush();

        byte[] prompt = recvUntil("hex):".getBytes());
        String promptStr = new String(prompt, StandardCharsets.UTF_8);
        String promptTail = promptStr.length() > 60 ?
                promptStr.substring(promptStr.length() - 60) : promptStr;
        System.out.println("[DEBUG] Exec prompt (tail): " + promptTail);

        String hexCt = bytesToHex(ctBytes);
        String hexCtPreview = hexCt.length() > 64 ? hexCt.substring(0, 64) : hexCt;
        System.out.println("[*] Sending hex (first 64 chars): " + hexCtPreview);

        outputStream.write((hexCt + "\n").getBytes());
        outputStream.flush();

        // Read until the next menu prompt
        byte[] resp = recvUntil("> ".getBytes());
        String respStr = new String(resp, StandardCharsets.UTF_8);
        String respTail = respStr.length() > 160 ?
                respStr.substring(respStr.length() - 160) : respStr;
        System.out.println("[DEBUG] Exec raw response (tail): " + respTail);

        System.out.println("[+] Server replied:\n" + respStr);
        return respStr;
    }

    // ---------------- Enhanced Bit Flipping Logic ----------------

    /**
     * Interactive bit flipping menu
     */
    private void interactiveBitFlip(byte[] originalCt, String originalMessage) {
        Scanner scanner = new Scanner(System.in);
        byte[] workingCt = originalCt.clone();

        System.out.println("\n=== Interactive Bit Flipping Menu ===");
        System.out.println("Original message format: puts '" + originalMessage + "'");
        System.out.println("Message starts at offset 6 (after 'puts \"')");

        while (true) {
            System.out.println("\nChoose attack type:");
            System.out.println("1. Precise string replacement");
            System.out.println("2. Single byte flip");
            System.out.println("3. Command injection attack");
            System.out.println("4. Multiple targeted flips");
            System.out.println("5. Execute current ciphertext");
            System.out.println("6. Reset to original");
            System.out.println("7. Exit");
            System.out.print("Choice: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());

                switch (choice) {
                    case 1:
                        workingCt = preciseStringReplace(workingCt, scanner);
                        break;
                    case 2:
                        workingCt = singleByteFlip(workingCt, scanner);
                        break;
                    case 3:
                        workingCt = commandInjectionAttack(workingCt, scanner);
                        break;
                    case 4:
                        workingCt = multipleTargetedFlips(workingCt, scanner);
                        break;
                    case 5:
                        executeCommand(workingCt, true);
                        break;
                    case 6:
                        workingCt = originalCt.clone();
                        System.out.println("[*] Reset to original ciphertext");
                        break;
                    case 7:
                        return;
                    default:
                        System.out.println("[!] Invalid choice");
                }
            } catch (Exception e) {
                System.out.println("[!] Error: " + e.getMessage());
            }
        }
    }

    /**
     * Precise string replacement attack
     */
    private byte[] preciseStringReplace(byte[] ct, Scanner scanner) {
        System.out.print("Enter original string to replace: ");
        String originalStr = scanner.nextLine();
        System.out.print("Enter new string (same length): ");
        String newStr = scanner.nextLine();

        if (originalStr.length() != newStr.length()) {
            System.out.println("[!] Strings must be the same length!");
            return ct;
        }

        System.out.print("Enter plaintext offset (6 for start of message): ");
        int offset = Integer.parseInt(scanner.nextLine());

        byte[] originalBytes = originalStr.getBytes(StandardCharsets.US_ASCII);
        byte[] newBytes = newStr.getBytes(StandardCharsets.US_ASCII);

        return applyPlaintextPatch(ct, offset, originalBytes, newBytes);
    }

    /**
     * Single byte flip attack
     */
    private byte[] singleByteFlip(byte[] ct, Scanner scanner) {
        System.out.print("Enter block index (0=IV, 1=C1, etc.): ");
        int blockIndex = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter byte offset within block (0-15): ");
        int byteOffset = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter new byte value (hex, e.g., ff): ");
        String hexValue = scanner.nextLine();

        byte newValue = (byte) Integer.parseInt(hexValue, 16);

        byte[] modifiedCt = ct.clone();
        int absolutePos = (blockIndex * BLOCK) + byteOffset;

        if (absolutePos >= modifiedCt.length) {
            System.out.println("[!] Position exceeds ciphertext length");
            return ct;
        }

        byte oldValue = modifiedCt[absolutePos];
        modifiedCt[absolutePos] = newValue;

        System.out.printf("[*] Flipped byte at block %d, offset %d: 0x%02x -> 0x%02x\n",
                blockIndex, byteOffset, oldValue & 0xFF, newValue & 0xFF);

        dumpBlocks(modifiedCt, "Modified Ciphertext");
        return modifiedCt;
    }

    /**
     * Command injection attack simulation
     */
    private byte[] commandInjectionAttack(byte[] ct, Scanner scanner) {
        System.out.println("[*] Command Injection Attack");
        System.out.println("This will attempt to change the command structure");

        System.out.print("Enter target command (e.g., 'system', 'eval', 'exec'): ");
        String targetCmd = scanner.nextLine();

        // Common attack: change 'puts' to target command
        String originalCmd = "puts";

        if (targetCmd.length() != originalCmd.length()) {
            System.out.println("[!] Target command must be same length as 'puts' (4 chars)");
            return ct;
        }

        // Command starts at offset 0
        byte[] originalBytes = originalCmd.getBytes(StandardCharsets.US_ASCII);
        byte[] newBytes = targetCmd.getBytes(StandardCharsets.US_ASCII);

        System.out.println("[*] Attempting to change 'puts' to '" + targetCmd + "'");
        return applyPlaintextPatch(ct, 0, originalBytes, newBytes);
    }

    /**
     * Multiple targeted flips
     */
    private byte[] multipleTargetedFlips(byte[] ct, Scanner scanner) {
        byte[] workingCt = ct.clone();

        System.out.print("Enter number of flips: ");
        int numFlips = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < numFlips; i++) {
            System.out.printf("\n--- Flip %d of %d ---\n", i + 1, numFlips);
            System.out.print("Block index: ");
            int blockIndex = Integer.parseInt(scanner.nextLine());
            System.out.print("Byte offset: ");
            int byteOffset = Integer.parseInt(scanner.nextLine());
            System.out.print("XOR value (hex): ");
            String xorHex = scanner.nextLine();

            byte xorValue = (byte) Integer.parseInt(xorHex, 16);
            int absolutePos = (blockIndex * BLOCK) + byteOffset;

            if (absolutePos >= workingCt.length) {
                System.out.println("[!] Position exceeds ciphertext length, skipping");
                continue;
            }

            byte oldValue = workingCt[absolutePos];
            workingCt[absolutePos] ^= xorValue;

            System.out.printf("[*] XOR'd byte at block %d, offset %d: 0x%02x ^ 0x%02x = 0x%02x\n",
                    blockIndex, byteOffset, oldValue & 0xFF, xorValue & 0xFF,
                    workingCt[absolutePos] & 0xFF);
        }

        dumpBlocks(workingCt, "Multi-Flip Modified Ciphertext");
        return workingCt;
    }

    /**
     * Advanced attack patterns
     */
    private void demonstrateAttackPatterns(byte[] originalCt, String originalMessage) {
        System.out.println("\n=== Advanced Attack Pattern Demonstrations ===");

        // Pattern 1: Quote escape attack
        System.out.println("\n[*] Pattern 1: Quote Escape Attack");
        try {
            // Try to escape the quote and add command injection
            String escapePattern = "'; system('whoami'); puts '";
            if (escapePattern.length() <= originalMessage.length()) {
                byte[] originalBytes = originalMessage.substring(0, escapePattern.length()).getBytes();
                byte[] newBytes = escapePattern.getBytes();

                byte[] escapeCt = applyPlaintextPatch(originalCt, 6, originalBytes, newBytes);
                System.out.println("[*] Executing quote escape attack...");
                executeCommand(escapeCt, true);
            } else {
                System.out.println("[!] Escape pattern too long for message");
            }
        } catch (Exception e) {
            System.out.println("[!] Quote escape attack failed: " + e.getMessage());
        }

        // Pattern 2: Partial corruption for information leakage
        System.out.println("\n[*] Pattern 2: Partial Corruption Analysis");
        for (int i = 0; i < Math.min(3, originalMessage.length()); i++) {
            try {
                byte[] corruptCt = originalCt.clone();
                // Flip a single bit in the IV to corrupt first block
                corruptCt[i] ^= 0x01;

                System.out.printf("[*] Corrupting IV byte %d...\n", i);
                String result = executeCommand(corruptCt, true);

                // Analyze the result for information leakage
                if (result.contains("error") || result.contains("invalid")) {
                    System.out.println("[+] Error response detected - potential info leak");
                }
            } catch (Exception e) {
                System.out.println("[!] Corruption test " + i + " failed: " + e.getMessage());
            }
        }
    }

    /**
     * Flip ciphertext so decrypted plaintext changes from orig to new.
     */
    public byte[] applyPlaintextPatch(byte[] ct, int ptOffset, byte[] orig, byte[] newBytes) {
        if (orig.length != newBytes.length) {
            throw new IllegalArgumentException("orig and new must be same length");
        }

        // Split ciphertext into blocks
        List<byte[]> blocks = new ArrayList<>();
        for (int i = 0; i < ct.length; i += BLOCK) {
            int end = Math.min(i + BLOCK, ct.length);
            byte[] block = new byte[end - i];
            System.arraycopy(ct, i, block, 0, end - i);
            blocks.add(block);
        }

        // Create mutable copies
        List<byte[]> prevBlocks = new ArrayList<>();
        for (byte[] block : blocks) {
            byte[] copy = new byte[block.length];
            System.arraycopy(block, 0, copy, 0, block.length);
            prevBlocks.add(copy);
        }

        System.out.printf("[*] Patching plaintext @offset %d for %d bytes%n",
                ptOffset, orig.length);

        for (int i = 0; i < orig.length; i++) {
            int p = ptOffset + i;
            int pblock = p / BLOCK;        // P1==0
            int within = p % BLOCK;
            int prevIndex = pblock;        // 0=IV, 1=C1, 2=C2, ...

            if (prevIndex >= prevBlocks.size()) {
                throw new IndexOutOfBoundsException("Patch exceeds ciphertext range");
            }

            int delta = (orig[i] & 0xFF) ^ (newBytes[i] & 0xFF);
            int before = prevBlocks.get(prevIndex)[within] & 0xFF;
            prevBlocks.get(prevIndex)[within] ^= delta;
            int after = prevBlocks.get(prevIndex)[within] & 0xFF;

            System.out.printf("    - byte %02d: P#%d off %02d  prevBlk %d  " +
                            "delta=0x%02x  %02x->%02x%n",
                    i, pblock, within, prevIndex, delta, before, after);
        }

        // Reconstruct ciphertext
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        for (byte[] block : prevBlocks) {
            result.write(block, 0, block.length);
        }

        byte[] newCt = result.toByteArray();
        dumpBlocks(newCt, "Tampered Ciphertext");
        return newCt;
    }

    // ---------------- main ----------------

    public void run() throws IOException {
        try {
            // Get original message
            String msg = "A".repeat(32);
            System.out.println("[*] Using message: " + msg);

            // Get encrypted message
            byte[] ct = requestEncrypt(msg);

            // Message analysis
            int msgStart = "puts '".length(); // 6
            System.out.println("[*] Message starts at plaintext offset " + msgStart);
            System.out.println("[*] Full plaintext structure: puts '" + msg + "'");

            // Original basic attack (keep for compatibility)
            System.out.println("\n=== Basic Attack (Original Logic) ===");
            int maxSafe = 16 - msgStart; // 10
            System.out.println("[*] Capping patch to first-block message bytes: " + maxSafe + " bytes");

            byte[] orig = ("A".repeat(maxSafe) + "A".repeat(16)).getBytes(StandardCharsets.US_ASCII);
            byte[] newBytes = ("B".repeat(maxSafe) + "B".repeat(16)).getBytes(StandardCharsets.US_ASCII);

            byte[] ctTampered = applyPlaintextPatch(ct, msgStart, orig, newBytes);
            executeCommand(ctTampered, true);

            // Enhanced interactive attacks
            System.out.println("\n=== Enhanced Attack Options ===");
            Scanner scanner = new Scanner(System.in);
            System.out.print("Continue with advanced attacks? (y/n): ");
            String response = scanner.nextLine().trim().toLowerCase();

            if (response.equals("y") || response.equals("yes")) {
                // Interactive bit flipping
                interactiveBitFlip(ct, msg);

                // Demonstrate advanced patterns
                System.out.print("\nDemonstrate advanced attack patterns? (y/n): ");
                response = scanner.nextLine().trim().toLowerCase();
                if (response.equals("y") || response.equals("yes")) {
                    demonstrateAttackPatterns(ct, msg);
                }
            }

        } finally {
            close();
        }
    }

    public void close() throws IOException {
        if (socket != null && !socket.isClosed()) {
            socket.close();
        }
    }

    public static void main(String[] args) {
        try {
            CBCAttack attack = new CBCAttack();
            attack.run();
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}