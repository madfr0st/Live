package practice;

import java.io.*;
import java.util.ArrayList;


public class B1033 {

    static ArrayList<Long> list;
    public static void main(String[] args)throws IOException {
        BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

        list = sieveOfEratosthenes();
        //System.out.println(list);
        int testCase = Integer.parseInt(inp.readLine());
        for(int t=0;t<testCase;t++){
            String[] s1 = inp.readLine().split(" ");
            long a = Long.parseLong(s1[0]);
            long b =Long.parseLong(s1[1]);

            long c = a+b;
            long d = a-b;

            //System.out.println(d);

            if(d!=1L){
                System.out.println("NO");
            }
            else{

                if(isPrime(c)){
                    System.out.println("YES");
                }
                else{
                    System.out.println("NO");
                }

            }


        }


    }
    static ArrayList<Long> sieveOfEratosthenes() {
        int n = 1000000;
        ArrayList<Long> list = new ArrayList<>();
        boolean[] prime = new boolean[n];

        for(int p = 2; p*p <n; p++) {
            if(!prime[p]) {
                for(int i = p*p; i < n; i += p)
                    prime[i] = true;
            }
        }

        for(int i = 2; i < n; i++) {
            if(!prime[i]) {
                list.add((long) i);
            }
        }
        return list;
    }
    static boolean isPrime(long a){
        int b = (int) Math.sqrt(a);
        //System.out.println(b);
        for(int i=0;i<list.size();i++){
            if(list.get(i)<=(long)b) {
                if (a % list.get(i) == 0) {
                    return false;
                }
            }
            else{
                break;
            }
        }
        return true;
    }
}
