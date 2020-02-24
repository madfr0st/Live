import java.io.*;
import java.util.*;


public class D {
    static class Graph{
        int vertices;
        ArrayList<ArrayList<Integer>> edge;
        Set<Integer> set;
        Graph(int vertices){
            this.vertices = vertices;
            set = new HashSet<>();
            edge = new ArrayList<>();
            for(int i=0;i<=vertices;i++){
                edge.add(new ArrayList<>());
            }
        }

        void addEdge(int a,int b){
            edge.get(a).add(b);
            edge.get(b).add(a);
        }

        void addSpecialEdge() throws IOException{
            String[] s1 = inp.readLine().split(" ");
            for(int i=0;i<s1.length;i++){
                set.add(Integer.parseInt(s1[i]));
            }
        }

        




    }
    static BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
    public static void main(String[] args) throws IOException {

        String[] s1 = inp.readLine().split(" ");
        int v = Integer.parseInt(s1[0]);
        int e = Integer.parseInt(s1[1]);
        int s = Integer.parseInt(s1[2]);

        Graph graph = new Graph(v);
        graph.addSpecialEdge();
        for(int i=0;i<e;i++){
            s1 = inp.readLine().split(" ");
            int a = Integer.parseInt(s1[0]);
            int b = Integer.parseInt(s1[1]);
            graph.addEdge(a,b);
        }
        graph.bfs();
    }
}