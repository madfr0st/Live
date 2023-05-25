package leetcode;

import javax.imageio.IIOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LC290 {
    static BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
    public static void main(String[] args) throws IIOException {


        String a = "abba";
        String b = "dog cat cat fish";
        System.out.println(wordPattern(a,b));

    }
    public static boolean wordPattern(String pattern, String s) {
        Map<String,String> map = new HashMap<>();
        String[] strings = pattern.split("");
        String[] givenString = s.split(" ");
        String made = "";
        Set<String> used = new HashSet<>();

        if(strings.length!=givenString.length){
            return false;
        }
        for(int i=0;i<givenString.length;i++){
            if(map.containsKey(givenString[i])){
                made += map.get(givenString[i]);
            }
            else{
                if(used.contains(strings[i])){
                    return false;
                }
                used.add(strings[i]);
                map.put(givenString[i],strings[i]);

                made += strings[i];
            }
        }

        if(made.equals(pattern)){
            return true;
        }

        return false;
    }
}
