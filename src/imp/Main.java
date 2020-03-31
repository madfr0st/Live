package imp;

import java.io.*;
import java.util.*;

public class Main {

    static class Pair<U extends Comparable<U>, V extends Comparable<V>>
            implements Comparable<Pair<U,V>>{

        public final U a;
        public final V b;

        private Pair(U a, V b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;

            Pair<?, ?> pair = (Pair<?, ?>) o;
            if (!a.equals(pair.a))
                return false;
            return b.equals(pair.b);
        }

        @Override
        public int hashCode() {
            return 31 * a.hashCode() + b.hashCode();
        }

        @Override
        public String toString() {
            return "(" + a + ", " + b + ")";
        }

        @Override
        public int compareTo(Pair<U, V> o) {
            if(this.a.equals(o.a)){
                return getV().compareTo(o.getV());
            }
            return getU().compareTo(o.getU());
        }
        private U getU() {
            return a;
        }
        private V getV() {
            return b;
        }
    }

    static class DisjointSet {

        private Map<Long, Node> map = new HashMap<>();

        class Node {
            long data;
            Node parent;
            int rank;
        }
        public void makeSet(long data) {
            Node node = new Node();
            node.data = data;
            node.parent = node;
            node.rank = 0;
            map.put(data, node);
        }
        public boolean union(long data1, long data2) {
            Node node1 = map.get(data1);
            Node node2 = map.get(data2);

            Node parent1 = findSet(node1);
            Node parent2 = findSet(node2);

            if (parent1.data == parent2.data) {
                return false;
            }

            if (parent1.rank >= parent2.rank) {
                //increment rank only if both sets have same rank
                parent1.rank = (parent1.rank == parent2.rank) ? parent1.rank + 1 : parent1.rank;
                parent2.parent = parent1;
            } else {
                parent1.parent = parent2;
            }
            return true;
        }

        public long findSet(long data) {
            return findSet(map.get(data)).data;
        }

        private Node findSet(Node node) {
            Node parent = node.parent;
            if (parent == node) {
                return parent;
            }
            node.parent = findSet(node.parent);
            return node.parent;
        }
    }

    static class segmentTree {

        public int[] createTree(int input[], Operation operation) {
            int height = (int) Math.ceil(Math.log(input.length) / Math.log(2));
            int segmentTree[] = new int[(int) (Math.pow(2, height + 1) - 1)];
            constructTree(segmentTree, input, 0, input.length - 1, 0, operation);
            return segmentTree;
        }

        private void constructTree(int segmentTree[], int input[], int low, int high, int pos, Operation operation) {
            if (low == high) {
                segmentTree[pos] = input[low];
                return;
            }
            int mid = (low + high) / 2;
            constructTree(segmentTree, input, low, mid, 2 * pos + 1, operation);
            constructTree(segmentTree, input, mid + 1, high, 2 * pos + 2, operation);
            segmentTree[pos] = operation.perform(segmentTree[2 * pos + 1], segmentTree[2 * pos + 2]);
        }

        public int rangeQuery(int[] segmentTree, int qlow, int qhigh, int len, Operation operation) {
            return rangeQuery(segmentTree, 0, len - 1, qlow, qhigh, 0, operation);
        }

        private int rangeQuery(int segmentTree[], int low, int high, int qlow, int qhigh, int pos, Operation operation) {
            if (qlow <= low && qhigh >= high) {
                return segmentTree[pos];
            }
            if (qlow > high || qhigh < low) {
                return 0;
            }
            int mid = (low + high) / 2;
            return operation.perform(rangeQuery(segmentTree, low, mid, qlow, qhigh, 2 * pos + 1, operation),
                    rangeQuery(segmentTree, mid + 1, high, qlow, qhigh, 2 * pos + 2, operation));
        }

        public void updateValueForSumOperation(int input[], int segmentTree[], int newVal, int index) {
            int diff = newVal - input[index];
            input[index] = newVal;
            updateVal(segmentTree, 0, input.length - 1, diff, index, 0);
        }

        private void updateVal(int segmentTree[], int low, int high, int diff, int index, int pos) {
            if (index < low || index > high) {
                return;
            }
            segmentTree[pos] += diff;
            if (low >= high) {
                return;
            }
            int mid = (low + high) / 2;
            updateVal(segmentTree, low, mid, diff, index, 2 * pos + 1);
            updateVal(segmentTree, mid + 1, high, diff, index, 2 * pos + 2);
        }
    }

    //-------------------------------------------------------------------------------------------------------------------


    static BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {

    }
}
