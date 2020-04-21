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
            int a = Integer.parseInt(s1[0]);
            int b = Integer.parseInt(s1[1]);
            int c = Integer.parseInt(s1[2]);
            int d = Integer.parseInt(s1[3]);

            int diff = Math.abs(c-d);
            int ans = Math.min(a-1,diff+b);
            out.write(ans+"\n");


        }


        out.flush();

    }
}