package practice;

import java.io.*;
import java.util.*;


public class C {

    public static void main(String[] args) throws IOException {
        BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));


        String[] s1 = inp.readLine().split(" ");
        int size = Integer.parseInt(s1[0]);
        int at = Integer.parseInt(s1[1]);

        int[] given = new int[size];
        s1 = inp.readLine().split("");
        for(int i=0;i<size;i++){
            given[i] = s1[i].charAt(0)-97;
        }

        int[] diff = new int[size];
        int sum = 0;

        int half = size/2;
        if(size%2==1){
            half = size/2+1;
        }
        for (int i = 0; i < size; i++) {
            diff[i] = Math.min(Math.abs(given[i] - given[size - 1 - i]), Math.abs(given[i] + 26 - given[size - 1 - i]));
            sum += diff[i];
        }

        if(at>half){
            at = size-at+1;
        }

        int left = 0;
        int right = 0;

        int l = at-1;
        int r = at-1;

        int amount = half;

        int delta = 1;
        int a = 0;
        int b = 0;

        System.out.println(at);

        while (r>=0){
            System.out.println(r);
            if(diff[r]>0){
                right = r;
            }
            if(r==half-1){
                r = at;
                delta = -1;
            }
            r+=delta;
        }

        System.out.println(right);

        delta = -1;

        while (l<=half-1){
            if(diff[l]>0){
                left = l;
            }
            if(l==0){
                l = at;
                delta = 1;
            }
            l+=delta;
        }

        r = right;
        l = left;

        if(r<at){
            a = at-r+half-at;
        }
        else{
            a = r-at;
        }

        if(l>at){
            b = l-1+at-1;
        }
        else{
            b = at-l;
        }

        a = Math.max(a,b);
        sum+=a;
        out.write(sum+"\n");

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

