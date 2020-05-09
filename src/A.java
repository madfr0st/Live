import java.io.*;
import java.util.*;


public class A {

    static long modulo = 1000000007;
    static BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    static int[] count;
    static int max = 0;

    public static void main(String[] args) throws IOException {

        int size = Integer.parseInt(inp.readLine());
        int[] given = new int[size];
        String[] s1 = inp.readLine().split(" ");

        int limit = (int) 1.5e7+1;

        int gcd = Integer.parseInt(s1[0]);

        for(int i=0;i<size;i++){
            given[i] = Integer.parseInt(s1[i]);
            gcd = gcd(gcd,given[i]);
        }


        int[] primes = new int[limit];

        for(int i=2;i<limit;i++){
            if(primes[i]==0){
                primes[i] = i;
                int multi = 2;
                while (multi*i<limit){
                    primes[multi*i] = i;
                    multi++;
                }
            }
        }

        count = new int[limit];

        for(int i=0;i<size;i++){
            given[i]/=gcd;
            fact(given[i],primes);
        }

//        print(count);
//        print(primes);
//        System.out.println(gcd);

        int ans = size-max;
        if(ans<size) {

            out.write(ans + "\n");
        }
        else{
            out.write(-1+"\n");
        }
        out.flush();


    }

    static void fact(int a,int[] primes){
        ArrayList<Integer> list = new ArrayList<>();
        Set<Integer> set = new HashSet<>();

       // System.out.println(a);
        while (a>1){
            int b = primes[a];
            a/=b;
            if(!set.contains(b)){
                set.add(b);
                count[b]++;
                max = Math.max(max,count[b]);
            }
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