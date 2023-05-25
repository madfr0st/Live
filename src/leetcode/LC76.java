package leetcode;

import practice.A;
import practice.H;

import javax.imageio.IIOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

public class LC76 {
    static BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
    public static void main(String[] args) throws IIOException {
        String s = "ADOBECODEBANC";
        String t = "ABC";
        minWindow(s,t);
    }
    public static String minWindow(String s, String t) {

        String[] s1 = s.split("");
        String[] s2 = t.split("");

        Map<String,Integer> map1= new HashMap<>();
        Map<String,Integer> map2 = new HashMap<>();
        Set<String> set = new HashSet<>();
        int count = 0;
        for(int i=0;i<26;i++){
            int j = 65+i;
            map2.put(((char) j)+"" ,0);
            map1.put(((char) j)+"" ,0);
        }
        for(int i=0;i<s2.length;i++){
            set.add(s2[i]);

                map2.put(s2[i], map2.get(s2[i]) + 1);

        }

        int l = 0;
        int r = 0;

        for(int i=0;i<s1.length;i++){
            if(set.contains(s1[i]) && map1.get(s1[i])!=map2.get(s1[i])){
                map1.put(s1[i],map1.get(s1[i])+1);
                count++;
            }
            if(count==s2.length){
                r = i;
                break;
            }
        }

        ArrayList<Integer> left = new ArrayList<>();
        ArrayList<Integer> right = new ArrayList<>();
        left.add(l);
        right.add(r);

        String removed = "";

        while (l<r && r<s1.length){
            System.out.println(removed);
            if(count==s2.length){
                left.add(l);
                right.add(r);
                removed = s1[l];
                l++;
                map1.put(removed,map1.get(removed)-1);
                while (l<r){
                    if(!set.contains(s1[l])){
                        l++;
                    }
                    else if(map1.containsKey(s1[l])) {
                        break;
                    }
                }
                count--;
            }
            else {
                r++;
                if(r<s1.length && s1[r].equals(removed)){
                    count++;
                }
            }
        }

        System.out.println(left);
        System.out.println(right);

        return "";
    }
}
