package practice;

import java.io.*;

public class d1311 {

    static BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {

        int testCase = Integer.parseInt(inp.readLine());

        for(int t=0;t<testCase;t++){
            String[] s1 = inp.readLine().split(" ");
            int a = Integer.parseInt(s1[0]);
            int b = Integer.parseInt(s1[1]);
            int c = Integer.parseInt(s1[2]);

            int[] ans = new int[3];

            int min = Integer.MAX_VALUE;

            int x = 1;
            int y = 1;

            for(int i=1;i<=20000;i++){
                x = i;
                y = x;
                int rem = Math.abs(a-x);
                int aa = 0;
                int bb = 0;
                while (y<=20000){

                    aa = Math.abs(b-y);

                    int f = c%y;

                    if(f<y-f){
                        bb = f;
                        min = Math.min(min,rem+aa+bb);
                        if(min==rem+aa+bb){
                            ans[0] = x;
                            ans[1] = y;
                            ans[2] = c/y*y;
                        }
                    }
                    else{
                        bb = y-f;
                        min = Math.min(min,rem+aa+bb);
                        if(min==rem+aa+bb){
                            ans[0] = x;
                            ans[1] = y;
                            ans[2] = (1+c/y)*y;
                        }
                    }
                    y+=x;
                }

            }

            out.write(min+"\n");
            out.write(ans[0]+" "+ans[1]+" "+ans[2]+"\n");

        }

        out.flush();

    }
}
