package ENC;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class FlagCBCAttack {
    private static final String HOST = "host5.metaproblems.com";
    private static final int PORT = 7040;
    private static final int BLOCK = 16; // AES block size

    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;

    public FlagCBCAttack() throws IOException {
        this.socket = new Socket();
        this.socket.setSoTimeout(15000);
        this.socket.connect(new InetSocketAddress(HOST, PORT), 15000);
        this.inputStream = socket.getInputStream();
        this.outputStream = socket.getOutputStream();
    }

    private byte[] recvUntil(byte[] marker) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] temp = new byte[1024];

        while (true) {
            int bytesRead = inputStream.read(temp);
            if (bytesRead == -1) break;

            buffer.write(temp, 0, bytesRead);
            byte[] data = buffer.toByteArray();

            if (contains(data, marker)) {
                break;
            }
        }
        return buffer.toByteArray();
    }

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

    private String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b & 0xFF));
        }
        return result.toString();
    }

    private void dumpBlocks(byte[] buffer, String title) {
        System.out.println("[DEBUG] " + title + " (length: " + buffer.length + ")");
        for (int i = 0; i < buffer.length; i += BLOCK) {
            int end = Math.min(i + BLOCK, buffer.length);
            byte[] block = new byte[end - i];
            System.arraycopy(buffer, i, block, 0, end - i);

            StringBuilder hex = new StringBuilder();
            StringBuilder ascii = new StringBuilder();

            for (byte b : block) {
                hex.append(String.format("%02x ", b & 0xFF));
                char c = (char) (b & 0xFF);
                ascii.append((c >= 32 && c < 127) ? c : '.');
            }

            System.out.printf("[DEBUG]   Block %02d: %-48s [%s]%n",
                    i / BLOCK, hex.toString().trim(), ascii.toString());
        }
    }

    private byte[] hexToBytes(String hex) {
        int len = hex.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }
        return data;
    }

    public byte[] requestEncrypt(String message) throws IOException {
        System.out.println("[*] Requesting encryption for: " + message);

        recvUntil("> ".getBytes());
        outputStream.write("1\n".getBytes());
        outputStream.flush();

        recvUntil("Enter a message: ".getBytes());
        outputStream.write((message + "\n").getBytes());
        outputStream.flush();

        byte[] lineBytes = recvLine();
        String line = new String(lineBytes, StandardCharsets.UTF_8).trim();
        System.out.println("[DEBUG] Full server response: " + line);

        if (!line.startsWith("Encrypted message:")) {
            throw new RuntimeException("Unexpected response: " + line);
        }

        String hexCt = line.split("Encrypted message:")[1].trim();
        byte[] ct = hexToBytes(hexCt);
        System.out.println("[DEBUG] Hex ciphertext: " + hexCt);
        dumpBlocks(ct, "Original Ciphertext");

        // Show what the plaintext should look like
        String expectedPlaintext = "puts '" + message + "'";
        System.out.println("[DEBUG] Expected plaintext: " + expectedPlaintext);
        System.out.println("[DEBUG] Expected plaintext length: " + expectedPlaintext.length());

        return ct;
    }

    public String executeCommand(byte[] ctBytes) throws IOException {
        System.out.println("[*] Executing modified ciphertext...");

        recvUntil("> ".getBytes());
        outputStream.write("2\n".getBytes());
        outputStream.flush();

        recvUntil("hex): ".getBytes());
        outputStream.write((bytesToHex(ctBytes) + "\n").getBytes());
        outputStream.flush();

        byte[] resp = recvUntil("> ".getBytes());
        String respStr = new String(resp, StandardCharsets.UTF_8);
        System.out.println("[+] Server response:\n" + respStr);
        return respStr;
    }

    public byte[] flipBits(byte[] ct, int ptOffset, byte[] original, byte[] target) {
        System.out.println("[DEBUG] Bit flipping details:");
        System.out.println("[DEBUG]   Plaintext offset: " + ptOffset);
        System.out.println("[DEBUG]   Original bytes: " + new String(original));
        System.out.println("[DEBUG]   Target bytes: " + new String(target));
        System.out.println("[DEBUG]   Length: " + original.length);

        byte[] modifiedCt = ct.clone();

        for (int i = 0; i < original.length; i++) {
            int plaintextPos = ptOffset + i;
            int blockNum = plaintextPos / BLOCK;
            int byteInBlock = plaintextPos % BLOCK;

            // Flip the corresponding ciphertext byte (IV for block 0, C1 for block 1, etc.)
            int cipherPos = (blockNum * BLOCK) + byteInBlock;

            byte delta = (byte) (original[i] ^ target[i]);
            byte oldCipherByte = modifiedCt[cipherPos];
            modifiedCt[cipherPos] ^= delta;
            byte newCipherByte = modifiedCt[cipherPos];

            System.out.printf("[DEBUG]   Byte %2d: PT pos=%2d (block %d, offset %2d) -> cipher pos=%2d, " +
                            "'%c'->'%c', delta=0x%02x, cipher: 0x%02x->0x%02x%n",
                    i, plaintextPos, blockNum, byteInBlock, cipherPos,
                    (char)original[i], (char)target[i], delta & 0xFF,
                    oldCipherByte & 0xFF, newCipherByte & 0xFF);
        }

        dumpBlocks(modifiedCt, "Modified Ciphertext");
        return modifiedCt;
    }

    public void extractFlag() throws IOException {
        System.out.println("=== Simple FLAG Extraction ===\n");

        // Send 32 bytes to get encryption
        String message = "A".repeat(32);
        System.out.println("[*] Sending 32-byte message: " + message);

        byte[] ct = requestEncrypt(message);
        System.out.println("[*] Got ciphertext, length: " + ct.length);

        // Current plaintext: puts 'AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'
        // Target plaintext:  puts 'ENV['FLAG']AAAAAAAAAAAAAAAAAA'
        // But we need to be more careful about block boundaries

        System.out.println("[DEBUG] Analyzing block structure:");
        String fullPlaintext = "puts '" + message + "'";
        System.out.println("[DEBUG] Full plaintext: " + fullPlaintext);
        System.out.println("[DEBUG] Length: " + fullPlaintext.length());

        // Show block breakdown
        for (int i = 0; i < fullPlaintext.length(); i += 16) {
            String block = fullPlaintext.substring(i, Math.min(i + 16, fullPlaintext.length()));
            System.out.printf("[DEBUG] Block %d: '%s' (length: %d)%n", i/16, block, block.length());
        }

        // Strategy 1: Try to change first few A's to ENV['FLAG'] - but this crosses blocks!
        // Let's try a simpler approach: change some A's to something that won't cause syntax errors

        System.out.println("\n[*] Strategy 1: Simple test replacement");
        String originalPart = "AAAAAAAAAAA";  // 4 A's
        String targetPart = "ENV['FLAG']";    // 4 characters, no special chars

        byte[] originalBytes = originalPart.getBytes(StandardCharsets.US_ASCII);
        byte[] targetBytes = targetPart.getBytes(StandardCharsets.US_ASCII);

        // The message starts at offset 6 (after "puts '")
        int messageOffset = 6;

        System.out.println("[*] Test flip: " + originalPart + " -> " + targetPart);
        byte[] modifiedCt1 = flipBits(ct, messageOffset, originalBytes, targetBytes);

        System.out.println("[*] Sending test modified ciphertext...");
        String result1 = executeCommand(modifiedCt1);

        // Strategy 2: If test works, try the actual flag extraction
        if (!result1.contains("Invalid char")) {
            System.out.println("\n[*] Strategy 2: FLAG extraction attempt");

            // Use a message that can accommodate ENV['FLAG'] cleanly
            String flagMessage = "BBBBBBBBBBB"; // 11 B's to replace with ENV['FLAG']
            byte[] ct2 = requestEncrypt(flagMessage);

            String originalFlag = "BBBBBBBBBBB";
            String targetFlag = "ENV['FLAG']";

            byte[] origFlagBytes = originalFlag.getBytes(StandardCharsets.US_ASCII);
            byte[] targFlagBytes = targetFlag.getBytes(StandardCharsets.US_ASCII);

            System.out.println("[*] Flag flip: " + originalFlag + " -> " + targetFlag);
            byte[] modifiedCt2 = flipBits(ct2, messageOffset, origFlagBytes, targFlagBytes);

            System.out.println("[*] Sending flag extraction ciphertext...");
            String result2 = executeCommand(modifiedCt2);

            if (result2.contains("MetaCTF") || result2.contains("flag")) {
                System.out.println("\n[SUCCESS] FLAG FOUND!");
            }
        }
    }

    public void close() throws IOException {
        if (socket != null && !socket.isClosed()) {
            socket.close();
        }
    }

    public static void main(String[] args) {
        try {
            FlagCBCAttack attack = new FlagCBCAttack();
            attack.extractFlag();
            attack.close();
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}