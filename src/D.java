import java.io.*;
import java.util.*;


public class D {

    static BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {



        String[] s1 = inp.readLine().split(" ");
        long xor = Long.parseLong(s1[0]);
        long sum = Long.parseLong(s1[1]);

        if(sum>xor) {
            if( ((xor^sum)&1)==0){

                long a = sum-xor;

                long b = a/2;

                ArrayList<Integer> list = decToBinary(xor);
                ArrayList<Integer> list1 = decToBinary(b);

                boolean ans = true;

                for(int i=0;i<64;i++){
                    if(list.get(i)==1 && list1.get(i)==1){
                        ans = false;
                        break;
                    }
                }

                if(ans){
                    xor+=b;
                    out.write(2+"\n");
                    out.write(b+" "+xor+"\n");
                }
                else{
                    out.write(3+"\n");
                    out.write(b+" "+b+" "+xor+"\n");
                }


            }
            else{
                out.write(-1+"\n");
            }

        }
        else if(sum==xor && sum==0){
            out.write(sum+"\n");
        }

        else if(sum==xor){
            out.write(1+"\n");
            out.write(sum+"\n");
        }
        else {
            out.write(-1+"\n");
        }


        out.flush();

    }
    public static ArrayList<Integer> decToBinary(long n)
    {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 63; i >= 0; i--) {
            long k = n >> i;
            if ((k & 1L) > 0)
                list.add(1);
            else
                list.add(0);
        }
        return list;
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