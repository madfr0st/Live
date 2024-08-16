package leetcode;

import javax.imageio.IIOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class LC980 {
    static BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
    static Set<Pair<Integer, Integer>> visited = new HashSet<>();
    static Set<Pair<Integer, Integer>> blocked = new HashSet<>();
    static Pair<Integer, Integer> startPoint = new Pair<>(-1, -1);
    static Pair<Integer, Integer> endPoint = new Pair<>(-1, -1);

    static ArrayList<String> list = new ArrayList<>();

    static int ans = 0;

    static void print(int[] array) {
        for (int j = 0; j < array.length; j++) {
            System.out.print(array[j] + " ");
        }
        System.out.println();
    }

    static void print(int[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) throws IIOException {

        int[][] test = new int[][]{{1, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 2}};
        // test = new int[][]{{0, 1}, {2, 0}};
        uniquePathsIII(test);
        System.out.println(ans);


    }

    public static int uniquePathsIII(int[][] grid) {

        int l = grid.length;
        int w = grid[0].length;
        for (int i = 0; i < l; i++) {
            for (int j = 0; j < w; j++) {
                if (grid[i][j] == 1) {
                    startPoint = new Pair<>(i, j);
                } else if (grid[i][j] == 2) {
                    endPoint = new Pair<>(i, j);
                } else if (grid[i][j] == -1) {
                    blocked.add(new Pair<>(i, j));
                }
            }
        }

        visited.add(startPoint);
        util(l, w, startPoint.a, startPoint.b);

        return ans;
    }

    public static void util(int l, int w, int i, int j) {

        //end point
        if (new Pair<>(i, j).equals(endPoint)) {
            System.out.println(list);
            visited.remove(new Pair<>(i, j));
            System.out.println(visited);
            System.out.println(blocked);
            if (visited.add(new Pair<>(i, j)) && visited.size() == l * w - blocked.size()) {
                System.out.println(i + " " + j);
                ans++;
            }

            return;
        }

        //left
        if (i - 1 >= 0 && !visited.contains(new Pair<>(i - 1, j)) && !blocked.contains(new Pair<>(i - 1, j))) {
            list.add("left");
            visited.add(new Pair<>(i - 1, j));
            util(l, w, i - 1, j);
            visited.remove(new Pair<>(i - 1, j));
            list.remove(list.size() - 1);

        }

        //top
        if (j - 1 >= 0 && !visited.contains(new Pair<>(i, j - 1)) && !blocked.contains(new Pair<>(i, j - 1))) {

            list.add("up");
            visited.add(new Pair<>(i, j - 1));
            util(l, w, i, j - 1);
            visited.remove(new Pair<>(i, j - 1));
            list.remove(list.size() - 1);
        }

        //right
        if (i + 1 < l && !visited.contains(new Pair<>(i + 1, j)) && !blocked.contains(new Pair<>(i + 1, j))) {
            list.add("right");
            visited.add(new Pair<>(i + 1, j));
            util(l, w, i + 1, j);
            visited.remove(new Pair<>(i + 1, j));
            list.remove(list.size() - 1);
        }


        //bottom

        if (j + 1 < w && !visited.contains(new Pair<>(i, j + 1)) && !blocked.contains(new Pair<>(i, j + 1))) {
            list.add("down");
            visited.add(new Pair<>(i, j + 1));
            util(l, w, i, j + 1);
            visited.remove(new Pair<>(i, j + 1));
            list.remove(list.size() - 1);
        }
    }

    static class Pair<U extends Comparable<U>, V extends Comparable<V>>
            implements Comparable<Pair<U, V>> {

        public final U a;
        public final V b;

        private Pair(U a, V b) {
            this.a = a;
            this.b = b;
        }

        static void print(Pair[] pairs) {
            for (int i = 0; i < pairs.length; i++) {
                System.out.print(pairs[i] + " ");
            }
            System.out.println();
        }

        static void print(Pair[][] pairs) {

            for (int i = 0; i < pairs.length; i++) {
                for (int j = 0; j < pairs[0].length; j++) {
                    System.out.print(pairs[i] + " ");
                }
                System.out.println();
            }
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
            if (this.a.equals(o.a)) {
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


}
