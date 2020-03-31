package practice;

import java.io.*;
import java.util.*;

public class D915 {
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

    static class Graph{
        int vertices;
        ArrayList<Integer>[] edge;
        boolean[] gray;
        Set<Pair<Integer,Integer>> setOne = new HashSet<>();
        Set<Pair<Integer,Integer>> setTwo = new HashSet<>();
        Set<Pair<Integer,Integer>> visited = new HashSet<>();
        boolean once = false;
        int cycle = 0;
        Map<Integer,Integer> parent = new HashMap<>();
        Graph(int vertices){
            this.vertices = vertices;
            gray = new boolean[vertices+1];
            edge = new ArrayList[vertices+1];
            for(int i=0;i<=vertices;i++){
                edge[i] = new ArrayList<>();
            }
        }
        void addEdge(int a,int b){
            edge[a].add(b);
        }

        void DFS(){
            boolean[] visited = new boolean[vertices+1];
            for(int i=1;i<=vertices;i++) {
                if(!visited[i]) {
                    parent.put(i,0);
                    gray[i] = true;
                    visited[i] = true;
                    DFSutil(i, visited);
                    gray[i] = false;
                }
            }
            System.out.println("cycles"+cycle);
            if(cycle>1 && setOne.size()==0){
                System.out.println("NO");
            }
            else{
                System.out.println("YES");
            }
        }

        private void DFSutil(int v,boolean[] visited){
            System.out.println(v);
            if(cycle>1 && setOne.size()==0){
                return;
            }

            Iterator<Integer> iterator = edge[v].listIterator();
            while (iterator.hasNext()){
                int n = iterator.next();
                if(!visited[n]){
                    gray[n] = true;
                    parent.put(n,v);
                    visited[v] = true;
                    DFSutil(n,visited);
                    visited[v] = false;
                    gray[n] = false;
                }
                else if(gray[n]){
                    cycle++;
                    int a = v;
                    int b = -1;
                    if(!once) {
                        //System.out.println(a);
                        while (parent.get(a) != n && parent.get(a)!=0) {
                            b = parent.get(a);
                            setOne.add(new Pair<>(b,a));
                            a = b;
                        }
                        setOne.add(new Pair<>(n,a));
                        once = true;
                    }
                    else{
                        setTwo = new HashSet<>();
                        while (parent.get(a) != n && parent.get(a)!=0) {
                            b = parent.get(a);
                            if(setOne.contains(new Pair<>(b,a))) {
                                setTwo.add(new Pair<>(b, a));
                            }
                            a = b;
                        }
                        if(setOne.contains(new Pair<>(n,a))) {
                            setTwo.add(new Pair<>(n, a));
                        }
                        setOne = new HashSet<>(setTwo);
                        if(setTwo.size()==0){
                            return;
                        }
                    }
                }
            }
        }

    }
    public static void main(String[] args) throws IOException{
        BufferedReader inp = new BufferedReader( new InputStreamReader(System.in));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
        String[] s1 = inp.readLine().split(" ");
        int a = Integer.parseInt(s1[0]);
        int b =Integer.parseInt(s1[1]);
        Graph graph = new Graph(a);
        for(int i=0;i<b;i++){
            s1 = inp.readLine().split(" ");
            int c = Integer.parseInt(s1[0]);
            int d = Integer.parseInt(s1[1]);
            graph.addEdge(c,d);
        }
        graph.DFS();
    }
}
