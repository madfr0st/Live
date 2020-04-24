
import java.io.*;
import java.util.*;

public class B {

    public static void main(String[] args) throws IOException {
        BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

        int testCase = Integer.parseInt(inp.readLine());

        while (testCase-- > 0) {
            String[] s1 = inp.readLine().split(" ");
            int size = Integer.parseInt(s1[0]);
            int k = Integer.parseInt(s1[1]);

            int[] given = new int[size];
            s1 = inp.readLine().split(" ");

            for (int i = 0; i < size; i++) {

                given[i] = Integer.parseInt(s1[i]);

            }

            int[] dp = new int[size];

            int max = 0;
            int at = 0;

            for (int i = size-2; i >0; i--) {

                if (given[i - 1] < given[i] && given[i + 1] < given[i]) {
                    dp[i]++;
                    dp[i]+=dp[i+1];
                } else {
                    dp[i] += dp[i +1];
                }

            }

            dp[0] = dp[1];
            //print(dp);

            for(int i=size-1;i>k-2;i--){

                int a = dp[i-k+2] - dp[i];
                //System.out.println((i-k+2)+ " "+(i));

                if(a>=max){
                    max = a;
                    at = i-k+1+1;
                }

            }

            max++;
            out.write(max +" "+ at+"\n");


        }

        out.flush();
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

    static void print(boolean[] array) {
        for (int j = 0; j < array.length; j++) {
            System.out.print(array[j] + " ");
        }
        System.out.println();
    }

    static void print(boolean[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }
    }

    static void print(long[] array) {
        for (int j = 0; j < array.length; j++) {
            System.out.print(array[j] + " ");
        }
        System.out.println();
    }

    static void print(long[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }
    }

    static void print(String[] array) {
        for (int j = 0; j < array.length; j++) {
            System.out.print(array[j] + " ");
        }
        System.out.println();
    }

    static void print(String[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }
    }

}