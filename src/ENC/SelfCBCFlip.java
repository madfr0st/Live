package ENC;

import java.util.*;

public class SelfCBCFlip {
    // hex string to byte[]
    private static byte[] hexToBytes(String hex) {
        int len = hex.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i+1), 16));
        }
        return data;
    }

    // byte[] to hex string
    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static void main(String[] args){
        String ori = "puts 'FLAG'";
        String mod = "puts  FLAG "; // spaces instead of quotes

        System.out.println("Ori length -> " + ori.length());
        System.out.println("Mod length -> " + mod.length());

        // Original full ciphertext (IV + ciphertext)
        String fullEnc = "255ccfb80cb8a16034e78e4a2facee5a14f639862ab65c9fb7df0f7da94406ac";
        System.out.println("Ciphertext bytes: " + fullEnc.length()/2);

        // split into IV + ciphertext
        String ivHex  = fullEnc.substring(0, 32);   // 16 bytes hex
        String encHex = fullEnc.substring(32);

        byte[] iv  = hexToBytes(ivHex);
        byte[] enc = hexToBytes(encHex);

        // We want to flip positions of the IV so decrypted block changes
        // ori[i] ^ iv[i] ^ mod[i] => enc[i] works
        for (int i = 0; i < ori.length(); i++) {
            if (ori.charAt(i) != mod.charAt(i)) {
                byte origChar = (byte) ori.charAt(i);
                byte modChar  = (byte) mod.charAt(i);
                iv[i] = (byte)(iv[i] ^ (origChar ^ modChar));
            }
        }

        // print the modified ciphertext (IV + enc)
        String newCipherHex = bytesToHex(iv) + bytesToHex(enc);
        System.out.println("Modified ciphertext: " + newCipherHex);
    }
}