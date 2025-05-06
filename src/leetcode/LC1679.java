package leetcode;

import javax.imageio.IIOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Collections;

public class LC1679 {
    static BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
    public static void main(String[] args) throws IIOException {

        int[] given = new int[]{1,8,6,2,5,4,8,3,7};
        System.out.println(maxOperations(given,3));


    }
    public static int maxOperations(int[] nums, int k) {
        int ans = 0;
        int l = 0;
        int r = nums.length-1;
        Arrays.sort(nums);

        while (l<r){
            if(nums[l]+nums[r]==k){
                ans++;
            }
            if(nums[l]+nums[r]<k){

                l++;
            }
            else{

                r--;
            }
        }
        return ans;
    }

}
