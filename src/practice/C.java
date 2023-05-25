package practice;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;


public class C {

    static class Pair<U extends Comparable<U>, V extends Comparable<V>>
            implements Comparable<Pair<U,V>> {

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
            if (this.a.equals(o.a)) {
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

        static void print(Pair[] pairs) {
            for (int i = 0; i < pairs.length; i++) {
                System.out.print(pairs[i] + " ");
            }
            System.out.println();
        }

        static void print(Pair[][] pairs) {

            for (int i = 0; i < pairs.length; i++) {
                for (int j = 0; j < pairs[0].length; j++) {
                    System.out.print(pairs[i] + " ");
                }
                System.out.println();
            }
        }
    }


    static BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {

        int t = Integer.parseInt(inp.readLine());
        while (t-->0){
            int size = Integer.parseInt(inp.readLine());
            long[] eachCupTea = new long[size+1];
            long[] individualCapacitySum = new long[size];
            long[] g3 = new long[size+1];
            String[] s1 = inp.readLine().split(" ");
            String[] s2 = inp.readLine().split(" ");
            long[] ans = new long[size+1];
            long[] carry = new long[size+1];
            long sum = 0;
            for(int i=0;i<size;i++){
                eachCupTea[i] = Long.parseLong(s1[i]);
                g3[i] = Long.parseLong(s2[i]);
                sum += Long.parseLong(s2[i]);
                individualCapacitySum[i] = sum;

            }
            sum = 0l;
            for(int i=0;i<size;i++){

                int j = Arrays.binarySearch(individualCapacitySum,i,size-1,eachCupTea[i]+sum);
//                System.out.println(j);
                j=-j;
                j=j-2;
                if(j>=i){
//                    System.out.println(j+"--");
                    if(j>=i){

                            ans[i] += 1l;
                            ans[j + 1] -= 1l;

                        carry[j+1] += Math.min(eachCupTea[i]+sum-individualCapacitySum[j],g3[j+1]);
//                        System.out.println(Arrays.toString(ans)+"--");
//                        System.out.println(Arrays.toString(carry)+"----");
                    }
                    if(j==-1){
                        carry[i] += Math.min(eachCupTea[i],g3[j+1]);
                    }
                }
                else if(j+1==i){
                    carry[j+1] += Math.min(eachCupTea[i],g3[j+1]);
                }

                sum+=g3[i];
            }

            long sum1 = 0l;

//            System.out.println(Arrays.toString(ans));
            for(int i=0;i<size;i++){
                sum1+=ans[i];
                ans[i] = sum1*g3[i]+carry[i];
            }

//            System.out.println(Arrays.toString(individualCapacitySum)+"  individualCapacitySum");
//            System.out.println(Arrays.toString(eachCupTea)+"  eachCupTea");
//            System.out.println(Arrays.toString(g3));
//            System.out.println(Arrays.toString(carry));
//            System.out.println(Arrays.toString(ans));

            for(int i=0;i<size;i++){
                System.out.print(ans[i]+" ");
            }
            System.out.println();


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

