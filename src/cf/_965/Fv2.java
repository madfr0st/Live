package cf._965;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;


public class Fv2 {


    static BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
    static long mod = 1000000007;

    public static void main(String[] args) throws IOException {

        int t = Integer.parseInt(inp.readLine());

        while (t-- > 0) {
            String[] s1 = inp.readLine().split(" ");
            int rect = Integer.parseInt(s1[0]);
            int point = Integer.parseInt(s1[1]);

            ArrayList<Pair<Integer, Integer>> pairs = new ArrayList<>();

            for (int i = 0; i < rect; i++) {
                s1 = inp.readLine().split(" ");
                int a = Integer.parseInt(s1[0]);
                int b = Integer.parseInt(s1[1]);
                if (a > b) {
                    pairs.add(new Pair<>(b, a));
                } else {
                    pairs.add(new Pair<>(a, b));
                }
            }

            Collections.sort(pairs);

            int count = 0;
            int cost = 0;


            int[][] dp1 = new int[rect][100];

            System.out.println(pairs);
            for (int i = 0; i < rect; i++) {
                int[] temp = rectCost(pairs.get(i).b,pairs.get(i).a);
                for(int j=0;j<pairs.get(i).b+pairs.get(i).a;j++){
                    dp1[i][j+1] = temp[j+1];
                }
            }
            print(dp1);

         //   int[] dp2 = new int[rect][100];

            for(int i=0;i<rect;i++){
                
            }



            if (count < point) {
                System.out.println(-1);
            } else {
                System.out.println(cost);
            }
        }

    }

    static int[] rectCost(int l, int w) {
        int[] cost = new int[l + w + 1];
        int size = l + w ;
        if (l == 1 && w == 1) {
            return new int[]{0, 1, 1};
        }
        for (int i = 0; i < size; i++) {
            if (l > w) {
                cost[i + 1] += cost[i] + w;
                l--;

            } else if (w >= l) {
                cost[i + 1] += cost[i] + l;
                w--;
            }
        }
        return cost;
    }

    static void solve(int l, int w, int count, int required) {

    }

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

    static void print(boolean[] array) {
        for (int j = 0; j < array.length; j++) {
            System.out.print(array[j] + " ");
        }
        System.out.println();
    }

    static void print(boolean[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }
    }

    static void print(long[] array) {
        for (int j = 0; j < array.length; j++) {
            System.out.print(array[j] + " ");
        }
        System.out.println();
    }

    static void print(long[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }
    }

    static void print(String[] array) {
        for (int j = 0; j < array.length; j++) {
            System.out.print(array[j] + " ");
        }
        System.out.println();
    }

    static void print(String[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static class MyScanner {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer stringTokenizer;

        private String next() {
            while (stringTokenizer == null || !stringTokenizer.hasMoreElements()) {
                try {
                    stringTokenizer = new StringTokenizer(bufferedReader.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return stringTokenizer.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }

        double nextDouble() {
            return Double.parseDouble(next());
        }

        String nextLine() {
            String str = "";
            try {
                str = bufferedReader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }

        String[] nextLineAsStringArray(String separator) {
            String[] str = null;
            try {
                str = bufferedReader.readLine().split(separator);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }

        int[] nextLineAsIntArray(String separator) {
            String[] str = null;
            int[] arrayInt = null;
            try {
                str = bufferedReader.readLine().split(separator);
                arrayInt = new int[str.length];
                for (int i = 0; i < str.length; i++) {
                    arrayInt[i] = Integer.parseInt(str[i]);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return arrayInt;
        }

        long[] nextLineAsLongArray(String separator) {
            String[] str = null;
            long[] arrayLong = null;
            try {
                str = bufferedReader.readLine().split(separator);
                arrayLong = new long[str.length];
                for (int i = 0; i < str.length; i++) {
                    arrayLong[i] = Long.parseLong(str[i]);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return arrayLong;
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