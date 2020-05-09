import java.io.*;
import java.util.*;


public class D {
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


    static BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {

        String[] s1 = inp.readLine().split(" ");
        int n = Integer.parseInt(s1[0]);
        int m = Integer.parseInt(s1[1]);
        int t = Integer.parseInt(s1[2]);

        long[][] dp = new long[n][n];
        Set<Integer>[][] set = new HashSet[n][n];
        Map<Pair<Integer,Integer>,Long> map = new HashMap<>();
        long[] given = new long[n];

        s1 = inp.readLine().split(" ");
        for(int i=0;i<n;i++){
            given[i] = Integer.parseInt(s1[i]);
        }
        for(int i=0;i<t;i++){
            s1 = inp.readLine().split(" ");
            int a = Integer.parseInt(s1[0]);
            int b = Integer.parseInt(s1[1]);
            int c = Integer.parseInt(s1[2]);
            map.put(new Pair<>(a-1,b-1),(long)c);
        }

        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                set[i][j] = new HashSet<>();
            }
        }

        for(int i=0;i<n;i++){
            dp[0][i] = given[i];
            set[0][i].add(i);
        }

        for(int i=0;i<n-1;i++){
            for(int j=0;j<n;j++){
                int max = 0;
                int at = -1;
                for(int k=0;k<n;k++){
                    if(!set[i][j].contains(k)){
                        long sum = given[k]+dp[i][j];
                        if(map.containsKey(new Pair<>(j,k))){
                            sum+=map.get(new Pair<>(j,k));
                        }

                        if(sum>dp[i+1][k]){
                            dp[i+1][k] = sum;
                            set[i+1][k] = new HashSet<>(set[i][j]);
                            set[i+1][k].add(k);
                        }
                    }
                }
//                print(dp);
//                System.out.println();
//                for(int ii=0;ii<n;ii++){
//                    for(int jj=0;jj<n;jj++){
//                        System.out.print(set[ii][jj]+" ");
//                    }
//                    System.out.println();
//                }
            }
        }

        long max = 0;
        for(int i=0;i<n;i++){
            max = Math.max(dp[m-1][i],max);
        }
        out.write(max+"\n");

        out.flush();



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