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
    static int[] array;

    public static void main(String[] args) throws IOException {


        int t = Integer.parseInt(inp.readLine());
        while (t-->0){

            int size = Integer.parseInt(inp.readLine());
            array = new int[size+1];

            int i = 1;
            PriorityQueue<Pair<Integer,Pair<Integer,Integer>>> priorityQueue = new PriorityQueue<>();
            priorityQueue.add(new Pair<>(-size,new Pair<>(1,size)));
            while (priorityQueue.size()>0){
                Pair<Integer,Pair<Integer,Integer>> pairPair = priorityQueue.poll();
                //System.out.println(pairPair);
                int a = -pairPair.a;
                int l = pairPair.b.a;
                int r = pairPair.b.b;
                int mid= 0;
                if(l==r){
                    array[l] = i;
                }
                else if(a%2==1){
                    mid = (l+r)/2;
                    array[mid] = i;
                    if(l<=mid-1){
                        priorityQueue.add(new Pair<>(-a/2,new Pair<>(l,mid-1)));
                    }
                    if(r>=mid+1){
                        priorityQueue.add(new Pair<>(-a/2,new Pair<>(mid+1,r)));
                    }
                }
                else{
                    mid = (l+r-1)/2;
                    array[mid] = i;
                    if(l<=mid-1){
                        priorityQueue.add(new Pair<>(-a/2+1,new Pair<>(l,mid-1)));
                    }
                    if(r>=mid+1){
                        priorityQueue.add(new Pair<>(-a/2,new Pair<>(mid+1,r)));
                    }
                }

                i++;
            }

            for(i=0;i<size;i++){
                out.write(array[i+1]+" ");
            }
            //print(array);
    out.write("\n");

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
}