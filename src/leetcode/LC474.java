package leetcode;

import javax.imageio.IIOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.util.Arrays;

public class LC474 {
    static BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
    public static void main(String[] args) throws IIOException {
        String[] given = {"10","0","1"};

        System.out.println(findMaxForm(given, 1, 1));

    }

    public static int findMaxForm(String[] strs, int m, int n) {
        int size = strs.length;
        int[][][] dp = new int[strs.length+1][m+1][n+1];

        int[][] count = new int[size][2];
        for(int i=0;i<size;i++){
            int a = 0;
            int b = 0;
            String[] s = strs[i].split("");
            for(int j=0;j<s.length;j++){
                if(s[j].equals("0")){
                    a++;
                }
                else{
                    b++;
                }
            }
            count[i][0] = a;
            count[i][1] = b;
//            System.out.println(a+" "+b);
        }



        int max = 0;
        for(int i=1;i<=size;i++){
            for(int j=0;j<=m;j++){
                for(int k=0;k<=n;k++){
                    int a = count[i-1][0];
                    int b = count[i-1][1];
                    if(j-a>=0 && k-b>=0){
                        dp[i][j][k] = Math.max(dp[i-1][j-a][k-b]+1,dp[i-1][j][k]);
                    }
                    if(k-1>0)
                    dp[i][j][k] = Math.max(dp[i][j][k],dp[i][j][k-1]);
                    if(j-1>0)
                    dp[i][j][k] = Math.max(dp[i][j][k],dp[i][j-1][k]);
                    dp[i][j][k] = Math.max(dp[i][j][k],dp[i-1][j][k]);

                    max = Math.max(dp[i][j][k],max);

                }
            }
        }


        return max;
    }
}
