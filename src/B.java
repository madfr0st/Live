
import java.io.*;
import java.util.*;

public class B {

    public static void main(String[] args) throws IOException {
        BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

        int testCase = Integer.parseInt(inp.readLine());

        for(int t=0;t<testCase;t++){

            String[] s1 = inp.readLine().split(" ");
            int a = Integer.parseInt(s1[0]);
            int b = Integer.parseInt(s1[1]);

            if(a>3){
                out.write("YES"+"\n");
            }
            else{
                if(a>=b){
                    out.write("YES"+"\n");
                }
                else if(a==2 && b==3){
                    out.write("YES"+"\n");
                }
                else{
                    out.write("NO"+"\n");
                }
            }


        }
        out.flush();
    }
}
