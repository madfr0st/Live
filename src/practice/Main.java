package practice;

import java.util.*;

public class Main {
    public static void func(String inputStr) {
        int n = inputStr.length();
        if (n < 2) {
            System.out.println("None");
            return;
        }

        int maxLen = 1;
        // Use a TreeSet to collect palindromes of max length (to get lexicographically smallest easily)
        TreeSet<String> candidates = new TreeSet<>();

        for (int center = 0; center < n; center++) {
            // Odd-length palindromes: expand around (center, center)
            expandAndCollect(inputStr, center, center, maxLen, candidates);

            // Even-length palindromes: expand around (center, center+1)
            expandAndCollect(inputStr, center, center + 1, maxLen, candidates);
        }

        if (candidates.isEmpty()) {
            System.out.println("None");
        } else {
            // The TreeSet is sorted lexicographically;
            // its first element is the lexicographically smallest among those with max length
            System.out.println(candidates.first());
        }
    }

    private static int globalMaxLen = 1;

    private static void expandAndCollect(String s, int left, int right, int currentMaxLen, TreeSet<String> candidates) {
        int n = s.length();
        while (left >= 0 && right < n && s.charAt(left) == s.charAt(right)) {
            int palLen = right - left + 1;
            if (palLen > globalMaxLen) {
                // Found strictly longer palindrome: clear previous candidates
                globalMaxLen = palLen;
                candidates.clear();
                candidates.add(s.substring(left, right + 1));
            } else if (palLen == globalMaxLen && palLen > 1) {
                // Same maximum length: add to candidates
                candidates.add(s.substring(left, right + 1));
            }
            left--;
            right++;
        }
    }

    public static void main(String[] args) {
        func("YABCCBAZ"); // prints ABCCBA
        func("ABC");      // prints None
    }
}