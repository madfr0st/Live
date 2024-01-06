package leetcode;



import kotlin.reflect.jvm.internal.impl.utils.DFS;

import javax.imageio.IIOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

public class LC133 {
    static BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
    public static void main(String[] args) throws IIOException{
        ArrayList<Node> neighbour = new ArrayList<>();
        neighbour.add(new Node(1));
        neighbour.add(new Node(2));
        neighbour.add(new Node(4));
    cloneGraph(new Node(4,neighbour));

    }
    public static Node cloneGraph(Node node) {

        Set<Integer> visited = new HashSet<>();



        return new Node();
    }

    public static void BFS(Node node,Set<Integer> set,Node cloneNode,Queue<Node> nodeQueue){
        set.add(node.val);
        List<Node> neighbour = node.neighbors;
        ArrayList<Node> cloneNeighbour = new ArrayList<>();
        for(int i=0;i<neighbour.size();i++){
            cloneNeighbour.add(new Node(neighbour.get(i).val));
            if(!set.contains(neighbour.get(i).val)){
                nodeQueue.add(neighbour.get(i));
            }
        }
    }
    public static class Node {
        public int val;
        public List<Node> neighbors;
        public Node() {
            val = 0;
            neighbors = new ArrayList<Node>();
        }
        public Node(int _val) {
            val = _val;
            neighbors = new ArrayList<Node>();
        }
        public Node(int _val, ArrayList<Node> _neighbors) {
            val = _val;
            neighbors = _neighbors;
        }
    }
}
