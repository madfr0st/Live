package bin;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Barens {
    public static void main(String[] args) throws IOException, InterruptedException {
        // 2252

        int words = 818;
        //words = 2;

        String path = "/Users/suman/text/barens.txt";
        String path1 = "/Users/suman/text/barens-statement.txt";
        BufferedReader inp = new BufferedReader(new FileReader(new File(path)));
        BufferedReader inp1 = new BufferedReader(new FileReader(new File(path1)));
        int i = 1;
        String[] wrd = new String[817];
        String[] mn = new String[817];
        List<String> list = new ArrayList<>();
        int k = 1;
        while (k<1539){
            list.add(inp1.readLine().replace("\"","'"));
            k++;
           // System.out.println(k);
        }
        Map<String,String> map = new HashMap<>();
        //System.out.println(list);
        while (true) {
            String s1 = inp.readLine();
            //System.out.println(s1);
            if(s1.equals(i+".")){
                String s2 = inp.readLine().replaceAll(" ","");
                String s3 = inp.readLine();
               // System.out.println("aa\taa\taa");
               // System.out.println("Use "+s2.replace(' ','"' )+"\" in sentence.");
                for(int l=0;l<list.size();l++){
                    if(list.get(l).toLowerCase(Locale.ROOT).contains(s2.toLowerCase())){
                        map.put(s2,list.get(l));
                    }
                }
                wrd[i-1] = s2;
                mn[i-1] = s3;
                //System.out.println(s2+"\t"+s3);
                i++;
            }
            if(i==words){
                break;
            }


        }

        System.out.print("[");
        for(int j=0;j<words-1;j++){
            System.out.print("\""+map.get(wrd[j])+"\",");
//            if(!map.containsKey(wrd[j])){
//                 System.out.println("Use \""+wrd[j].replace(' ','"' )+"\" in sentence.");
//            }
        }
        System.out.println("]");
//
//        System.out.print("[");
//        for(int j=0;j<words-1;j++){
//            System.out.print("\""+mn[j].replace('"','\'')+"\",");
//        }
//        System.out.println("]");


    }
}