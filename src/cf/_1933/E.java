package cf._1933;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;


public class E {

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

    public static class Graph{
        int vertices;
        int[] maxCost;
        ArrayList<ArrayList<Integer>> edges;
        int cost;
        Graph(int v,int cost){
            this.vertices = v;
            this.cost = cost;
            edges = new ArrayList<>();
            for(int i=0;i<vertices+1;i++){
                edges.add(new ArrayList<>());
                maxCost = new int[this.vertices+1];
            }
        }

        void addEdge(int a, int b){
           edges.get(a).add(b);
           edges.get(b).add(a);
        }

        void DFS(int v){
            boolean[] visited = new boolean[v+1];
            DFSutil( v, visited);
        }

        private void DFSutil(int v, boolean[] visited) {
            if(!visited[v]){
                visited[v] = true;
                Iterator<Integer> iterator = edges.get(v).iterator();
                if(iterator.hasNext()){
                    int a = iterator.next();
                    if(!visited[a]){
                        DFSutil(a,visited);
                    }
                }
            }
        }
    }

    private static void merge(int[] arr, int left, int middle, int right) {

        int size1 = middle - left + 1;
        int size2 = right - middle;

        /* Create temp arrays */
        int[] Left = new int [size1];
        int[] Right = new int [size2];

        /*Copy data to temp arrays*/
        for (int i=0; i<size1; ++i) {
            Left[i] = arr[left + i];
        }
        for (int j=0; j<size2; ++j) {
            Right[j] = arr[middle + 1 + j];
        }

        /* Merge the temp arrays */

        // Initial indexes of first and second subarrays
        int i = 0, j = 0;

        // Initial index of merged subarry array
        int k = left;
        while (i < size1 && j < size2)
        {
            if (Left[i] <= Right[j])
            {
                arr[k] = Left[i];
                i++;
            }
            else
            {
                arr[k] = Right[j];
                j++;
            }
            k++;
        }

        /* Copy remaining elements of L[] if any */
        while (i < size1)
        {
            arr[k] = Left[i];
            i++;
            k++;
        }

        /* Copy remaining elements of R[] if any */
        while (j < size2)
        {
            arr[k] = Right[j];
            j++;
            k++;
        }
    }

    // Main function that sorts arr[l..r] using
// merge()
    static void sort(int[] arr, int left, int right) {
        if (left < right)
        {
            // Find the middle point
            int m = (left+right)/2;

            // Sort first and second halves
            sort(arr, left, m);
            sort(arr , m+1, right);

            // Merge the sorted halves
            merge(arr, left, m, right);
        }
    }

    private static void merge(long[] arr, int left, int middle, int right) {

        int size1 = middle - left + 1;
        int size2 = right - middle;

        /* Create temp arrays */
        long[] Left = new long [size1];
        long[] Right = new long [size2];

        /*Copy data to temp arrays*/
        for (int i=0; i<size1; ++i) {
            Left[i] = arr[left + i];
        }
        for (int j=0; j<size2; ++j) {
            Right[j] = arr[middle + 1 + j];
        }

        /* Merge the temp arrays */

        // Initial indexes of first and second subarrays
        int i = 0, j = 0;

        // Initial index of merged subarry array
        int k = left;
        while (i < size1 && j < size2)
        {
            if (Left[i] <= Right[j])
            {
                arr[k] = Left[i];
                i++;
            }
            else
            {
                arr[k] = Right[j];
                j++;
            }
            k++;
        }

        /* Copy remaining elements of L[] if any */
        while (i < size1)
        {
            arr[k] = Left[i];
            i++;
            k++;
        }

        /* Copy remaining elements of R[] if any */
        while (j < size2)
        {
            arr[k] = Right[j];
            j++;
            k++;
        }
    }

    // Main function that sorts arr[l..r] using
// merge()
    static void sort(long[] arr, int left, int right) {
        if (left < right)
        {
            // Find the middle point
            int m = (left+right)/2;

            // Sort first and second halves
            sort(arr, left, m);
            sort(arr , m+1, right);

            // Merge the sorted halves
            merge(arr, left, m, right);
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
