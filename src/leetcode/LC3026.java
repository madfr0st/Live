package leetcode;

import javax.imageio.IIOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class LC3026 {
    static BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
    public static void main(String[] args) throws IIOException {



    }

    public static long maximumSubarraySum(int[] nums, int k) {

        ArrayList<Integer>[] list = new ArrayList[(int) 10e5+1];
        for(int i=0;i<=10e5;i++){
            list[i] = new ArrayList<>();
        }


        int size = nums.length;

        int[] sumArray = new int[size];
        int sum = 0;
        for(int i=0;i<size;i++){
            list[nums[i]].add(i);
            sum+=nums[i];

        }

        return 0;
    }

}
