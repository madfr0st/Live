package leetcode;

import javax.imageio.IIOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Stack;

public class LC11 {
    static BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
    public static void main(String[] args) throws IIOException {

        int[] given = new int[]{1,1};
        System.out.println(maxArea(given));


    }
    public static int maxArea(int[] height) {
        int max = 0;
        int[] reverse = new int[height.length];
        for(int i=0;i<reverse.length;i++){
            reverse[i] = height[reverse.length-1-i];
        }
        int a = Math.max(solve(height,reverse),solve(reverse,height));



        return a;
    }

    public static int solve(int[] given, int[] reverse){
        int size = given.length;
        int[] max = new int[size];
        int ans = 0;
        max[0] = reverse[0];
        for(int i=1;i<size;i++){
            max[i] = Math.max(max[i-1],reverse[i]);
        }
        for(int i=0;i<size;i++){
            int b = Arrays.binarySearch(max,given[i]);
            if(b>=0){
                while (b>0){
                    if(max[b-1]==given[i]){
                        b--;
                    }
                    else {
                        break;
                    }
                }
            }
            else {
                b = -b-1;
            }
            ans = Math.max((size-b-i-1)*given[i],ans);

        }

        return ans;
    }


}
