package cf._1914;

import java.io.IOException;
import java.util.ArrayList;

public class Test1 {
    public static void main(String[] args) throws IOException{
        int[] given = new int[]{10,1,2,7,6,1,5};
        int sum = 8;
        ArrayList<ArrayList<Integer>> arrayLists = new ArrayList<>();

        int size = given.length;
        for(int i=0;i<10;i++){
            arrayLists.add(new ArrayList<>());
        }
        int tempSize = size;
        for(int i=0;i<size;i++){
            for(int j=0;j<=i;j++){
                arrayLists.get(i).add(given[j]);
            }
        }

        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();

        for(int i=0;i<given.length;i++){
            int tempSum = 0;
            for(int j=0;j<arrayLists.get(i).size();j++){
                tempSum+=arrayLists.get(i).get(j);
            }
            if(tempSum == sum){
                ans.add(arrayLists.get(i));
            }
        }

        System.out.println(ans);




    }
}
