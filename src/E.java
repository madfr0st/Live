import java.io.*;
import java.util.*;


public class E {
    static BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {


        long n = Integer.parseInt(inp.readLine());
        long tenFact = 720*8*9*10;
        long nineFact = 720*8*9;

        long mod = 998244353;
        long side = 9;
        long center = 9*9*10;
        long len = n;

        ArrayList<Long> ans = new ArrayList<>();
        ans.add(10L);

        for(int i=2;i<=n;i++){

            int rest = i-2;

            long a = 2*side*10;
            a%=mod;
            side*=10;
            side%=mod;

            if(rest>0){
                a += center * rest;
                center *= 10;
                center %= mod;
                a %= mod;

            }
            ans.add(a);
        }

        for(int i=0;i<ans.size();i++){
            out.write(ans.get(ans.size()-1-i)+" ");
        }



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
