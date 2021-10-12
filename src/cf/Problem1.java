package cf;

import java.io.*;
import java.util.*;

public class Problem1 {

    static BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        int N = 13;
        int[] A = new int[]{3,3,2,2,8,2,8,3,4,7,9,2,2};
        int B = 3;

//        int N = 3;
//        int[] cf.A = new int[]{1,2,3};
//        int cf.B = 4;

        System.out.println(Arrays.toString(count_triplets(N,A,B)));
    }

    static long[] count_triplets(int N, int[] A, int B) {
        long[] ans = new long[B];

        ArrayList<Integer> list1 = new ArrayList<>();
        ArrayList<Integer> list2 = new ArrayList<>();

        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < N; i++) {
            A[i] %= B;
            if (map.containsKey(A[i])) {
                map.put(A[i], map.get(A[i]) + 1);
            } else {
                map.put(A[i], 1);
                list1.add(A[i]);
            }
        }

        Map<Integer,Integer> sumMap = new HashMap<>();


        for (int i = 0; i < N - 1; i++) {
            for (int j = i + 1; j < N; j++) {
                int sum = A[i] + A[j];
                ans[(A[i]+sum)%B] -= 1;
                ans[(A[j]+sum)%B] -= 1;

                sum%=B;

                if(sumMap.containsKey(sum)){
                    sumMap.put(sum,sumMap.get(sum)+1);
                }
                else{
                    sumMap.put(sum,1);
                    list2.add(sum);
                }

            }
        }



        for(int i=0;i<list1.size();i++){
            for(int j=0;j<list2.size();j++){
                int sum = list1.get(i)+list2.get(j);
                sum%=B;

                int count = map.get(list1.get(i))*sumMap.get(list2.get(j));
                ans[sum]+=count;

            }
        }


        for(int i=0;i<ans.length;i++){
            ans[i]/=3;
        }
        return ans;



    }
}
