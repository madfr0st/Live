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

        int[] given = new int[]{1,8,6,2,5,4,8,3,7};
        System.out.println(maxArea(given));


    }
    public static int maxArea(int[] height) {
        int ans = 0;
        int l = 0;
        int r = height.length-1;
        while (l<r){
            ans = Math.max(ans,Math.min(height[l],height[r])* (r-l));
            if(height[l]<height[r]){

                l++;
            }
            else{

                r--;
            }
        }
        return ans;
    }

}
