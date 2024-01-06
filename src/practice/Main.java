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
      double a = 275.0;
      double b = 550.0;
      double c = 195;
      double d = 70;
      double e = 75;
      double f = 548.25;

      System.out.println("shazli : " + (price(a)+price(e)/8));
      System.out.println( "farhan : " + (price(b)/2+price(e)/8));
      System.out.println( "bhavya : " + (price(b)/2+price(e)/8));
      System.out.println("nupur : "+ (price(e)+price(e)/8));

      System.out.println("akshat : "+(f+price(e)/8+price(c)/4));
        System.out.println("suman : "+(f+price(e)/8+price(c)/4));
        System.out.println("akash : "+(f+price(e)/8+price(c)/4));
        System.out.println("dixhit : "+(f+price(e)/8+price(c)/4));
        System.out.println("suman.saurav117@ybl");


    }

    static void print(int[][] array){
        for(int i=0;i< array.length;i++) {
            for (int j = 0; j < array[0].length; j++) {
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }
    }

    static double  price(double a){
        return (a*1.10)*1.05;
    }
}