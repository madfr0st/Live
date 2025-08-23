package leetcode;

import javax.imageio.IIOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;

public class LC41 {
    static BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
    public static void main(String[] args) throws IIOException {

        int[] given  = new int[]{
                0,1,2
        };

        System.out.println(firstMissingPositive(given));

    }
    public static int firstMissingPositive(int[] nums) {

        boolean one   = false;
        for(int i=0;i<nums.length;i++){
            if(nums[i]==1){
                one = true;
            }
        }
        if(!one){
            return 1;
        }

        for(int i=0;i<nums.length;i++){
            if(nums[i]<=0){
                nums[i] = 1;
            }
        }

        for(int i=0;i<nums.length;i++){
            if(Math.abs(nums[i])<=nums.length && nums[i]!=0){
                nums[Math.abs(nums[i])-1] = - Math.abs(nums[Math.abs(nums[i])-1]);
            }

        }
        System.out.println(Arrays.toString(nums));

        for(int i=0;i<nums.length;i++){
            if(nums[i]>=0){
                return i+1;
            }
        }

        return nums.length;

    }

}
