package codeChef;

import java.io.*;
import java.util.*;

public class IPCTRAIN {

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
            if(this.b.equals(o.b)){
                return -getU().compareTo(o.getU());
            }
            return -getV().compareTo(o.getV());
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

    public static void main(String[] args) throws IOException{
        int testCase = Integer.parseInt(inp.readLine());

        Comparator<Pair> revComp = new Comparator<Pair>() {
            @Override
            public int compare(Pair pair1, Pair pair2) {
                if(pair1.b.compareTo(pair2.b)>0) {
                    return -1;
                }

                    return 1;}
        };

        for(int t=0;t<testCase;t++){
            String[] s1 = inp.readLine().split(" ");
            int size = Integer.parseInt(s1[0]);
            int days =Integer.parseInt(s1[1]);
            ArrayList<Pair<Integer,Pair<Integer,Integer>>> given = new ArrayList<>();
            for(int i=0;i<size;i++){
                s1 = inp.readLine().split(" ");
                int a = Integer.parseInt(s1[0]);
                int b = Integer.parseInt(s1[1]);
                int c = Integer.parseInt(s1[2]);
                given.add(new Pair<>(a,new Pair<>(b,c)));
            }
            Collections.sort(given,revComp);
            System.out.println(given);

            PriorityQueue<Pair<Integer,Pair<Integer,Integer>>> priorityQueue = new PriorityQueue<>();

            int sum = 0;

            int j = 0;
            for(int i=0;i<days;i++){
                while (j<size){
                    if(given.get(j).a<=i){
                        priorityQueue.add(given.get(j));
                        j++;
                    }
                    else{
                        break;
                    }
                }

                System.out.println(priorityQueue +" "+i);
                if(priorityQueue.size()>0) {

                    Pair<Integer, Pair<Integer, Integer>> pair = priorityQueue.poll();
                    System.out.println(pair);
                    sum += pair.b.b;
                    if (pair.b.a - 1 > 0) {
                        pair = new Pair<>(pair.a, new Pair<>(pair.b.a - 1, pair.b.b));
                    }
                    priorityQueue.add(pair);
                }
            }

            System.out.println(sum);


            
        }
    }



}
