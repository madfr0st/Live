import java.io.*;


public class E {
    public static void main(String[] args) throws IOException {
        BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
        System.out.println(pow(8));
        String[] s1 = inp.readLine().split(" ");
        long max = Long.parseLong(s1[0]);
        long amount = Long.parseLong(s1[1]);

        int count1 = 1;
        int count2 = 2;
        while (count1<amount && count2<amount){
            max = max/2;
            count1 = count2*2;
            count2 = count1+1;
        }




    }
    static long pow(long a){
        long c = 1;
        long count = 0;

        while (c<a){
            c*=2;
            count++;
        }
        return count;
    }
}
