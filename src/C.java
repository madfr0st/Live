import java.io.*;
import java.util.*;


public class C {

    public static void main(String[] args) throws IOException {
        BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

        int testCase = Integer.parseInt(inp.readLine());
        for(int t=0;t<testCase;t++){

            int size = Integer.parseInt(inp.readLine());
            int[] given = new int[size];
            String[] s1 = inp.readLine().split(" ");
            Map<Integer,Integer> map = new HashMap<>();
            int min = Integer.MAX_VALUE;
            for(int i=0;i<size;i++){
                given[i] = Integer.parseInt(s1[i]);
                if(map.containsKey(given[i])){
                    min = Math.min(min,i-map.get(given[i])+1);
                    map.put(given[i],i);
                }
                else{
                    map.put(given[i],i);
                }
            }

            if(min<Integer.MAX_VALUE) {
                out.write(min + "\n");
            }
            else {
                out.write(-1+"\n");
            }


        }
        out.flush();


    }
    static long[] decToBinary(long n,long k)
    {
        // array to store binary number
        long[] binaryNum = new long[64];

        // counter for binary array
        int i = 0;
        while (n > 0L) {
            // storing remainder in binary array
            binaryNum[i] = n % k;
            n = n / k;
            i++;
        }
        return binaryNum;
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
    static long calc(int a,int b){
        long c = b-a+1;
        c = c*(c+1);
        c/=2l;
        return c;
    }
}

