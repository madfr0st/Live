package cf;

import java.util.*;

class Test{
    static int ans = Integer.MAX_VALUE;
    public static void main(String[] args){

        System.out.println(179179175L * 179179175L + 179179175L * 10L + 26L);

    }

    static int[] count_kings(int[] royality,int[] road,int[] capacity,int[] querry){
        int[] ans = new int[querry.length];

        int size = road.length;

        Graph graph = new Graph(size);

        for(int i=0;i<size;i++){
            graph.addEdge(i+1,road[i]);
        }

        graph.init(royality,capacity);
        graph.DFS(querry);



        return graph.DFS(querry);
    }

    static class Graph{
        ArrayList<Integer>[] edges;
        int vertices;
        static Map<Integer,Integer> map = new HashMap<>();
        int[] ans;
        static PriorityQueue<Integer>[] priorityQueues;
        int[] c;
        Graph(int vertices){
            this.vertices = vertices;
            edges = new ArrayList[vertices+1];
            ans = new int[vertices+1];
            for(int i=0;i<=vertices;i++){
                edges[i] = new ArrayList();
            }
            priorityQueues = new PriorityQueue[vertices+1];
        }

        void init(int[] r,int[] c){
            for(int i=0;i<r.length;i++){
                priorityQueues[i+1].add(r[i]);
            }
            this.c = c;
        }


        void addEdge(int a,int b){
            edges[a].add(b);
            edges[b].add(a);
        }

        int[] DFS(int[] querry){
            boolean[] visited = new boolean[vertices+1];
            int i = 1;
            while (ans[1]<vertices){
                visited = new boolean[vertices+1];
                DFSutil(1,visited);
                map.put(i,ans[1]);
                i++;
            }

            int[] ret = new int[querry.length];

            for(int j=0;j<querry.length;j++){
                ret[j] = map.get(querry[i]);
            }

            return ret;
        }

        void DFSutil(int v,boolean[] visited){
            visited[v] = true;

            if(edges[v].size()==1){
                int b =  edges[v].get(0);

                int max=  c[v];

                int count = 0;
                int sum = 0;
                while (priorityQueues[v].size()>0 && sum+priorityQueues[v].peek()<=max){
                    int p =priorityQueues[v].poll();
                    priorityQueues[b].add(p);
                    sum+=p;
                    count++;
                }

                ans[b]+=count;

            }

            Iterator<Integer> iterator = edges[v].listIterator();
            while (iterator.hasNext()){
                int a = iterator.next();
                if(!visited[a]){



                    DFSutil(a,visited);

                    int max=  c[a];

                    int count = 0;
                    int sum = 0;
                    while (priorityQueues[a].size()>0 && sum+priorityQueues[a].peek()<=max){
                        int p =priorityQueues[a].poll();
                        priorityQueues[v].add(p);
                        sum+=p;
                        count++;
                    }

                    ans[v]+=count;


                }
            }
        }



    }

}