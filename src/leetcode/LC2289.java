package leetcode;

import javax.imageio.IIOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

public class LC2289 {
    static BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
    public static void main(String[] args) throws IIOException {
        int[] given = new int[]{7,14,4,14,13,2,6,13};
        int[] given1 = new int[]{4,5,7,7,13};
        int[] given2 = new int[]{10,1,2,3,4,5,6,1,2,3};
        System.out.println(totalSteps(given));
    }

    public static int totalSteps(int[] nums) {
        int max = 0;
        int count = 0;
        ArrayList<Integer> list = new ArrayList<>();
        list.add(0);

        int courrent = nums[0];
        for(int i=1;i<nums.length;i++) {
            if(nums[i]>=courrent){
                courrent = nums[i];
               list.add(i);
            }
        }
        list.add(nums.length);
        if(list.size()>=2){
            for(int i=1;i<list.size();i++){
                int a = list.get(i-1);
                int b = list.get(i);
                ArrayList<Integer> list1 = new ArrayList<>();
                for(int j=a+1;j<b;j++){
                    list1.add(nums[j]);
                }
                max = Math.max(check(list1),max);
            }
        }
        else {
            int a = 0;
            int b = nums.length;
            ArrayList<Integer> list1 = new ArrayList<>();
            for(int j=a+1;j<b;j++){
                list1.add(nums[j]);
            }
            max = Math.max(check(list1),max);
        }

        return max;
    }

    static int check(ArrayList<Integer> list){
        Stack<Integer> stack = new Stack<>();
        stack.add(Integer.MAX_VALUE);
        Map<Integer,Integer> map = new HashMap<>();
        int max = 0;
        int pop = 0;
        map.put(Integer.MAX_VALUE,0);
        for(int i=0;i<list.size();i++){
            int a = list.get(i);
            while (!stack.empty()){
                int b = stack.peek();
                if(b>a){
                    stack.add(a);
                    map.put(a,pop+1);
                    max = Math.max(map.get(a)-map.get(b),max);
                    break;
                }
                else {
                    int c = stack.pop();
                    pop = Math.max(map.get(c),pop);
                }
            }
        }
        return max;
    }



}
