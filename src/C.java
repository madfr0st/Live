import java.io.*;
import java.util.*;


public class C {
    static BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {


        int t = Integer.parseInt(inp.readLine());
        while (t-->0){
            int n =Integer.parseInt(inp.readLine());
            Set<Integer> set = new HashSet<>();
            String[] s1 = inp.readLine().split(" ");
            int[] given = new int[n];
            for(int i=0;i<n;i++){
                given[i] = i+Integer.parseInt(s1[i]);
            }
            //print(given);
            boolean ans = true;
            for(int i=0;i<n;i++){
                int rem = given[i]%n;
                if(rem<0){
                    rem+=n;
                }

                if(set.contains(rem)){
                    ans = false;
                }
                else {
                    set.add(rem);
                }

            }
            if(ans) {
                out.write("YES" + "\n");
            }
            else{
                out.write("NO"+"\n");
            }

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

