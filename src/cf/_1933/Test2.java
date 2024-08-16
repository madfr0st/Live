package cf._1933;

import java.io.IOException;
import java.util.ArrayList;

public class Test2 {
    public static ArrayList<String> list = new ArrayList<>();
    public static void main(String[] args) throws IOException{
        String given = "sssss";
        String[] strings = given.split("");
        int sum = 0;
        for(int i=0;i<strings.length;i++){

           sum+=evenString(strings,i);
           sum+=oddString(strings,i);
        }
        System.out.println(sum);
        //System.out.println(list);

    }

    public static int evenString(String[] string,int pos){
        int size = string.length;
        int count = 0;
        String s = "";

        for(int i=0;i<size;i++){
            if(pos-i-1<0||pos+i>=size){
                return count;
            }
            if(string[pos-i-1].equals(string[pos+i])){
                s=string[pos-i]+s+string[pos+i];
                list.add(s);
                count++;
            }
        }
        return count;
    }

    public static int oddString(String[] string,int pos){
        int size = string.length;
        int count = 0;
        String s = "";


        for(int i=0;i<size;i++){
            if(pos-i<0||pos+i>=size){
                return count;
            }
            if(string[pos-i].equals(string[pos+i])){
                s=string[pos-i]+s+string[pos+i];
                list.add(s);
                count++;
            }

        }
        return count;
    }
}
