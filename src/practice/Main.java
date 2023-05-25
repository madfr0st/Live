package practice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;


public class Main{
    static BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
    public static void main(String[] args) throws IOException{
      Integer i = new Integer(257);
      Short y = i.shortValue();
      y = y.reverseBytes(y);
      System.out.println(y);


    }

    static void print(int[][] array){
        for(int i=0;i< array.length;i++) {
            for (int j = 0; j < array[0].length; j++) {
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }
    }
}