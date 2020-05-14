
import java.io.*;
import java.util.*;

public class B {
    static BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {

        int t = Integer.parseInt(inp.readLine());
        while (t-->0){
            String[]s1 = inp.readLine().split(" ");
            int size = Integer.parseInt(s1[0]);
            int k = Integer.parseInt(s1[1]);
            int[] given = new int[size];
            int[] given1 = new int[size];
            s1 = inp.readLine().split(" ");
            String[] s2 = inp.readLine().split(" ");

            int sum = 0;

            for(int i=0;i<size;i++){
                given[i] = Integer.parseInt(s1[i]);
                sum+=given[i] ;
                given1[i] = Integer.parseInt(s2[i]);
            }
            Arrays.sort(given);
            Arrays.sort(given1);

            int i =0;

            while (k-->0){
                int a = given[i];
                int b = given1[size-1-i];
                if(b-a>0){
                    sum+=b-a;
                }
                else {
                    break;
                }
                i++;
            }

            out.write(sum+"\n");


        }



        out.flush();
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