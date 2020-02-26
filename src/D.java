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

        void bfs(){
            Queue<Integer> queue = new ArrayDeque<>();
            Map<Integer,Integer> dist = new HashMap<>();
            Map<Integer,Integer> parent = new HashMap<>();
            boolean[] visited = new boolean[vertices+1];
            queue.add(1);
            visited[1] = true;
            parent.put(1,1);
            dist.put(1,0);
            int min = Integer.MAX_VALUE;
            ArrayList<Integer> list = new ArrayList<>();

            while (queue.size()>0){
                int v = queue.poll();
                Iterator<Integer> iterator = edge.get(v).listIterator();
                while (iterator.hasNext()){
                    int n = iterator.next();
                    if(!visited[n]){
                        queue.add(n);
                        visited[n] = true;
                        parent.put(n,v);
                        dist.put(n,dist.get(v)+1);
                    }
                }
            }

            list.add(vertices);
            int n = vertices;
            while (parent.get(n)!=1){
                list.add(parent.get(n));
                n = parent.get(n);
            }
            list.add(1);

            System.out.println(list);
            //System.out.println(dist);
            System.out.println(dist.get(vertices));

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