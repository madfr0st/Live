package cf._965;

import java.io.*;
import java.util.*;


public class C {



    static BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
    static long mod = 1000000007;

    public static void main(String[] args) throws IOException {

        int t = Integer.parseInt(inp.readLine());
        while (t-->0){
            int size = Integer.parseInt(inp.readLine());
            int[] givenArray = new int[size];
            String[] s1 = inp.readLine().split(" ");
            for(int i=0;i<size;i++){
                givenArray[i] = Integer.parseInt(s1[i]);
            }

            int k = Integer.parseInt(inp.readLine());
            while (k-->0){
                s1 = inp.readLine().split("");
                if(s1.length!=size){
                    System.out.println("NO");
                }
                else{
                    Map<String,Integer> map = new HashMap<>();
                    Set<Integer> set = new HashSet<>();
                    boolean ans = true;
                    int[] test = new int[size];
                    for(int i=0;i<size;i++){
                        if(map.containsKey(s1[i])){
                            if(givenArray[i]==map.get(s1[i])) {
                                test[i] = map.get(s1[i]);
                            }else{
                                ans = false;
                                break;
                            }
                        }
                        else{
                            if(!set.contains(givenArray[i])){
                                set.add(givenArray[i]);
                                map.put(s1[i],givenArray[i]);
                                test[i] = givenArray[i];
                            }
                            else {
                                ans = false;
                                break;
                            }
                        }
                    }

                    for(int i=0;i<size;i++){
                        if(givenArray[i]!=test[i]){
                            ans = false;
                        }
                    }

                    if(ans){
                        System.out.println("YES");
                    }
                    else{
                        System.out.println("NO");
                    }

                }

            }

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