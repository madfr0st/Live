package ENC;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CBCAttack {
    private static final String HOST = "host5.metaproblems.com";
    private static final int PORT = 7040;
    private static final int BLOCK = 16; // AES block size

    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;

    public CBCAttack() throws IOException {
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

    // ---------------- precise CBC flip (P1-only safe patch) ----------------

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
            // 32-byte message; the service encrypts: "puts '<msg>'"
            String msg = "A".repeat(32);
            byte[] ct = requestEncrypt(msg);

            // In plaintext, message starts after "puts '" (6 bytes)
            int msgStart = "puts '".length(); // 6
            System.out.println("[*] Message starts at plaintext offset " + msgStart);

            // SAFEST WINDOW: only the first plaintext block (P1) is reliably controllable
            // That means we can change at most 16 - 6 = 10 bytes of the message
            int maxSafe = 16 - msgStart; // 10
            System.out.println("[*] Capping patch to first-block message bytes: " + maxSafe + " bytes");

            // Demonstrate a benign change (A->B) for those bytes
            byte[] orig = ("A".repeat(maxSafe) + "A".repeat(16)).getBytes(StandardCharsets.US_ASCII);
            byte[] newBytes = ("B".repeat(maxSafe) + "B".repeat(16)).getBytes(StandardCharsets.US_ASCII);

            byte[] ctTampered = applyPlaintextPatch(ct, msgStart, orig, newBytes);

            // Execute once
            executeCommand(ctTampered, true);

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