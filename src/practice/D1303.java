package practice;

import java.io.*;

public class D1303 {
    public static void main(String[] args) throws IOException{
        BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

        int testCase = Integer.parseInt(inp.readLine());

        for(int t=0;t<testCase;t++){
            String[] s1 = inp.readLine().split(" ");
            long n = Integer.parseInt(s1[0]);
            int size = Integer.parseInt(s1[1]);
            int[] given = new int[size];
            s1 = inp.readLine().split(" ");
            long sum = 0;
            for(int i=0;i<size;i++){
                int a = Integer.parseInt(s1[i]);
                given[red(a)]++;
                sum+=a;
            }

            int[] bin = decToBinary(n);

            if(sum>n){

                int count = 0;

                for(int i=63;i<=0;i--){

                    if(bin[i]==1){
                        if(given[i]>0){
                            given[i]--;
                        }
                        else{

                        }
                    }

                    


                }

            }
            else if(sum==n){
                System.out.println(0);
            }
            else{
                System.out.println(-1);
            }



        }


    }
    static int[] decToBinary(long n) {
        int[] ans = new int[64];
        // array to store binary number
        int[] binaryNum = new int[64];

        // counter for binary array
        int i = 0;
        while (n > 0) {
            // storing remainder in binary array
            long a = n% 2L;
            binaryNum[i] = (int) a;
            n = n / 2l;
            i++;
        }

        // printing binary array in reverse order
        int k = 64-i;
        for (int j = i - 1; j >= 0; j--){
            ans[k] = binaryNum[j];
            k++;
            }
        return ans;
    }
    static int red(int a){
        int b = 1;
        int count = 0;
        while (b!=a){
            b*=2;
            count++;
        }
        return count;
    }
}
