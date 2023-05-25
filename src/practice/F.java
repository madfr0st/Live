package practice;

import java.io.*;
import java.util.*;

public class F {

    static BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {


        String[] s1 = inp.readLine().split("");
        String[] s2 = inp.readLine().split("");
        int ans = 0;
        int[][] dp = new int[s1.length+1][s2.length+1];

        for(int i=1;i<=s1.length;i++){
            for(int j=1;j<=s2.length;j++){
                if(s1[i-1].equals(s2[j-1])){
                    dp[i][j] = Math.max(dp[i][j],dp[i-1][j-1]+1);
                }
                dp[i][j] = Math.max(dp[i][j],dp[i-1][j]);
                dp[i][j] = Math.max(dp[i][j],dp[i][j-1]);
            }
        }
        String s3 = "";

        int i = s1.length;
        int j = s2.length;

        while (i>0 && j>0){
             if(dp[i][j]==dp[i-1][j]){
                i--;
            }
            else  if(dp[i][j]==dp[i][j-1]){
                j--;
            }
            else if(dp[i][j]>dp[i-1][j-1]){
                s3 = s1[i-1]+s3;
                i--;
                j--;
            }
        }

        System.out.println(s3);

       out.flush();

    }

    static void print(int[] array){
        for(int j=0;j<array.length;j++){
            System.out.print(array[j]+" ");
        }
        System.out.println();
    }
    static void print(int[][] array){
        for(int i=0;i< array.length;i++) {
            for (int j = 0; j < array[0].length; j++) {
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }
    }
    static void print(boolean[] array){
        for(int j=0;j<array.length;j++){
            System.out.print(array[j]+" ");
        }
        System.out.println();
    }
    static void print(boolean[][] array){
        for(int i=0;i< array.length;i++) {
            for (int j = 0; j < array[0].length; j++) {
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }
    }
    static void print(long[] array){
        for(int j=0;j<array.length;j++){
            System.out.print(array[j]+" ");
        }
        System.out.println();
    }
    static void print(long[][] array){
        for(int i=0;i< array.length;i++) {
            for (int j = 0; j < array[0].length; j++) {
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }
    }
    static void print(String[] array){
        for(int j=0;j<array.length;j++){
            System.out.print(array[j]+" ");
        }
        System.out.println();
    }
    static void print(String[][] array){
        for(int i=0;i< array.length;i++) {
            for (int j = 0; j < array[0].length; j++) {
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }
    }


}
