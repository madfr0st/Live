package practice;

import java.io.*;
import java.util.*;


public class G {

    static class Pair<U extends Comparable<U>, V extends Comparable<V>>
            implements Comparable<Pair<U,V>>{

        final public U a;
        final public V b;

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
                    System.out.print(pairs[i][j] + " ");
                }
                System.out.println();
            }
        }
    }


    static BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    public static class Graph{
        int vertices;
        int[] dp;
        int ans = 0;
        ArrayList<ArrayList<Integer>> edges = new ArrayList<>();
        Graph(int vertices){
            dp = new int[vertices+1];
            //Arrays.fill(dp,1);
            this.vertices = vertices;
            for(int i=0;i<=vertices;i++){
                edges.add(new ArrayList<>());
            }
        }

        void addEdge(int a,int b){
            edges.get(a).add(b);
        }

        void DFS(){
            boolean[] visited = new boolean[this.vertices+1];
            for(int i=1;i<=vertices;i++){
                if(!visited[i]) {
                    ans = Math.max( DFSutil(i, visited),ans);
                }
            }
            System.out.println(ans-1);
        }

        int DFSutil(int v, boolean[] visited){
            int max = 0;
            if(!visited[v]){
                visited[v] = true;
                if(edges.get(v).size()==0){
                    dp[v] = 1;
                    return 1;
                }
                for (int a : edges.get(v)) {
                    max = Math.max(DFSutil(a, visited) + 1, max);
                }

                dp[v] = Math.max(dp[v],max);
                ans = Math.max(dp[v],ans);
                return dp[v];
            }
            return dp[v];
        }


    }

    public static void main(String[] args) throws IOException {

        String[] s1 = inp.readLine().split(" ");
        int v = Integer.parseInt(s1[0]);
        int e = Integer.parseInt(s1[1]);
        Graph graph = new Graph(v);
        for(int i=0;i<e;i++){
            s1 = inp.readLine().split(" ");
            graph.addEdge(Integer.parseInt(s1[0]),Integer.parseInt(s1[1]));
        }

        graph.DFS();



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

