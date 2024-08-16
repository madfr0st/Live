package cf._965;

import java.io.*;
import java.util.Stack;
import java.util.StringTokenizer;


public class D {



    static BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
    static long mod = 1000000007;

    public static void main(String[] args) throws IOException {

        int t = Integer.parseInt(inp.readLine());
        while (t-->0){
            int size = Integer.parseInt(inp.readLine());
            String[] s1 = inp.readLine().split(" ");
            String[] s2 = inp.readLine().split("");
            Stack<Integer> stackL = new Stack<>();
            Stack<Integer> stackR = new Stack<>();

            long[] sumArray = new long[size];
            long count = 0;

            int[] given  = new int[size];

            for(int i=0;i<size;i++){

                given[i] = Integer.parseInt(s1[i]);

                count+=given[i];

                sumArray[i] = count;

                if(s2[i].equals("R")){
                    stackR.push(i);
                }
            }

            for(int i=size-1;i>=0;i--){
                if(s2[i].equals("L")){
                    stackL.push(i);
                }
            }

            long ans = 0;

            while (!stackL.isEmpty()){
                int l = stackL.pop();
                while (!stackR.isEmpty()){
                    int r = stackR.pop();
                    if(l<r){
                        ans+=sumArray[r]-sumArray[l]+given[l];
                        break;
                    }
                }
            }

            System.out.println(ans);




        }
    }

    public static class MyScanner {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer stringTokenizer;
        private String next() {
            while (stringTokenizer == null || !stringTokenizer.hasMoreElements()) {
                try {
                    stringTokenizer = new StringTokenizer(bufferedReader.readLine());
                }
                catch (IOException e) {
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
            try{
                str = bufferedReader.readLine();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }
        String[] nextLineAsStringArray(String separator) {
            String[] str = null;
            try{
                str = bufferedReader.readLine().split(separator);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }

        int[] nextLineAsIntArray(String separator) {
            String[] str = null;
            int[] arrayInt = null;
            try{
                str = bufferedReader.readLine().split(separator);
                arrayInt = new int[str.length];
                for(int i=0;i<str.length;i++){
                    arrayInt[i] = Integer.parseInt(str[i]);
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return arrayInt;
        }

        long[] nextLineAsLongArray(String separator) {
            String[] str = null;
            long[] arrayLong = null;
            try{
                str = bufferedReader.readLine().split(separator);
                arrayLong = new long[str.length];
                for(int i=0;i<str.length;i++){
                    arrayLong[i] = Long.parseLong(str[i]);
                }
            }
            catch (IOException e) {
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

}