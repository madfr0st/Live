package dev;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EqualRootToLeafDistances {

    // We'll store edges in an adjacency list
    // Each entry in graph[u] is an Edge object: (to, weight)
    static class Edge {
        int to;
        long weight;  // use long to avoid overflow if increments get large

        Edge(int t, long w) {
            this.to = t;
            this.weight = w;
        }
    }

    // Global adjacency for the tree
    static List<Edge>[] graph;
    // We'll keep track of subtree distance for each node: subDist[u] = max distance from u down to any leaf in its subtree
    static long[] subDist;
    // We'll accumulate the total increments here
    static long totalIncrements = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Read number of nodes
        int n = sc.nextInt();

        // Initialize adjacency list
        graph = new ArrayList[n+1];
        for (int i = 0; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }
        subDist = new long[n+1];

        // Read edges: for each edge, we have (u, v, weight)
        for (int i = 0; i < n - 1; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            long w = sc.nextLong();

            // store bidirectionally
            graph[u].add(new Edge(v, w));
            graph[v].add(new Edge(u, w));
        }

        // We'll do DFS from node 1 (assuming 1 is our chosen root)
        dfs(1, -1);

        // totalIncrements now holds the minimal sum of increments required
        System.out.println(totalIncrements);

        sc.close();
    }

    // Returns the max distance from u down to any leaf in u's subtree.
    // parent = the node we came from so we don't go backwards in the tree.
    private static long dfs(int u, int parent) {
        // First, gather child-distances
        long maxDist = 0;

        // We'll store (index in adjacency list, distance from u to child's farthest leaf)
        List<Long> childDistances = new ArrayList<>();

        for (int i = 0; i < graph[u].size(); i++) {
            Edge edge = graph[u].get(i);
            int v = edge.to;
            if (v == parent) continue;

            long distChild = dfs(v, u) + edge.weight;
            childDistances.add(distChild);
            // We track the maximum
            if (distChild > maxDist) {
                maxDist = distChild;
            }
        }

        // Now we know the maximum distance among all children: maxDist
        // We need to increment edges so that each child's distance becomes maxDist
        int childIndex = 0;  // to track which child distance belongs to which adjacency entry
        for (int i = 0; i < graph[u].size(); i++) {
            Edge edge = graph[u].get(i);
            int v = edge.to;
            if (v == parent) continue;

            long distChild = childDistances.get(childIndex++);
            if (distChild < maxDist) {
                long needed = maxDist - distChild;  // how much to increment edge (u->v)
                totalIncrements += needed;
                edge.weight += needed;  // increment in the adjacency from u->v

                // also update the reverse edge from v->u
                for (Edge rev : graph[v]) {
                    if (rev.to == u) {
                        rev.weight += needed;
                        break;
                    }
                }
            }
        }

        // subDist[u] is now the distance from u to the farthest leaf in its subtree
        subDist[u] = maxDist;
        return maxDist;
    }
}