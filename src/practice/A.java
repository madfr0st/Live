package practice;

import java.io.*;
import java.util.*;


public class A {

    static class Pair<U extends Comparable<U>, V extends Comparable<V>>
            implements Comparable<Pair<U,V>>{

        public final U a;
        public final V b;

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
            if(this.a.equals(o.a)){
                return getV().compareTo(o.getV());
            }
            return getU().compareTo(o.getU());
        }
        private U getU() {
            return a;
        }
        private V getV() {
            return b;
        }
        static void print(Pair[] pairs){
            for(int i=0;i<pairs.length;i++){
                System.out.print(pairs[i]+" ");
            }
            System.out.println();
        }
        static void print(Pair[][] pairs){

            for(int i=0;i<pairs.length;i++){
                for(int j=0;j<pairs[0].length;j++) {
                    System.out.print(pairs[i] + " ");
                }
                System.out.println();
            }
        }
    }


    static long modulo = 998244353;
    static BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));


    public static void main(String[] args) throws IOException {

        String[] s1 = inp.readLine().split("");
        int ans = 0;
        int left = 0;
        int right = 0;

        int sum = 0;

        boolean[] visited = new boolean[s1.length];
        int size = visited.length;

        int first = -1;

        for(int i=0;i<size;i++){
            if(s1[i].equals("(")){
                if(first==-1){
                    first = i;
                }
                left++;
            }
            else {
                right++;
            }
        }

        if(left==right){
            left = 0;
            left = 0;
            for(int i=first;i<size;i++){
                if(s1[i].equals("(")){
                    if(sum==0){
                        left = i;
                    }
                    sum++;
                }
                else{
                    sum--;
                    if(sum==0){
                        ans++;
                    }
                    if(sum<0){
                        sum = 0;
                        ans = 0;
                    }
                }
            }

            if(left>0){
                ans++;
            }


            out.write(ans+"\n");


        }
        else{
            out.write(0+"\n");
        }


        out.flush();
    }

    static int gcd(int a, int b) {
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