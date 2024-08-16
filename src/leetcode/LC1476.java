package leetcode;

import javax.imageio.IIOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.util.Arrays;

public class LC1476 {
    static BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
    public static void main(String[] args) throws IIOException {

        System.out.println( Arrays.toString(findArray(new int[]{5, 2, 0, 3, 1})));
    }

    public static int[] findArray(int[] pref) {

        int a = 5;
        int[] ans = new int[pref.length];
        int size = pref.length;
        ans[0] = pref[0];
        a = pref[0];
        for(int i=1;i<size;i++){
            int b = pref[i]^a;
            ans[i] = b;
            a = a^b;
        }
        return ans;
    }
}
