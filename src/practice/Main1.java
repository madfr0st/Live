package practice;

import java.util.List;
import java.util.Scanner;
import java.util.Vector;


public class Main1 {

    public static int maxtoffe(List<Integer> array) {
        int n = array.size();
        if (n == 0) return 0;
        if (n == 1) return array.get(0);

        // prev = dp[i-2], curr = dp[i-1]
        int prev = array.get(0);
        int curr = Math.max(array.get(0), array.get(1));

        for (int i = 2; i < n; i++) {
            int pick = prev + array.get(i);  // pick array[i]
            int skip = curr;                 // skip array[i]
            int next = Math.max(pick, skip);
            prev = curr;
            curr = next;
        }

        return curr;
    }

    // Example usage:
    public static void main(String[] args) {
        List<Integer> arr = List.of(5, 30, 99, 60, 5, 10);
        System.out.println(maxtoffe(arr));  // Output: 114
    }
}