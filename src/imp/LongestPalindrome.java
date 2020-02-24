package imp;

import java.util.HashMap;
import java.util.Map;

class LongestPalindrome
{
    // Function to find the length of Longest Palindromic Subsequence
    // of subString X[i..j]
    public static int longestPalindrome(String X, int i, int j,
                                        Map<String, Integer> lookup)
    {
        // base condition
        if (i > j) {
            return 0;
        }

        // if String X has only one character, it is palindrome
        if (i == j) {
            return 1;
        }

        // construct a unique map key from dynamic elements of the input
        String key = i + "|" + j;

        // if sub-problem is seen for the first time, solve it and
        // store its result in a map
        if (!lookup.containsKey(key))
        {
			/* if last character of the string is same as the first character
			include first and last characters in palindrome and recur
			for the remaining subString X[i+1, j-1] */

            if (X.charAt(i) == X.charAt(j)) {
                lookup.put(key, longestPalindrome(X, i + 1, j - 1, lookup) + 2);
            }
            else {

			/* if last character of string is different to the first character

			1. Remove last char & recur for the remaining subString X[i, j-1]
			2. Remove first char & recur for the remaining subString X[i+1, j]
			3. Return maximum of the two values */

                lookup.put(key, Integer.max(longestPalindrome(X, i, j - 1, lookup),
                        longestPalindrome(X, i + 1, j, lookup)));
            }
        }

        // return the sub-problem solution from the map
        return lookup.get(key);
    }

    // Longest Palindromic Subsequence using Dynamic Programming
    public static void main(String[] args)
    {
        String X = "ABBDCACB";
        int n = X.length();

        // create a map to store solutions of subproblems
        Map<String, Integer> lookup = new HashMap<>();

        System.out.print("The length of Longest Palindromic Subsequence is " +
                longestPalindrome(X, 0, n - 1, lookup));
    }
}