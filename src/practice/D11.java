package practice;

import java.io.*;
import java.util.*;

public class D11 {
    static class Graph {
        int vertices;
        Set<Integer> used;
        ArrayList<Integer> edge[];
        Map<Integer, Integer> parent = new HashMap<>();
        ArrayList<Integer> list = new ArrayList<>();
        int cycle = 0;

        Graph(int vertices) {
            this.vertices = vertices;
            used = new HashSet<>();
            edge = new ArrayList[vertices + 1];
            for (int i = 0; i <= vertices; i++) {
                edge[i] = new ArrayList<>();
            }
        }

        void addEdge(int a, int b) {
            edge[a].add(b);
            edge[b].add(a);
        }

        void DFS() {
            boolean[] visited = new boolean[vertices + 1];
            for (int i = 1; i <= vertices; i++) {
                parent.clear();
                parent.put(i, 0);
                visited[i] = true;
                //list.add(i);
                DFSutil(i, visited, i);
//                list.remove(list.size()-1);
//                System.out.println();
                used.add(i);
            }
            System.out.println(cycle / 2);
        }

        void DFSutil(int v, boolean[] visited, int u) {
            //System.out.println(v);
            Iterator<Integer> iterator = edge[v].listIterator();
            while (iterator.hasNext()) {
                int n = iterator.next();
                if (!used.contains(n)) {
                    if (!visited[n]) {
                        //list.add(n);
                        parent.put(n, v);
                        visited[n] = true;
                        DFSutil(n, visited, u);
                        visited[n] = false;
                        parent.remove(n);
//                        list.remove(list.size()-1);
                    } else if (n == u && parent.get(v) != n) {

//                            list.add(n);
                        cycle++;
//                            System.out.println(list+" // "+parent.get(n));
//                            list.remove(list.size()-1);

                    }
                }
            }
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
        String[] s1 = inp.readLine().split(" ");
        int a = Integer.parseInt(s1[0]);
        int b = Integer.parseInt(s1[1]);
        Graph graph = new Graph(a);
        for(int i=0;i<b;i++){
            s1 = inp.readLine().split(" ");
            graph.addEdge(Integer.parseInt(s1[0]),Integer.parseInt(s1[1]));
        }
        graph.DFS();
    }

}
