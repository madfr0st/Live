

import java.util.*;

class Test{
    static int ans = Integer.MAX_VALUE;
    public static void main(long given){



    }

    static int check(long n,int pre,int op){

        int sum = 0;

        String[] s1 = (n+"").split("");
        String p = "";
        for(int i=1;i<s1.length;i++){
            p+=s1[i];
        }

        String k = (Integer.parseInt(s1[1])+1)+"";

        String kk = "";
        for(int i=0;i<s1.length-1;i++){
            kk+=k;
        }
        long delta = Long.parseLong(kk);

        int t1 = check(delta,pre,op+1);
        //int t2 = check(norm,pre,op+1);


        return sum;
    }

}