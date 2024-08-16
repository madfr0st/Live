package cf._1933;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;


public class C {

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

    static BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
    static Set<Integer> set = new HashSet<>();
    public static void main(String[] args) throws IOException{

        int t = Integer.parseInt(inp.readLine());
        while (t-->0){
            String[] s1 = inp.readLine().split(" ");
            int a = Integer.parseInt(s1[0]);
            int aa = a;
            int b = Integer.parseInt(s1[1]);
            int bb = b;
            int c = Integer.parseInt(s1[2]);
            set.clear();
            //set.add(1);
            set.add(c);
            aa = 1;
            while (aa<=c){
                if(c%aa==0){
                    pow(c/aa,b);
                    aa = aa*a;
                }
                else {
                    break;
                }
            }

            //System.out.println(set);
            System.out.println(set.size());


        }

    }

    static void pow(int a,int b){
        int bb = 1;
        while (a>=bb && a%bb==0){
            set.add(a/bb);
            bb=bb*b;
        }
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

