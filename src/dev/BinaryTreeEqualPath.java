package dev;

import java.io.IOException;
import java.util.*;

public class BinaryTreeEqualPath {

    public static long minsum(int n, List<Integer> weights) {
        if (n <= 1) return 0;

        List<Edge>[] graph = new ArrayList[n + 1];
        for (int i = 0; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 2; i <= n; i++) {
            int parent = i / 2;
            long w = weights.get(i - 2);
            graph[parent].add(new Edge(i, w));
            graph[i].add(new Edge(parent, w));
        }

        long[] subDist = new long[n + 1];
        long[] totalIncrements = new long[1];

        dfs(1, -1, graph, subDist, totalIncrements);

        return totalIncrements[0];
    }

    static class Edge {
        int to;
        long weight;

        Edge(int t, long w) {
            this.to = t;
            this.weight = w;
        }
    }

    private static long dfs(int u, int parent, List<Edge>[] graph, long[] subDist, long[] totalIncrements) {
        long maxDist = 0;
        List<Long> childDistances = new ArrayList<>();
        List<Integer> childIndices = new ArrayList<>();

        for (int i = 0; i < graph[u].size(); i++) {
            Edge edge = graph[u].get(i);
            if (edge.to == parent) continue;

            long distChild = dfs(edge.to, u, graph, subDist, totalIncrements) + edge.weight;
            childDistances.add(distChild);
            childIndices.add(i);
            if (distChild > maxDist) {
                maxDist = distChild;
            }
        }

        for (int c = 0; c < childDistances.size(); c++) {
            long distChild = childDistances.get(c);
            if (distChild < maxDist) {
                long need = maxDist - distChild;
                totalIncrements[0] += need;

                int edgeIndex = childIndices.get(c);
                graph[u].get(edgeIndex).weight += need;

                int childNode = graph[u].get(edgeIndex).to;
                for (Edge rev : graph[childNode]) {
                    if (rev.to == u) {
                        rev.weight += need;
                        break;
                    }
                }
            }
        }

        subDist[u] = maxDist;
        return maxDist;
    }

    public static void main(String[] args) {
        // Test case 1: n=3, edges=[10,5] => expected 5
        List<Integer> edges1 = List.of(10, 5);
        System.out.println("n=3 => " + minsum(3, edges1));

        // Test case 2: n=7, edges=[3,1,2,1,5,4] => expected 3
        List<Integer> edges2 = List.of(3, 1, 2, 1, 5, 4);
        System.out.println("n=7 => " + minsum(7, edges2));

        // Test case 3: n=9, edges=[1,1,1,1,1,1,1,20] => expected 59
        List<Integer> edges3 = List.of(1, 1, 1, 1, 1, 1, 1, 20);
        System.out.println("n=9 => " + minsum(9, edges3));

        // Additional test case: n=2, edges=[5] => expected 0 (only one leaf)
        List<Integer> edges4 = List.of(5);
        System.out.println("n=2 => " + minsum(2, edges4));

        // Additional test case: n=5, edges=[1,2,3,4] => expected 2
        List<Integer> edges5 = List.of(1, 2, 3, 4);
        System.out.println("n=5 => " + minsum(5, edges5));
    }
}