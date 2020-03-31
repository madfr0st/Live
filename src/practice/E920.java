package practice;


import java.io.*;
import java.util.*;

public class E920 {
    static public class DisjointSet {

        private Map<Long, Node> map = new HashMap<>();

        class Node {
            long data;
            Node parent;
            int rank;

            @Override
            public String toString() {
                return findSet(data) + "";
            }
        }


        /**
         * Create a set with only one element.
         */
        public void makeSet(long data) {
            Node node = new Node();
            node.data = data;
            node.parent = node;
            node.rank = 0;
            map.put(data, node);
        }

        /**
         * Combines two sets together to one.
         * Does union by rank
         *
         * @return true if data1 and data2 are in different set before union else false.
         */
        public boolean union(long data1, long data2) {
            Node node1 = map.get(data1);
            Node node2 = map.get(data2);

            Node parent1 = findSet(node1);
            Node parent2 = findSet(node2);

            //if they are part of same set do nothing
            if (parent1.data == parent2.data) {
                return false;
            }

            //else whoever's rank is higher becomes parent of other
            if (parent1.data > parent2.data) {
                //increment rank only if both sets have same rank
                parent1.rank = (parent1.rank == parent2.rank) ? parent1.rank + 1 : parent1.rank;
                parent2.parent = parent1;
            } else {
                parent1.parent = parent2;
            }
            return true;
        }

        /**
         * Finds the representative of this set
         */
        public long findSet(long data) {
            return findSet(map.get(data)).data;
        }

        /**
         * Find the representative recursively and does path
         * compression as well.
         */
        private Node findSet(Node node) {
            Node parent = node.parent;
            if (parent == node) {
                return parent;
            }
            node.parent = findSet(node.parent);
            return node.parent;
        }
    }

    static class Graph {
        int vertices;
        Set<Integer>[] edge;
        DisjointSet disjointSet = new DisjointSet();
        Map<Integer,Integer> dad = new HashMap<>();
        static Queue<Integer> unvisited = new ArrayDeque<>();
        static boolean[] visited;

        Graph(int vertices) throws IOException {
            this.vertices = vertices;
            visited = new boolean[vertices + 1];
            edge = new HashSet[vertices + 1];
            for (int i = 1; i <= vertices; i++) {
                edge[i] = new HashSet<>();
                disjointSet.makeSet(i);
                unvisited.add(i);
            }
        }

        void addEdge(int a, int b) {
            edge[a].add(b);
            edge[b].add(a);
        }

        void bfs() throws IOException {

            while (unvisited.size()>0){
                int a = unvisited.poll();
                bfsUtil(a,a);
            }

            Set<Integer> set = new HashSet<>();
            Map<Integer, Integer> map = new HashMap<>();
            for (
                    int i = 1;
                    i <= vertices; i++) {
                int a = (int) disjointSet.findSet(i);
                if (set.contains(a)) {
                    map.put(a, map.get(a) + 1);
                } else {
                    map.put(a, 1);
                    set.add(a);
                }
            }

            Iterator<Integer> iterator = set.iterator();
            out.write(set.size() + "\n");
            ArrayList<Integer> list = new ArrayList<>();
            while (iterator.hasNext()) {
                list.add(map.get(iterator.next()));
            }
            Collections.sort(list);
            for (
                    int i = 0; i < list.size(); i++) {
                out.write(list.get(i) + " ");
            }
            out.flush();
            //System.out.println(disjointSet.map);
        }

        private void bfsUtil(int v,int parent){
            Queue<Integer> queue = new ArrayDeque<>();
            queue.add(v);
            visited[v] = true;
            while (queue.size()>0 && unvisited.size()>0) {
                v = queue.poll();
                Set<Integer> check = new HashSet<>();
                while (unvisited.size() > 0) {
                    //System.out.println(unvisited);
                    int b = unvisited.poll();
                    if (!edge[v].contains(b)) {
                        visited[b] = true;
                        disjointSet.union(parent,b);
                        queue.add(b);
                    } else {
                        unvisited.add(b);
                        if(check.contains(b)){
                            break;
                        }
                        else{
                            check.add(b);
                        }
                    }
                }
            }
        }

        private void dfsUtil(int v, boolean[] visited, int parernt) {
            System.out.println(unvisited+" "+v);
            visited[v] = true;
            disjointSet.union(parernt, v);
            Set<Integer> check = new HashSet<>();
            while (unvisited.size()>0){
                int a = unvisited.poll();
                System.out.println(disjointSet.map+" "+a);

                if(!edge[v].contains(a)){
                    dad.put(a,v);
                    dfsUtil(a,visited,parernt);
                }
                else{
                    unvisited.add(a);
                    if(check.contains(a)){
                        System.out.println(unvisited+" "+v);
                        return;
                    }
                    else{
                        check.add(a);
                    }
                }
            }
            System.out.println(unvisited+" "+v);
        }
    }

    static BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {

        String[] s1 = inp.readLine().split(" ");
        int v = Integer.parseInt(s1[0]);
        int e = Integer.parseInt(s1[1]);
        Graph graph = new Graph(v);
        for(int i=0;i<e;i++){
            s1 = inp.readLine().split(" ");
            int a = Integer.parseInt(s1[0]);
            int b = Integer.parseInt(s1[1]);
            graph.addEdge(a,b);
        }
        graph.bfs();
    }
}
