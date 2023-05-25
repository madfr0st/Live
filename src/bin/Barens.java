package bin;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;

public class Barens {
    public static void main(String[] args) throws IOException, InterruptedException {
        // 2252

        int words = 818;
        //words = 2;

        String path = "/Users/suman/text/barens.txt";
        BufferedReader inp = new BufferedReader(new FileReader(new File(path)));
        int i = 1;
        String[] wrd = new String[817];
        String[] mn = new String[817];
        while (true) {
            String s1 = inp.readLine();
            //System.out.println(s1);
            if(s1.equals(i+".")){
                String s2 = inp.readLine();
                String s3 = inp.readLine();
                //System.out.println(i+" "+s2+" : "+s3);
                wrd[i-1] = s2;
                mn[i-1] = s3;
                i++;
            }
            if(i==words){
                break;
            }


        }

        System.out.print("{");
        for(int j=0;j<wrd.length;j++){

            System.out.print("\""+wrd[j]+"\",");
        }
        System.out.print("}");
        System.out.println(Arrays.toString(wrd));
        System.out.println(Arrays.toString(mn));


    }
}