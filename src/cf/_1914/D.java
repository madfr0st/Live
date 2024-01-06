package cf._1914;



import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;


public class D {

    static long modulo = 998244353l;
    static BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
    public static void main(String[] args) throws IOException {


       MyScanner myScanner = new MyScanner();
        int t = myScanner.nextInt();
        while (t-->0) {
            int size = myScanner.nextInt();
            long[] a1 = myScanner.nextLineAsLongArray(" ");
            long[] a2 = myScanner.nextLineAsLongArray(" ");
            long[] a3 = myScanner.nextLineAsLongArray(" ");

            Arrays.sort(a1);
            Arrays.sort(a2);
            Arrays.sort(a3);

            long max = 0;
            max = Math.max(max, a1[size - 1] + a2[size - 2] + a3[size - 3]);
            max = Math.max(max, a1[size - 1] + a3[size - 2] + a2[size - 3]);
            max = Math.max(max, a2[size - 1] + a1[size - 2] + a3[size - 3]);
            max = Math.max(max, a2[size - 1] + a3[size - 2] + a2[size - 3]);
            max = Math.max(max, a3[size - 1] + a1[size - 2] + a1[size - 3]);
            max = Math.max(max, a3[size - 1] + a2[size - 2] + a1[size - 3]);

            out.write(max + "\n");


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
            implements Comparable<Pair<U,V>>{

        public U a;
        public V b;

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




    static int gcd(int a, int b)
    {
        if (b == 0)
            return a;
        return gcd(b, a % b);
    }
    static void print(int[] array){
        for(int j=0;j<array.length;j++){
            System.out.print(array[j]+" ");
        }
        System.out.println();
    }
    static void print(int[][] array){
        for(int i=0;i< array.length;i++) {
            for (int j = 0; j < array[0].length; j++) {
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }
    }
    static void print(boolean[] array){
        for(int j=0;j<array.length;j++){
            System.out.print(array[j]+" ");
        }
        System.out.println();
    }
    static void print(boolean[][] array){
        for(int i=0;i< array.length;i++) {
            for (int j = 0; j < array[0].length; j++) {
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }
    }
    static void print(long[] array){
        for(int j=0;j<array.length;j++){
            System.out.print(array[j]+" ");
        }
        System.out.println();
    }
    static void print(long[][] array){
        for(int i=0;i< array.length;i++) {
            for (int j = 0; j < array[0].length; j++) {
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }
    }
    static void print(String[] array){
        for(int j=0;j<array.length;j++){
            System.out.print(array[j]+" ");
        }
        System.out.println();
    }
    static void print(String[][] array){
        for(int i=0;i< array.length;i++) {
            for (int j = 0; j < array[0].length; j++) {
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }
    }
}