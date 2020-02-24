import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class B {
    public static void main(String[] args) throws IOException {
        BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

        int testCase = Integer.parseInt(inp.readLine());

        for(int t=0;t<testCase;t++){
            String[] s1 = inp.readLine().split(" ");
            int size = Integer.parseInt(s1[0]);
            int x = Integer.parseInt(s1[1]);
            Set<Integer> set = new HashSet<>();

            s1 = inp.readLine().split(" ");
            int[] given = new int[size];
            for(int i=0;i<size;i++){
                given[i] = Integer.parseInt(s1[i]);
                set.add(given[i]);
            }

            int min = Integer.MAX_VALUE;

            for(int i=0;i<size;i++){
                int a = x/given[i];
                int rem = x%given[i];
                if(rem>0 && a>0){
                    a++;
                }
                else if(rem>0){
                    a+=2;
                }
                min = Math.min(a,min);
            }

            out.write(min+"\n");

        }
        out.flush();

    }
}
