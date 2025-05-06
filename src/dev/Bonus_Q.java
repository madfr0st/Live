package dev;

import java.io.IOException;
import java.util.Arrays;

public class Bonus_Q {
    public static void main(String[] args) throws IOException {
        double gamma = 1;
        int iterations = 4;
        int gridSize = 4;

        calc_Q(gamma, iterations, gridSize, true );
        calc_Q(gamma,iterations,gridSize, false);
    }

    public static double[][] calc_Q(double gamma, int k, int size, boolean deterministic) {
        double[][] dp = new double[size][size];
        double[][] dp_temp = new double[size][size];

        while (k-- > 0) {
            // Deep copy of dp to dp_temp before updating dp
            for (int i = 0; i < size; i++) {
                dp_temp[i] = Arrays.copyOf(dp[i], size);
            }

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {

                    // Terminal states
                    if ((i == 0 && j == 0) || (i == size - 1 && j == size - 1)) {
                        dp[i][j] = 0;
                        continue;
                    }

                    // Getting values from adjacent cells
                    double up = (j - 1 >= 0) ? dp_temp[i][j - 1] : dp_temp[i][j];
                    double down = (j + 1 < size) ? dp_temp[i][j + 1] : dp_temp[i][j];
                    double left = (i - 1 >= 0) ? dp_temp[i - 1][j] : dp_temp[i][j];
                    double right = (i + 1 < size) ? dp_temp[i + 1][j] : dp_temp[i][j];

                    // Compute Q-value
                    if(deterministic) {
                        dp[i][j] = calc_Deterministic(up, down, left, right, gamma);
                    }
                    else {
                        dp[i][j] = calc_Stochastic(up, down, left, right, gamma);
                    }
                }
            }

            // Print the DP table after each iteration
            print(dp, size);
        }

        return dp;
    }

    public static double calc_Deterministic(double up, double down, double left, double right, double gamma) {
        return 0.25 * (-1 + gamma * up) +
                0.25 * (-1 + gamma * down) +
                0.25 * (-1 + gamma * left) +
                0.25 * (-1 + gamma * right);
    }

    public static double calc_Stochastic(double up, double down, double left, double right, double gamma) {
        // For actions up, down, and left, use the same update as the deterministic case.
        // For action Right, combine the outcomes: 90% chance for right and 10% chance for left.
        double rightActionValue = 0.9 * (-1 + gamma * right) + 0.1 * (-1 + gamma * left);
        return 0.25 * (-1 + gamma * up) +
                0.25 * (-1 + gamma * down) +
                0.25 * (-1 + gamma * left) +
                0.25 * rightActionValue;
    }

    static void print(double[][] dp, int size) {
        System.out.println("Updated DP Table:");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.printf("%.2f ", dp[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
}
