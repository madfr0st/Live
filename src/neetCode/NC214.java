package neetCode;

import javax.imageio.IIOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;

public class NC214 {
    static BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
    public static void main(String[] args) throws IIOException {
        String input1 = "aacecaaa";
        System.out.println(shortestPalindrome(input1)); // Output: "aaacecaaa"
    }

    public static String shortestPalindrome(String s) {
        if (s == null || s.length() == 0) return s;

        String rev = new StringBuilder(s).reverse().toString();
        String combined = s + "#" + rev;
        System.out.println(combined);

        // Build the KMP table (lps array)
        int[] lps = buildKMPTable(combined);
        System.out.println(Arrays.toString(lps));

        // The value at the end of lps gives us the longest prefix of s which is also suffix in rev
        int longestPrefix = lps[combined.length() - 1];

        String addOn = rev.substring(0, rev.length() - longestPrefix);
        return addOn + s;
    }

    private static int[] buildKMPTable(String str) {
        int[] lps = new int[str.length()];
        int len = 0;

        for (int i = 1; i < str.length(); ) {
            if (str.charAt(i) == str.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0) {
                    len = lps[len - 1]; // backtrack
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }
        return lps;
    }
}
