
import java.util.*;

public class Solution {
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
        static void print(Pair[] pairs){
            for(int i=0;i<pairs.length;i++){
                System.out.print(pairs[i]+" ");
            }
            System.out.println();
        }
        static void print(Pair[][] pairs){

            for(int i=0;i<pairs.length;i++){
                for(int j=0;j<pairs[0].length;j++) {
                    System.out.print(pairs[i] + " ");
                }
                System.out.println();
            }
        }
    }




    int modulo = (int)1E9+7;

    public int[] pathsWithMaxScore(List<String> board) {

        int size = board.size();
        int[][] dp = new int[size][size];
        int[][] max_sum = new int[size][size];
        boolean[][] check = new boolean[size][size];
        check[size-1][size-1] = true;
        int count = 0;
        Queue<Pair<Integer,Integer>> queue = new ArrayDeque<>();

        for(int i=size-1;i>=0;i--){
            String[] string = board.get(i).split("");
            if(i!=0) {
                for (int j = size - 1; j >= 0; j--) {

                    String s1 = string[j];
                    if (s1.equals("X")) {
                        continue;
                    }
                    else if(s1.equals("S")){
                        check[i-1][j] = check[i][j]|check[i-1][j];
                        check[i][j-1] = check[i][j]|check[i][j-1];
                    }
                    else {
                        if (j == 0) {
                            max_sum[i - 1][j] = Math.max(max_sum[i][j] + Integer.parseInt(s1), max_sum[i - 1][j]);
                            check[i-1][j] = check[i][j]|check[i-1][j];
                        } else {
                            max_sum[i - 1][j] = Math.max(max_sum[i][j] + Integer.parseInt(s1), max_sum[i - 1][j]);
                            max_sum[i][j - 1] = Math.max(max_sum[i][j] + Integer.parseInt(s1), max_sum[i][j - 1]);
                            check[i-1][j] = check[i][j]|check[i-1][j];
                            check[i][j-1] = check[i][j]|check[i][j-1];
                        }
                    }

                }
            }
            else{
                for(int j=size-1;j>=1;j--){
                    String s1 = string[j];
                    if (s1.equals("X")) {
                        continue;
                    }
                    else {
                        max_sum[i][j - 1] = Math.max(max_sum[i][j] + Integer.parseInt(s1), max_sum[i][j - 1]);
                        check[i][j - 1] = check[i][j] | check[i][j - 1];
                    }
                }
            }
        }

        //print(max_sum);
        queue.add(new Pair<>(0,0));
        Set<Pair<Integer,Integer>> set = new HashSet<>();

        while (queue.size()!=0){
            Pair<Integer,Integer> pair = queue.poll();
            if(!set.contains(pair)){
                set.add(pair);
                if(pair.a+1<size && pair.b+1<size && max_sum[pair.a+1][pair.b]==max_sum[pair.a][pair.b+1]
                        && board.get(pair.a+1).charAt(pair.b)!=('X') && board.get(pair.a).charAt(pair.b+1)!=('X')){
                    queue.add(new Pair<>(pair.a+1,pair.b));
                    queue.add(new Pair<>(pair.a,pair.b+1));
                    count++;
                }
            }
        }

        int ans = 1;

        for(int i=0;i<count;i++){
            ans*=2;
            ans%=modulo;
        }

        //print(check);

        if(!check[0][0]){
            return new int[]{0,0};
        }

        return new int[]{max_sum[0][0],ans};

    }

    public static void main(String[] args){
        List<String> list = new ArrayList<>();
        list.add("E12");
        list.add("1X1");
        list.add("21S");

        Solution solution = new Solution();
        print(solution.pathsWithMaxScore(list));

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