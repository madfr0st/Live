package timestamp;

import practice.G;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class check1 {
    static class Graph{
        int v;
        int ans = 0;

        ArrayList<Integer>[] list;
        Graph(int v){
            this.v = v;
            list = new ArrayList[v+1];
            for(int i=0;i<=v;i++){
                list[i] = new ArrayList<>();
            }
        }

        void addNode(int a,int b){
            list[a].add(b);
            list[b].add(a);
        }

        void DFS(int a,int b){
            boolean[] visited = new boolean[v+1];
            DFSutil(a,b,visited);
        }

        void DFSutil(int a,int z,boolean[] visited){

            if(visited[a]==false) {
                visited[a] = true;
                Iterator<Integer> iterator = list[a].iterator();
                while (iterator.hasNext()) {
                    int b = iterator.next();
                    System.out.println(b+" =");
                    if (b == z) {
                        ans = 1;
                    }
                    if (!visited[b]) {
                        DFSutil(b, z, visited);
                    }
                }
            }
        }

    }
    public static void main(String[] args) throws IOException {
       Graph graph = new Graph(5);
        BufferedReader inp  = new BufferedReader(new InputStreamReader(System.in));
        Map<Integer,Integer> map = new HashMap<>();

        for(int i=0;i<5;i++){
            map.put(Integer.parseInt(inp.readLine()),(i+1));
        }
    System.out.println(map);
        for(int i=0;i<4;i++){
            String[] aa = inp.readLine().split(" ");
            int a = Integer.parseInt(aa[0]);
            int b = Integer.parseInt(aa[1]);
            graph.addNode(map.get(a),map.get(b));


        }

        graph.DFS(3,4);
        System.out.println(graph.ans);

    }
}



