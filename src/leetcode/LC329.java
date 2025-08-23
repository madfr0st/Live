package leetcode;

import javax.imageio.IIOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

public class LC329 {
    static BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
    public static void main(String[] args) throws IIOException {

        int[][] matrix = {
                { 7 },
                { 2 },
                { 8 }
        };

        System.out.println(longestIncreasingPath(matrix));




    }

    public static int longestIncreasingPath(int[][] matrix) {

//        Set<Integer> set = new HashSet<>();
//        ArrayList<Integer> list = new ArrayList<>();
//        for(int i=0;i<matrix.length;i++){
//            for(int j=0;j<matrix[0].length;j++) {
//                if(!set.contains(matrix[i][j])){
//                    set.add(matrix[i][j]);
//                    list.add(matrix[i][j]);
//                }
//            }
//        }

//        Collections.sort(list);
//        Map<Integer,Integer> map = new HashMap<>();
//        for(int i=0;i<list.size();i++){
//            map.put(list.get(i),i+1);
//        }

//        for(int i=0;i<matrix.length;i++){
//            for(int j=0;j<matrix[0].length;j++) {
//                matrix[i][j] = map.get(matrix[i][j]);
//                System.out.println(matrix[i][j]);
//            }
//        }

        Graph graph = new Graph(matrix.length*matrix[0].length+1);




        for(int i=0;i<matrix.length;i++){
            for(int j=0;j<matrix[0].length;j++){
                System.out.println(matrix[0].length*i + j+1);
                if(i-1>=0){
                    graph.addEdge(matrix[i][j],matrix[i-1][j], (matrix[0].length*i + j+1), (matrix[0].length*(i-1) + j+1));
                }
                if(j-1>=0){
                    graph.addEdge(matrix[i][j],matrix[i][j-1], (matrix[0].length*i + j+1), (matrix[0].length*(i) + j));
                }
                if(i+1<matrix.length){
                    graph.addEdge(matrix[i][j],matrix[i+1][j], (matrix[0].length*i + j+1), (matrix[0].length*(i+1) + j+1));
                }
                if(j+1<matrix[0].length){
                    graph.addEdge(matrix[i][j],matrix[i][j+1], (matrix[0].length*i + j+1), (matrix[0].length*(i) + j+2));
                }
            }
        }
        graph.printGraph();
        return graph.topological_sort();
    }

    public static class Graph{
        int v;
        ArrayList<Integer>[] vertices;
        Graph(int v) {
            this.v = v;
            vertices = new ArrayList[v + 1];
            for (int i = 0; i < v + 1; i++) {
                vertices[i] = new ArrayList<>();
            }
        }

        void addEdge(int a, int b, int p, int q){
            if(a<b){
                vertices[p].add(q);
            }
            if (a>b){
                vertices[q].add(p);
            }
        }

        void printGraph(){
            for (int i=0;i<v;i++){
                System.out.println(vertices[i+1]);
            }
        }

        int topological_sort(){
            Stack<Integer> stack = new Stack<>();
            boolean[] visited = new boolean[v+1];
            for (int i = 1; i < v+1; i++) {
                if (!visited[i]) {
                    dfs(i, visited, stack);
                }
            }

            visited = new boolean[v+1];
            int[] dp = new int[v+1];
            Arrays.fill(dp,1);

            while (!stack.isEmpty()) {
                int l = stack.pop();
                dfs_DP(l,visited,dp);
            }

            int max = 1;
            for(int i=0;i<dp.length;i++){
                max = Math.max(max,dp[i]);
            }
            return max;


        }



        private void dfs_DP(int u, boolean[] visited, int[] dp) {
            visited[u] = true;
            Iterator<Integer> iterator = vertices[u].iterator();
            while (iterator.hasNext()) {
                int nbr = iterator.next();
//                if (!visited[nbr]) {
                    dp[nbr] = Math.max(dp[u]+1,dp[nbr]);
//                    dfs_DP(nbr, visited, dp);
//                }
            }
        }

        private void dfs(int u, boolean[] visited, Stack<Integer> stack) {
            visited[u] = true;
            Iterator<Integer> iterator = vertices[u].iterator();
            while (iterator.hasNext()) {
                int nbr = iterator.next();
                if (!visited[nbr]) {
                    dfs(nbr, visited, stack);
                }
            }

            System.out.println(stack);

            stack.push(u);
        }




    }
}
