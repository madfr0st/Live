package practice;

import java.io.*;
import java.util.*;

public class E {

    static BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    static class Graph{
        int vertices;
        ArrayList<Integer> edge[];
        Graph(int vertices){
            this.vertices = vertices;
            edge = new ArrayList[vertices+1];
            for(int i=0;i<=vertices;i++){
                edge[i] = new ArrayList<>();
            }
        }

        void addEdge(int a,int b){
            edge[a].add(b);
            edge[b].add(a);
        }

        void DFS(){

        }



    }

    public static void main(String[] args) throws IOException {

        String[] s1 = inp.readLine().split(" ");
        int size = Integer.parseInt(s1[0]);
        int weight = Integer.parseInt(s1[1]);

        int[] givenWeight = new int[size];
        int[] givenValue = new int[size];

        for(int i=0;i<size;i++){
            String[] s2 = inp.readLine().split(" ");
            givenWeight[i] = Integer.parseInt(s2[0]);
            givenValue[i] = Integer.parseInt(s2[1]);
        }

        long[][] dp = new long[size+1][100002];

        int value = 100001;

        for(int i=0;i<=size;i++) {
            for (int j = 1; j <= value; j++) {
                dp[i][j] = Long.MAX_VALUE/100000;
            }
        }

        long ans = 0;

        for(int i=1;i<=size;i++){
            for(int j=0;j<=value;j++){
                    if(j+givenValue[i-1]<value ) {
                        dp[i][j + givenValue[i - 1]] = Math.min(dp[i - 1][j] + givenWeight[i - 1], dp[i][j + givenValue[i - 1]]);
                    }
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j]);

            }
        }

        for(int i=1;i<=size;i++){
            for(int j=1;j<=value;j++){
                if(dp[i][j]<=weight){
                    ans = Math.max(j,ans);
                }
            }
        }

        System.out.println(ans);


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
