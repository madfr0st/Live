package practice;

import java.io.*;
import java.util.*;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.util.Arrays.fill;


public class A {

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


    static long modulo = 998244353;
    static BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));


    public static void main(String[] args) throws IOException {

        int[] check = new int[]{1,3,4,6,7,10};
        int j = Arrays.binarySearch(check,3,5,2);
        System.out.println(j);

    }

    public static long taxiDriver(List<Long> pickup, List<Long> drop, List<Long> tip) {
        ArrayList<Pair<Long,Pair<Long,Long>>> pairs = new ArrayList<>();
        for(int i=0;i<pickup.size();i++){
            pairs.add(new Pair<>(drop.get(i),new Pair<>(pickup.get(i),tip.get(i))));
        }
        Collections.sort(pairs);
        pickup.clear();
        drop.clear();
        tip.clear();
        drop.add(0l);
        for(int i=0;i<pairs.size();i++){
            pickup.add(pairs.get(i).b.a);
            drop.add(pairs.get(i).a);
            tip.add(pairs.get(i).b.b);
        }


        System.out.println(pairs);
        System.out.println(pickup);
        System.out.println(drop);
        System.out.println(tip);

        long ans = 0;
        int size = pickup.size();
        drop.add(0,0l);
        System.out.println(drop);
        long[] dp = new long[size+1];
        for(int i=0;i<size;i++){
            long a = pickup.get(i);
            int b = Collections.binarySearch(drop, a);
            System.out.println(b);
            if(b<0){
                b = -b-2;
                dp[i+1] = Math.max(dp[i], dp[b]+drop.get(i+1)-pickup.get(i)+tip.get(i));
            }
            else{
                dp[i+1] = Math.max(dp[i+1], dp[b]+drop.get(i+1)-pickup.get(i)+tip.get(i));
            }
            System.out.println(dp[i+1]+"-----"+drop.get(i+1)+""+pickup.get(i));

        }
        print(dp);
        return ans;
    }
    public static int solve(ArrayList<Integer> A){
        int size = A.size();
        int[][] w = new int[size][size];
        int[][] w2 = new int[size][size];
        int max = Integer.MIN_VALUE;
        for(int i=0;i<size;i++){
            for (int j=0;j<size;j++){
                int a = A.get(i);
                int q = a/(j+1);
                if(a%(j+1)>0){
                   q++;
                }
                w[i][j] = q;
                w2[i][j] = q;
                max = Math.max(q,max);
            }
        }
        for(int i=0;i<size;i++) {
            for (int j = 0; j < size; j++) {
                w[i][j]=max-w[i][j]+1;
            }
        }

        int[] ret = hungarianMethod(w);
        int ans = 0;
        for(int i=0;i<size;i++){
            ans+=w2[ret[i]][i];
        }
        return ans;
    }
    public final static int INF = (int) 1E9;
    static int[] hungarianMethod(int w[][]) {
        final int n = w.length, m = w[0].length, PHI = -1, NOL = -2;
        boolean[] x[] = new boolean[n][m], ss = new boolean[n], st = new boolean[m];
        int[] u = new int[n], v = new int[m], p = new int[m], ls = new int[n], lt = new int[m], a = new int[n];
        int f = 0;

        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                f = max(f, w[i][j]);

        fill(u, f);
        fill(p, INF);
        fill(lt, NOL);
        fill(ls, PHI);
        fill(a, -1);

        while (true) {
            f = -1;
            for (int i = 0; i < n && f == -1; i++)
                if (ls[i] != NOL && !ss[i])
                    f = i;

            if (f != -1) {
                ss[f] = true;
                for (int j = 0; j < m; j++)
                    if (!x[f][j] && u[f] + v[j] - w[f][j] < p[j]) {
                        lt[j] = f;
                        p[j] = u[f] + v[j] - w[f][j];
                    }
            } else {
                for (int i = 0; i < m && f == -1; i++)
                    if (lt[i] != NOL && !st[i] && p[i] == 0)
                        f = i;

                if (f == -1) {
                    int d1 = INF, d2 = INF, d;
                    for (int i : u)
                        d1 = min(d1, i);

                    for (int i : p)
                        if (i > 0)
                            d2 = min(d2, i);

                    d = min(d1, d2);

                    for (int i = 0; i < n; i++)
                        if (ls[i] != NOL)
                            u[i] -= d;

                    for (int i = 0; i < m; i++) {
                        if (p[i] == 0)
                            v[i] += d;
                        if (p[i] > 0 && lt[i] != NOL)
                            p[i] -= d;
                    }

                    if (d2 >= d1)
                        break;
                } else {
                    st[f] = true;
                    int s = -1;

                    for (int i = 0; i < n && s == -1; i++)
                        if (x[i][f])
                            s = i;

                    if (s == -1) {
                        for (int l, r; ; f = r) {
                            r = f;
                            l = lt[r];

                            if (r >= 0 && l >= 0)
                                x[l][r] = !x[l][r];
                            else
                                break;

                            r = ls[l];
                            if (r >= 0 && l >= 0)
                                x[l][r] = !x[l][r];
                            else
                                break;
                        }

                        fill(p, INF);
                        fill(lt, NOL);
                        fill(ls, NOL);
                        fill(ss, false);
                        fill(st, false);

                        for (int i = 0; i < n; i++) {
                            boolean ex = true;
                            for (int j = 0; j < m && ex; j++)
                                ex = !x[i][j];
                            if (ex)
                                ls[i] = PHI;
                        }
                    } else
                        ls[s] = f;
                }
            }
        }

        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                if (x[i][j])
                    a[j] = i;
        return a;
    }


    public int[] findRedundantDirectedConnection(int[][] edges) {
        Boolean[] visited = new Boolean[edges.length];

        return new int[]{1,2};
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