package dev;

import java.io.IOException;
import java.util.Arrays;

public class Bonus_Q_v2 {
    public static void main(String[] args) throws IOException {
        double gamma = .9;
        int iterations = 3;
        int gridSize = 4;

        System.out.println("Deterministic Environment:");
        // Computing the value function using deterministic updates
        double[][] dpDeterministic = calc_Q(gamma, iterations, gridSize, true);
        // Printing the greedy policy grid for the deterministic environment
        printGreedyPolicy(dpDeterministic, gamma, true);

        System.out.println("\nStochastic Environment:");
        // Computing the value function using stochastic updates
        double[][] dpStochastic = calc_Q(gamma, iterations, gridSize, false);
        // Printing the greedy policy grid for the stochastic environment
        printGreedyPolicy(dpStochastic, gamma, false);
    }

    public static double[][] calc_Q(double gamma, int k, int size, boolean deterministic) {
        double[][] dp = new double[size][size];
        double[][] dp_temp = new double[size][size];

        while (k-- > 0) {

            for (int i = 0; i < size; i++) {
                dp_temp[i] = Arrays.copyOf(dp[i], size);
            }

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {

                    if ((i == 0 && j == 0) || (i == size - 1 && j == size - 1)) {
                        dp[i][j] = 0;
                        continue;
                    }

                    double up = (j - 1 >= 0) ? dp_temp[i][j - 1] : dp_temp[i][j];
                    double down = (j + 1 < size) ? dp_temp[i][j + 1] : dp_temp[i][j];
                    double left = (i - 1 >= 0) ? dp_temp[i - 1][j] : dp_temp[i][j];
                    double right = (i + 1 < size) ? dp_temp[i + 1][j] : dp_temp[i][j];

                    if (deterministic) {
                        dp[i][j] = calc_Deterministic(up, down, left, right, gamma);
                    } else {
                        dp[i][j] = calc_Stochastic(up, down, left, right, gamma);
                    }
                }
            }

            printDP(dp, size);
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

    static void printDP(double[][] dp, int size) {
        System.out.println("Updated DP Table:");
        for (int j = 0; j < size; j++) {
            for (int i = 0; i < size; i++) {
                System.out.printf("%.2f ", dp[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void printGreedyPolicy(double[][] dp, double gamma, boolean deterministic) {
        int size = dp.length;
        System.out.println("Greedy Policy Grid:");
        for (int j = 0; j < size; j++) {
            for (int i = 0; i < size; i++) {

                if ((i == 0 && j == 0) || (i == size - 1 && j == size - 1)) {
                    System.out.printf("%-5s", "T");
                    continue;
                }

                double qUp = -1 + gamma * ((j - 1 >= 0) ? dp[i][j - 1] : dp[i][j]);
                double qDown = -1 + gamma * ((j + 1 < size) ? dp[i][j + 1] : dp[i][j]);
                double qLeft = -1 + gamma * ((i - 1 >= 0) ? dp[i - 1][j] : dp[i][j]);
                double qRight;

                if (deterministic) {
                    qRight = -1 + gamma * ((i + 1 < size) ? dp[i + 1][j] : dp[i][j]);
                } else {
                    double rightValue = (i + 1 < size) ? dp[i + 1][j] : dp[i][j];
                    double leftForRight = (i - 1 >= 0) ? dp[i - 1][j] : dp[i][j];
                    qRight = 0.9 * (-1 + gamma * rightValue) + 0.1 * (-1 + gamma * leftForRight);
                }

                double maxQ = Math.max(Math.max(qUp, qDown), Math.max(qLeft, qRight));

                double epsilon = 1e-9;

                StringBuilder actions = new StringBuilder();
                if (Math.abs(qUp - maxQ) < epsilon) {
                    actions.append("^");
                }
                if (Math.abs(qDown - maxQ) < epsilon) {
                    if (actions.length() > 0) actions.append("/");
                    actions.append("v");
                }
                if (Math.abs(qLeft - maxQ) < epsilon) {
                    if (actions.length() > 0) actions.append("/");
                    actions.append("<");
                }
                if (Math.abs(qRight - maxQ) < epsilon) {
                    if (actions.length() > 0) actions.append("/");
                    actions.append(">");
                }
                System.out.printf("%-5s", actions.toString());
            }
            System.out.println();
        }
        System.out.println();
    }


}
