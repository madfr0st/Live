import java.io.*;
import java.util.*;


public class A {
    static int modulo = 1000000007;
    public static void main(String[] args) throws IOException {
        BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

        int size = Integer.parseInt(inp.readLine());
        int[] given = new int[size];
        for(int i=0;i<size;i++){
            given[i] = Integer.parseInt(inp.readLine());
        }

        Arrays.sort(given);

        boolean[] visited = new boolean[size];

        int a = given[size-1]/2;
        int index = Arrays.binarySearch(given,a);

        index = size/2-1;

        int ans = 0;
        //print(given);
        for(int i=size-1;i>=0;i--){
            if(visited[i]){
                continue;
            }
            while (index>=0 && given[index]>given[i]/2 ){
                index--;
            }
            if(index>=0 && given[index]<=given[i]/2 ){
                ans++;
                visited[i] = true;
                visited[index] = true;
                index--;
            }
            if(index<0){
                break;
            }
        }

        ans = size-ans;

        out.write(ans+"\n");


        out.flush();

    }

    static void print(int[] array){
        for(int j=0;j<array.length;j++){
            System.out.print(j+":"+array[j]+", ");
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