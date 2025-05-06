package leetcode;

import javax.imageio.IIOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

public class LC15 {
    static BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
    public static void main(String[] args) throws IIOException {
        System.out.println(threeSum(new int[]{0,0,0}));
    }

    public static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        Arrays.sort(nums);
        Set<List<Integer>> set = new HashSet<>();

        for (int i=0;i<nums.length-2;i++){
            for (int j=i+1;j<nums.length-1;j++){
                int a = nums[i];
                int b = nums[j];
                int d = -(a+b);
                int c = Arrays.binarySearch(nums,j+1,nums.length,d);
                //System.out.println(c);
                if(c>j){
                    List<Integer> list = new ArrayList<>();
                    list.add(a);
                    list.add(b);
                    list.add(nums[c]);
                    set.add(list);
                }
            }
        }

        ans.addAll(set);

        return ans;
    }
}
