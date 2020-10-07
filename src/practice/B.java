package practice;

import java.io.*;
import java.util.*;


public class B {

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


    public static void main(String[] args) throws IOException {



        out.flush();


    }
    static boolean check(int mid,int[] given,int size){

        Set<Integer> set = new HashSet<>();
        Map<Integer,Integer> map = new HashMap();

        for(int i=mid;i<size;i++){
            if(set.contains(given[i])){
                map.put(given[i],map.get(given[i])+1);
            }
            else{
                map.put(given[i],1);
                set.add(given[i]);
            }
        }

        boolean ans = false;

        boolean one = true;
        Iterator<Integer> iterator = set.iterator();
        while (iterator.hasNext()){
            if(map.get(iterator.next())>1){
                one = false;
            }
        }
        if(one){
            ans = true;
        }

        for(int i=0;i<size-mid;i++){
            int a = given[i+mid];
            if(set.contains(a)){
                if(map.get(a)>1){
                    map.put(a,map.get(a)-1);
                }
                else{
                    map.put(a,0);
                    set.remove(a);
                }
            }

            a = given[i];
            if(set.contains(a)){
                map.put(a,map.get(a)+1);
            }
            else{
                set.add(a);
                map.put(a,1);
            }
            iterator = set.iterator();
            one = true;
            while (iterator.hasNext()){
                if(map.get(iterator.next())>1){
                    one = false;
                }
            }
            if(one){
                ans = true;
            }
        }

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

