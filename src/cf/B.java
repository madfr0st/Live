package cf;

import java.io.*;
import java.util.*;


public class B {

    static long modulo = 1000000007l;

    static class Pair<U extends Comparable<U>, V extends Comparable<V>>
            implements Comparable<Pair<U,V>>{

        public U a;
        public V b;

        private Pair(U a, V b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;

            Pair<?, ?> pair = (Pair<?, ?>) o;
            if (!a.equals(pair.a))
                return false;
            return b.equals(pair.b);
        }

        @Override
        public int hashCode() {
            return 31 * a.hashCode() + b.hashCode();
        }

        @Override
        public String toString() {
            return "(" + a + ", " + b + ")";
        }

        @Override
        public int compareTo(Pair<U, V> o) {
            if(this.b.equals(o.b)){
                return getU().compareTo(o.getU());
            }
            return getV().compareTo(o.getV());
        }
        private U getU() {
            return a;
        }
        private V getV() {
            return b;
        }
    }

    static BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
    static List<Pair<Double,Double>> list;

    public static void main(String[] args) throws IOException {

        int size = Integer.parseInt(inp.readLine());
        double[] ans = new double[size];
        list = new ArrayList<>();
        for(int i=0;i<size;i++){
            String[] a  = inp.readLine().split(" ");
            list.add(new Pair<>(Double.parseDouble(a[0]),Double.parseDouble(a[1])));
        }

        Collections.sort(list,Collections.reverseOrder());
        //System.out.println(list);

        double[] maxSum = new double[size];

        double sum = 0;

        for(int i=0;i<size;i++){
            sum+=list.get(i).b;
            maxSum[i] = sum;
        }

        double[][] dp = new double[size][size+1];
        boolean[][][] bool = new boolean[size+1][size+1][size+1];

        bool[0][1][0] = true;
        dp[0][1] = check(sum,bool[0][1]);


        for(int i=1;i<size;i++){
            for(int j=1;j<=size;j++){


                bool[i-1][j-1][i] = true;

                double a = check(sum,bool[i-1][j-1]);

                double b = dp[i-1][j];

                if(a>=b){
                    bool[i][j] = bool[i-1][j-1];
                    dp[i][j] = a;
                }
                else{
                    dp[i][j] = dp[i-1][j];
                }

            }
        }


        for(int i=0;i<size;i++){
            System.out.print(dp[size-1][i+1]+" ");
        }

        //print(dp);



    }


    static double check(double maxSum,boolean[] bool){
        double capacitySum = 0;
        double empty = 0;
        double sum = 0;
        for(int i=0;i<bool.length-1;i++){
            if(bool[i]){
                empty += list.get(i).a-list.get(i).b;
                sum+=list.get(i).a;
                capacitySum+=list.get(i).b;
            }
        }

        double max = capacitySum+ Math.min(empty,(maxSum-capacitySum)/2);
        //System.out.println(max+" "+Arrays.toString(bool));
        return max;
    }

    static void decToBinary(int n,int[][] coutt,int p)
    {

        for (int i = 31; i >= 0; i--) {
            int k = n >> i;
            if ((k & 1) > 0) {
                coutt[p+1][i]++;
            }
        }
    }
    static int gcd(int a, int b)
    {
        if (b == 0)
            return a;
        return gcd(b, a % b);
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
    static void print(double[] array){
        for(int j=0;j<array.length;j++){
            System.out.print(array[j]+" ");
        }
        System.out.println();
    }
    static void print(double[][] array){
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