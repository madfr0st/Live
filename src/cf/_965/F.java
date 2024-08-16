package cf._965;

import java.io.*;
import java.util.*;


public class F {


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

            for (int i = 0; i < rect; i++) {
                int maxp = pairs.get(i).a + pairs.get(i).b;
                if (count + maxp == point) {
                    cost += pairs.get(i).a * pairs.get(i).b;
                    break;
                } else if (count + maxp > point) {
                    //System.out.println(cost+" "+count);
                    int w = pairs.get(i).a;
                    int l = pairs.get(i).b;

                    if(count+l-w<point){
                        count+=l-w;
                        cost += (l-w)*w;

                        for(int j=0;j<w;j++){
                            if(count+2>point){
                                count+=1;
                                cost+=(w-j);
                                break;
                            }
                            else {
                                count+=2;
                                cost+=(w-j)*2-1;
                            }
                        }

                        if(count==point){
                            break;
                        }


                    }
                    else {
                        cost += (point-count)*w;
                    }


                } else {
                    count += maxp;
                    cost += pairs.get(i).a * pairs.get(i).b;
                }
            }

            if(count!=point){
                System.out.println(-1);
            }
            else {
                System.out.println(cost);
            }
        }

    }

    static void solve(int l, int w, int count, int required){

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