package ENC;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AESCBCBitFlip {
    private static final int BLOCK_SIZE = 16; // AES block size
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";

    private byte[] key;
    private SecureRandom random;

    public AESCBCBitFlip() {
        this.random = new SecureRandom();
        this.key = generateKey(32); // AES-256
    }

    public AESCBCBitFlip(byte[] key) {
        this.random = new SecureRandom();
        this.key = key.clone();
    }

    // ---------------- Utility Methods ----------------

    private byte[] generateKey(int length) {
        byte[] key = new byte[length];
        random.nextBytes(key);
        return key;
    }

    private byte[] generateIV() {
        byte[] iv = new byte[BLOCK_SIZE];
        random.nextBytes(iv);
        return iv;
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b & 0xFF));
        }
        return result.toString();
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

    private void dumpBlocks(byte[] data, String title) {
        System.out.println("\n[*] " + title + " (length: " + data.length + " bytes)");
        for (int i = 0; i < data.length; i += BLOCK_SIZE) {
            int end = Math.min(i + BLOCK_SIZE, data.length);
            byte[] block = Arrays.copyOfRange(data, i, end);

            StringBuilder hex = new StringBuilder();
            StringBuilder ascii = new StringBuilder();

            for (byte b : block) {
                hex.append(String.format("%02x ", b & 0xFF));
                char c = (char) (b & 0xFF);
                ascii.append((c >= 32 && c < 127) ? c : '.');
            }

            System.out.printf("  Block %02d: %-48s [%s]\n",
                    i / BLOCK_SIZE, hex.toString().trim(), ascii.toString());
        }
    }

    // ---------------- AES-CBC Implementation ----------------

    public byte[] aesCbcEncrypt(byte[] plaintext, byte[] iv) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(key, ALGORITHM);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);

        return cipher.doFinal(plaintext);
    }

    public byte[] aesCbcDecrypt(byte[] ciphertext, byte[] iv) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(key, ALGORITHM);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

        return cipher.doFinal(ciphertext);
    }

    // Safe decrypt that handles padding errors
    public byte[] safeDecrypt(byte[] ciphertext, byte[] iv) {
        try {
            return aesCbcDecrypt(ciphertext, iv);
        } catch (BadPaddingException | IllegalBlockSizeException e) {
            System.out.println("[!] Decryption failed (padding error): " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.out.println("[!] Decryption failed: " + e.getMessage());
            return null;
        }
    }

    // ---------------- Message Processing (Original Logic) ----------------

    public Map<String, String> processMessage(byte[] iv, byte[] data, boolean isCiphertext) throws Exception {
        Map<String, String> result = new HashMap<>();

        if (!isCiphertext) {
            // Encrypt normally
            byte[] ciphertext = aesCbcEncrypt(data, iv);
            result.put("ciphertext", bytesToHex(ciphertext));
            result.put("desc", "normal encryption");
        } else {
            // Already ciphertext → create a NEW encryption
            byte[] newPlaintext = "sum ['test']".getBytes();
            byte[] newCiphertext = aesCbcEncrypt(newPlaintext, iv);
            result.put("ciphertext", bytesToHex(newCiphertext));
            result.put("desc", "sum ['test']");
        }

        return result;
    }

    // ---------------- Bit Flipping Attack Logic ----------------

    /**
     * Basic bit flip attack - modify specific bytes in ciphertext
     */
    public byte[] basicBitFlip(byte[] originalCiphertext, byte[] iv, int blockIndex, int byteIndex, byte newValue) {
        // Combine IV and ciphertext for easier manipulation
        byte[] fullCiphertext = new byte[iv.length + originalCiphertext.length];
        System.arraycopy(iv, 0, fullCiphertext, 0, iv.length);
        System.arraycopy(originalCiphertext, 0, fullCiphertext, iv.length, originalCiphertext.length);

        // Calculate absolute position
        int position = (blockIndex * BLOCK_SIZE) + byteIndex;

        if (position >= fullCiphertext.length) {
            throw new IndexOutOfBoundsException("Position exceeds ciphertext length");
        }

        System.out.printf("[*] Flipping block %d, byte %d (absolute pos %d): 0x%02x -> 0x%02x\n",
                blockIndex, byteIndex, position, fullCiphertext[position] & 0xFF, newValue & 0xFF);

        fullCiphertext[position] = newValue;

        // Return new IV and ciphertext
        byte[] newIv = Arrays.copyOfRange(fullCiphertext, 0, BLOCK_SIZE);
        byte[] newCiphertext = Arrays.copyOfRange(fullCiphertext, BLOCK_SIZE, fullCiphertext.length);

        // For simplicity, return the full modified data (IV + ciphertext)
        return fullCiphertext;
    }

    /**
     * Precise plaintext patch - change specific plaintext bytes
     */
    public byte[] precisionBitFlip(byte[] originalCiphertext, byte[] iv, int plaintextOffset,
                                   byte[] originalBytes, byte[] newBytes) {
        if (originalBytes.length != newBytes.length) {
            throw new IllegalArgumentException("Original and new bytes must have same length");
        }

        // Combine IV and ciphertext
        byte[] fullCiphertext = new byte[iv.length + originalCiphertext.length];
        System.arraycopy(iv, 0, fullCiphertext, 0, iv.length);
        System.arraycopy(originalCiphertext, 0, fullCiphertext, iv.length, originalCiphertext.length);

        byte[] modifiedCiphertext = fullCiphertext.clone();

        System.out.printf("[*] Precision bit flip at plaintext offset %d for %d bytes\n",
                plaintextOffset, originalBytes.length);

        for (int i = 0; i < originalBytes.length; i++) {
            int ptPos = plaintextOffset + i;
            int blockNum = ptPos / BLOCK_SIZE;
            int byteInBlock = ptPos % BLOCK_SIZE;

            // To affect plaintext block N, we modify ciphertext block N-1 (or IV for block 0)
            int cipherBlockToModify = blockNum; // 0 = IV, 1 = first cipher block, etc.
            int cipherPos = (cipherBlockToModify * BLOCK_SIZE) + byteInBlock;

            if (cipherPos >= modifiedCiphertext.length) {
                throw new IndexOutOfBoundsException("Cipher position exceeds available data");
            }

            byte delta = (byte) (originalBytes[i] ^ newBytes[i]);
            byte oldValue = modifiedCiphertext[cipherPos];
            modifiedCiphertext[cipherPos] ^= delta;

            System.out.printf("  Byte %d: PT block %d offset %d -> cipher pos %d, " +
                            "delta=0x%02x, 0x%02x -> 0x%02x\n",
                    i, blockNum, byteInBlock, cipherPos, delta & 0xFF,
                    oldValue & 0xFF, modifiedCiphertext[cipherPos] & 0xFF);
        }

        return modifiedCiphertext;
    }

    /**
     * Demonstrate various bit flipping attacks
     */
    public void demonstrateBitFlipping() {
        System.out.println("=== AES-CBC Bit Flipping Attack Demonstration ===\n");

        try {
            // Original message
            String originalMessage = "puts 'Hello, World! This is a test message for CBC bit flipping.'";
            byte[] plaintext = originalMessage.getBytes();
            byte[] iv = generateIV();

            System.out.println("Original message: " + originalMessage);
            System.out.println("Key: " + bytesToHex(key));
            System.out.println("IV:  " + bytesToHex(iv));

            // Encrypt
            byte[] ciphertext = aesCbcEncrypt(plaintext, iv);
            dumpBlocks(plaintext, "Original Plaintext");
            dumpBlocks(ciphertext, "Original Ciphertext");

            // Test 1: Basic bit flip
            System.out.println("\n=== Test 1: Basic Bit Flip Attack ===");
            byte[] flippedData1 = basicBitFlip(ciphertext, iv, 0, 6, (byte) 'X'); // Change 6th byte in IV
            byte[] newIv1 = Arrays.copyOfRange(flippedData1, 0, BLOCK_SIZE);
            byte[] newCiphertext1 = Arrays.copyOfRange(flippedData1, BLOCK_SIZE, flippedData1.length);

            byte[] decrypted1 = safeDecrypt(newCiphertext1, newIv1);
            if (decrypted1 != null) {
                System.out.println("Decrypted: " + new String(decrypted1));
                dumpBlocks(decrypted1, "Decrypted After Basic Flip");
            }

            // Test 2: Precision attack - change "Hello" to "XXXXX"
            System.out.println("\n=== Test 2: Precision Bit Flip Attack ===");
            int helloOffset = originalMessage.indexOf("Hello");
            if (helloOffset != -1) {
                byte[] originalBytes = "Hello".getBytes();
                byte[] newBytes = "XXXXX".getBytes();

                byte[] flippedData2 = precisionBitFlip(ciphertext, iv, helloOffset, originalBytes, newBytes);
                byte[] newIv2 = Arrays.copyOfRange(flippedData2, 0, BLOCK_SIZE);
                byte[] newCiphertext2 = Arrays.copyOfRange(flippedData2, BLOCK_SIZE, flippedData2.length);

                byte[] decrypted2 = safeDecrypt(newCiphertext2, newIv2);
                if (decrypted2 != null) {
                    System.out.println("Decrypted: " + new String(decrypted2));
                    dumpBlocks(decrypted2, "Decrypted After Precision Flip");
                }
            }

            // Test 3: Command injection simulation
            System.out.println("\n=== Test 3: Command Injection Simulation ===");
            String cmdMessage = "puts 'safe_command'";
            byte[] cmdPlaintext = cmdMessage.getBytes();
            byte[] cmdCiphertext = aesCbcEncrypt(cmdPlaintext, iv);

            System.out.println("Original command: " + cmdMessage);

            // Try to change 'safe_command' to 'evil_command'
            int cmdOffset = cmdMessage.indexOf("safe_command");
            if (cmdOffset != -1) {
                byte[] safeBytes = "safe_command".getBytes();
                byte[] evilBytes = "evil_command".getBytes();

                byte[] flippedData3 = precisionBitFlip(cmdCiphertext, iv, cmdOffset, safeBytes, evilBytes);
                byte[] newIv3 = Arrays.copyOfRange(flippedData3, 0, BLOCK_SIZE);
                byte[] newCiphertext3 = Arrays.copyOfRange(flippedData3, BLOCK_SIZE, flippedData3.length);

                byte[] decrypted3 = safeDecrypt(newCiphertext3, newIv3);
                if (decrypted3 != null) {
                    System.out.println("Modified command: " + new String(decrypted3));
                    dumpBlocks(decrypted3, "Modified Command Plaintext");
                }
            }

        } catch (Exception e) {
            System.err.println("Error in demonstration: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ---------------- Main Method ----------------

    public static void main(String[] args) {
        try {
            AESCBCBitFlip demo = new AESCBCBitFlip();

//            // Original functionality test
//            System.out.println("=== Original Functionality Test ===");
//            byte[] iv = demo.generateIV();
//
//            // Example 1: Encrypt fresh message
//            byte[] message = "aaaaaaaaaaaaaaaa".getBytes();
//            Map<String, String> result1 = demo.processMessage(iv, message, false);
//            System.out.println("Fresh Encryption: " + result1);
//
//            // Example 2: If input is ciphertext, create a new encryption
//            String fakeCiphertext = result1.get("ciphertext");
//            Map<String, String> result2 = demo.processMessage(iv, demo.hexToBytes(fakeCiphertext), true);
//            System.out.println("Re-Encryption: " + result2);
//
//            System.out.println("\n" + "=".repeat(60) + "\n");

            // Bit flipping demonstration
            demo.demonstrateBitFlipping();

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}