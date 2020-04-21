

import java.io.*;
import java.util.*;


public class D {

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
    }

    static BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {

        int testCase = Integer.parseInt(inp.readLine());
        while (testCase-->0){
            int size = Integer.parseInt(inp.readLine());
            int[] given = new int[size];
            String[] s1 = inp.readLine().split(" ");
            for(int i=0;i<size;i++){
                given[i] = Integer.parseInt(s1[i]);
            }
            int size1 = Integer.parseInt(inp.readLine());
            Pair<Integer,Integer>[] pairs = new Pair[size1];
            for(int i=0;i<size1;i++){
                s1 = inp.readLine().split(" ");
                int a = Integer.parseInt(s1[0]);
                int b = Integer.parseInt(s1[1]);
                pairs[i] = new Pair<>(a,b);
            }
            Map<Integer,Integer> map = new HashMap<>();
            int d = -1;
            int max = 0;
            for(int i=0;i<size1;i++){
                int c = pairs[i].b ;
                if(map.containsKey(c)){
                    map.put(c,Math.max(map.get(c),pairs[i].a));
                }
                else{
                    map.put(c,pairs[i].a);
                }
            }

            //System.out.println(map);

            int[] maxDamage = new int[size];

            if(map.containsKey(size)) {
                maxDamage[size-1] = map.get(size);
            }

            for(int i=size-2;i>=0;i--){

                if(map.containsKey(i+1)){
                    maxDamage[i] = Math.max(maxDamage[i+1],map.get(i+1));
                }
                else{
                    maxDamage[i] = maxDamage[i+1];
                }

            }

            //print(maxDamage);
            int count = 0;
            boolean ans = true;
            for(int i=0;i<size;i++){
                int a = given[i];
                max = Math.max(a,max);

                if(max>maxDamage[0]){
                    ans = false;
                    break;
                }

                if(maxDamage[d+1]>=max){
                    d++;
                }
                else{
                    //System.out.println(max);
                    d = -1;
                    count++;
                    max = 0;
                    i--;
                }
            }

            if(d>-1){
                count++;
            }

            if(ans) {
                out.write(count + "\n");

            }
            else{
                out.write(-1+"\n");
            }




        }
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
    static long calc(int a,int b){
        long c = b-a+1;
        c = c*(c+1);
        c/=2l;
        return c;
    }
}