package practice;

import java.io.*;
import java.util.*;

public class E545 {
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
        ArrayList<Pair<Long,Long>>[] edge;
        Map<Pair<Long,Pair<Long,Long>>,Long> index = new HashMap<>();
        Graph(int vertices){
            this.vertices = vertices;
            edge = new ArrayList[vertices+1];
            for(int i=0;i<=vertices;i++){
                edge[i] = new ArrayList();
            }
        }
        void addEdge(long a,long b,long c,long d){
            edge[(int)a].add(new Pair<>(b,c));
            edge[(int)b].add(new Pair<>(a,c));
            index.put(new Pair<>(Math.min(a,b),new Pair<>(Math.max(a,b),c)),d);
        }

        void dijkstra(long v) throws IOException{

            int count = 1;
            Map<Long,Long> dist = new HashMap<>();
            dist.put(v,0L);
            ArrayList<Long> ans = new ArrayList<>();
            long sum = 0L;
            PriorityQueue<Pair<Long,Pair<Long,Pair<Long,Long>>>> priorityQueue = new PriorityQueue<>();
            boolean[] visited = new boolean[vertices+1];

            Iterator<Pair<Long,Long>> iterator = edge[(int)v].listIterator();
            while (iterator.hasNext()){
                Pair<Long,Long> pair = iterator.next();
                priorityQueue.add(new Pair<>(pair.b,new Pair<>(pair.b,new Pair<>(pair.a,v))));
            }
            visited[(int)v] = true;
            while (priorityQueue.size()>0){
                Pair<Long,Pair<Long,Pair<Long,Long>>> pair = priorityQueue.poll();
                if(!visited[Math.toIntExact((pair.b.b.a))]){
                    count++;
                    while (true){
                        if(priorityQueue.size()>0) {
                            Pair<Long, Pair<Long, Pair<Long, Long>>> pair1 = priorityQueue.peek();
                            if (pair.a.equals(pair1.a) && pair.b.b.a.equals(pair1.b.b.a)) {
                                if (pair1.b.a < pair.b.a) {
                                    pair = pair1;
                                }
                                priorityQueue.poll();
                            } else {
                                break;
                            }
                        }
                        else{
                            break;
                        }
                    }
                    visited[Math.toIntExact(pair.b.b.a)] = true;
                    dist.put(pair.b.b.a,dist.get(pair.b.b.b)+pair.b.a);
                    sum += pair.b.a;
                    ans.add(index.get(new Pair<>(Math.min(pair.b.b.a,pair.b.b.b),new Pair<>(Math.max(pair.b.b.a,pair.b.b.b),pair.b.a))));
                    Iterator<Pair<Long,Long>> iterator1 = edge[Math.toIntExact(pair.b.b.a)].listIterator();
                    while (iterator1.hasNext()){
                        Pair<Long,Long> pair1 = iterator1.next();
                        if(!visited[Math.toIntExact(pair1.a)]) {
                            priorityQueue.add(new Pair<>(dist.get(pair.b.b.a) + pair1.b, new Pair<>(pair1.b, new Pair<>(pair1.a, pair.b.b.a))));
                        }
                    }
                }
                if(count==vertices){
                    break;
                }
            }
//            System.out.println(sum);
//            System.out.println(ans);
            out.write(sum+"\n");
           for(int i=0;i<ans.size();i++){
               out.write(ans.get(i)+" ");
           }
           out.flush();

        }

    }
    static BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {

        String[] s1 = inp.readLine().split(" ");
        long v = Long.parseLong(s1[0]);
        long e = Long.parseLong(s1[1]);
        Graph graph = new Graph((int) v);
        for(int i=0;i<e;i++){
            s1 = inp.readLine().split(" ");
            long a = Long.parseLong(s1[0]);
            long b = Long.parseLong(s1[1]);
            long c = Long.parseLong(s1[2]);
            graph.addEdge(a,b,c,i+1);
        }
        long at = Long.parseLong(inp.readLine());
        graph.dijkstra(at);

    }
}
