package neetCode;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class NC214_2 {
    static BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
    public static String shortestPalindrome(String s) {
        long hashBase = 31;
        long mod = (long) 1e9+7;
        long left = 0, right = 0, power = 1;
        int index = -1;

        for(int i=0; i<s.length(); i++){
            char at = s.charAt(i);

            left = (left * hashBase + (at-'a'+1)) % mod;
            right = (right + (at-'a'+1)*power) % mod;
            power = (power * hashBase) % mod;
            if(left==right){
                index = i;
            }
        }
        String suff = s.substring(index+1);
        StringBuilder rev = new StringBuilder(suff).reverse();
        return rev.append(s).toString();
    }
    public static void main(String[] args) {
        String S = "mannamus";
        System.out.println(shortestPalindrome(S));
    }
}
