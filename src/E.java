import java.io.*;
import java.sql.Array;
import java.util.*;


public class E {
    static BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {

        int t = Integer.parseInt(inp.readLine());
        while (t-->0){
            String[] s1 = inp.readLine().split(" ");
            int size = Integer.parseInt(s1[0]);
            int k = Integer.parseInt(s1[1]);

            String[] strings = inp.readLine().split("");
            int[]dp = new int[size];

            int[] sum = new int[size];
            int a = 0;
            for(int i=size-1;i>=0;i--){
                if(strings[i].equals("1")){
                    a++;
                }
                sum[i] = a;
            }
            int min = (int)1e10;
            for(int i=0;i<k;i++){
                int b = 0;
                int c = 0;
                for(int j=i;j<size;j+=k){
                    c++;
                    if(strings[j].equals("1")){
                        b++;
                    }
                }
                //System.out.println(b+" "+c);
                int ans = c-b+(a-b);
                min = Math.min(ans,min);
                //System.out.println(ans+" '");
            }
            System.out.println(min);

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
