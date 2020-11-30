import java.util.*;

public class Solution1 {
    public int solve(String[] s){

        int size = s.length;

        int ans = 0;

        int[] dp = new int[size];

        Stack<Integer> stack = new Stack<>();
        int count = 0;

        for(int i=0;i<size;i++){
            if(s[i].equals("(")){
                stack.push(i);
            }
            else if(stack.size()>0){
                int a = stack.pop();
                dp[i] += dp[i-1]+2;
                if(a>0) {
                    dp[i] += dp[a - 1];
                }
                ans = Math.max(dp[i],ans);
            }
        }

//        print(s);
//        print(dp);


        return ans;
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
