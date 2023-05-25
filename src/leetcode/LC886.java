package leetcode;



import javax.imageio.IIOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class LC886 {
    static BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
    public static void main(String[] args) throws IIOException{

    }

    public static class Graph{
        int V;
        ArrayList[] arrayLists;
        Graph(int V) {
            arrayLists = new ArrayList[V];
            for (int i = 0; i <= V; i++) {
                arrayLists[i] = new ArrayList<Integer>();
            }
        }

        void addNode(int a,int b){
            arrayLists[a].add(b);
            arrayLists[b].add(a);
        }

        void BFS(){

        }

    }
}
