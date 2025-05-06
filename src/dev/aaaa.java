package dev;

public class aaaa {


    public static void main(String[] args){
        System.out.println(getMaxOnesSumExcludingLast("100110"));
    }

    public static int getMaxOnesSumExcludingLast(String s) {
        int n = s.length();
        if (n == 0) {
            return 0; // No characters, no flips
        }
        if (n == 1) {
            return (s.charAt(0) == '0') ? 1 : 0;
        }


        int totalOnes = 0;
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '1') {
                totalOnes++;
            }
        }
        int[] B = new int[n];
        for (int i = 0; i < n; i++) {
            B[i] = (s.charAt(i) == '1') ? 1 : -1;
        }

        int maxSubSum = UpToIndex(B, n - 2);
        int answer = (n - totalOnes) + maxSubSum;

        return answer;
    }

    private static int UpToIndex(int[] arr, int endIndex) {
        if (endIndex < 0) {
            return 0;
        }
        int currentSum = 0;
        int bestSum = Integer.MIN_VALUE;

        for (int i = 0; i <= endIndex; i++) {
            int x = arr[i];
            if (currentSum + x >= x) {
                currentSum = currentSum + x;
            } else {
                currentSum = x;
            }
            if (currentSum > bestSum) {
                bestSum = currentSum;
            }
        }

        // Also consider the possibility of picking no subarray at all => sum=0
        // if that can yield a better result. That might be relevant if all numbers are negative.
        // Only do this if the problem statement allows an "empty" subarray.
        // Usually, "selecting an interval" implies we must pick something.
        // We'll show both versions:
        bestSum = Math.max(bestSum, 0);

        return bestSum;
    }
}
