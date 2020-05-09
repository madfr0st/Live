import java.util.*;

public class Solution {
    public static int solution(int[] x,int[] y){

        Map<Integer,Integer> mapX = new HashMap<>();
        Map<Integer,Integer> mapY = new HashMap<>();
        Set<Integer> set = new HashSet<>();
        ArrayList<Integer> list = new ArrayList<>();

        for(int i=0;i<x.length;i++){
            if(mapX.containsKey(x[i])){
                mapX.put(x[i],mapX.get(x[i])+1);
            }
            else{
                mapX.put(x[i],1);
            }

            if(!set.contains(x[i])){
                set.add(x[i]);
                list.add(x[i]);
            }

        }

        for(int i=0;i<y.length;i++){
            if(mapY.containsKey(y[i])){
                mapY.put(y[i],mapY.get(y[i])+1);
            }
            else{
                mapY.put(y[i],1);
            }

            if(!set.contains(y[i])){
                set.add(y[i]);
                list.add(y[i]);
            }

        }

        int ans = 0;

        for(int i=0;i<list.size();i++){
            if(mapX.get(list.get(i))!=mapY.get(list.get(i))){
                ans = list.get(i);
                break;
            }
        }
        return ans;
    }
}
