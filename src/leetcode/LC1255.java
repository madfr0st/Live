package leetcode;

import javax.imageio.IIOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Stack;

public class LC1255 {
    static BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IIOException {

        System.out.println(maxScoreWords(
                new String[]{"azb", "ax", "awb", "ayb", "bpppp"},
                new char[]{'z', 'a', 'w', 'x', 'y', 'b', 'p', 'p', 'p'},
                new int[]{10, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 3, 2, 3, 3}));

    }

    public static int maxScoreWords(String[] words, char[] letters, int[] score) {
        Arrays.sort(letters);
        int max = 0;
        boolean[] used = new boolean[words.length];
        int index = 0;

        max = checkScore(words, letters, score, used, max, index);
        boolean[] test = new boolean[1];
        test = used.clone();
        test[index] = true;
        max = Math.max(max, checkScore(words, letters, score, test, max, index));


        return max;
    }

    public static int checkScore(String[] words, char[] letters, int[] score, boolean[] used, int max, int index) {

        System.out.println(Arrays.toString(used));
        max = Math.max(max, calcScore(words, letters, score, used, max, index));

        if (index == words.length - 1) {
            return max;
        }

        index++;
        max = Math.max(checkScore(words, letters, score, used, max, index), max);
        boolean[] test = new boolean[1];
        test = used.clone();
        test[index] = true;
        max = Math.max(checkScore(words, letters, score, test, max, index), max);
        return max;
    }

    public static int calcScore(String[] words, char[] letters, int[] score, boolean[] used, int max, int index) {

        StringBuilder s1 = new StringBuilder();
        Stack<String> stack1 = new Stack<>();
        Stack<String> stack2 = new Stack<>();
        for (int i = 0; i < words.length; i++) {
            if (used[i]) {
                s1.append(words[i]);
            }
        }

        if (s1.isEmpty()) {
            return 0;
        }

        String[] s2 = s1.toString().split("");
        Arrays.sort(s2);

        stack1.addAll(Arrays.asList(s2));
        for (char letter : letters) {
            stack2.add(String.valueOf(letter));
        }

        while (!stack2.isEmpty()) {
            if (stack1.isEmpty()) {
                break;
            }
            if (stack1.peek().equals(stack2.peek())) {
                stack1.pop();
                stack2.pop();
            } else {
                stack2.pop();
            }
        }

        if (!stack1.isEmpty()) {
            return 0;
        }


        int sum = 0;

        System.out.println(Arrays.toString(s2));

        int[][] dp = new int[s2.length + 1][letters.length + 1];
        for (int i = 1; i <= s2.length; i++) {
            for (int j = 1; j <= letters.length; j++) {

                if (s2[i - 1].charAt(0) == letters[j - 1]) {
                    dp[i][j] = Math.max(dp[i - 1][j - 1] + score[letters[j - 1] - 'a'], dp[i][j]);
                }
                dp[i][j] = Math.max(dp[i - 1][j], dp[i][j]);
                dp[i][j] = Math.max(dp[i][j - 1], dp[i][j]);
                sum = Math.max(sum, dp[i][j]);
            }
            print(dp);
            System.out.println(sum);
            System.out.println("\n");
        }

        return sum;
    }

    static void print(int[] array) {
        for (int j = 0; j < array.length; j++) {
            System.out.print(array[j] + " ");
        }
        System.out.println();
    }

    static void print(int[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }
    }


}
