import java.io.*;
import java.util.*;


public class A {
    static int modulo = 1000000007;
    public static void main(String[] args) throws IOException {
        BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

        int testCase = Integer.parseInt(inp.readLine());
        for(int t=0;t<testCase;t++){
            String[] s1 = inp.readLine().split(" ");
            int size = Integer.parseInt(s1[0]);
            int days = Integer.parseInt(s1[1]);

            s1 = inp.readLine().split(" ");
            int ans = 0;
            int[] given = new int[size];
            for(int i=0;i<size;i++){
                given[i] = Integer.parseInt(s1[i]);
            }
            ans = given[0];

            for(int i=1;i<size;i++){
                int a = given[i];
                if(a*i<=days){
                    ans+=a;
                    days-=a*i;
                }
                else{
                    int b = days/i;
                    ans+=b;
                    break;
                }
            }

            System.out.println(ans);

        }

    }
}