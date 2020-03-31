import processing.core.PGraphics;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class Main {

    static class Graph{
        int vertices;
        ArrayList[] edge;
        Graph(int vertices){
            this.vertices = vertices;
            edge = new ArrayList[vertices+1];
            for(int i=0;i<=vertices;i++){
                edge[i] = new ArrayList<Integer>();
            }
        }
        void addEdge(int a,int b){
            edge[a].add(b);
            edge[b].add(a);
        }
    }

    static BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        Graph graph = new Graph(6);
    }

}
