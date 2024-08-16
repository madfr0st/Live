package leetcode;

import javax.imageio.IIOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;

public class LC1402 {
    static BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IIOException {

        System.out.println(maxSatisfaction(new int[]{-1, -8, 0, 5, -9}));

    }

    public static int maxSatisfaction(int[] satisfaction) {


        Arrays.sort(satisfaction);
        System.out.println(Arrays.toString(satisfaction));
        int max = 0;
        int sumAll = 0;
        int totalSum = 0;
        int size = satisfaction.length;
        while (size-- > 0) {

            int temp = totalSum + satisfaction[size] + sumAll;
            if (temp > 0) {
                max = Math.max(max, temp);
                totalSum = temp;
                sumAll += satisfaction[size];
            }

        }

        return max;
    }


}
