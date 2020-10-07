package imp;


import java.util.*;

public class Sieve {

    static class Expo{

        int a;
        int b;

        private Expo(int a, int b) {
            this.a = a;
            this.b = b;
        }
    }

    int size;
    int[] primes;
    static ArrayList<Integer> divisors;
    Sieve(int size){
        this.size = size+1;
        primes = new int[this.size];
    }

    void initialize(){
        for(int i=2;i<size;i++){
            if(primes[i]==0){
                int multi = 1;
                while (multi*i<size){
                    primes[i*multi] = i;
                    multi++;
                }
            }
        }
    }

    ArrayList<Expo> primeFact(int v){
        ArrayList<Expo> list = new ArrayList<>();
        Map<Integer,Integer> map = new HashMap<>();
        while (v>1){
            int a = primes[v];
            if(map.containsKey(a)){
                list.set(map.get(a),new Expo(a,list.get(map.get(a)).b+1));
            }
            else{
                list.add(new Expo(a,1));
                map.put(a,list.size()-1);
            }
            v /=primes[v];
        }
        return list;
    }

    ArrayList<Integer> allDivisors(int v){
        ArrayList<Expo> list = primeFact(v);

        ArrayList<ArrayList<Integer>> lists2d = new ArrayList<>();

        for(int i=0;i<list.size();i++){
            lists2d.add(new ArrayList<>());
            int a = lists2d.size()-1;
            lists2d.get(a).add(1);
            int b = list.get(i).a;
            int d = b;
            int pow = list.get(i).b;

            int  j =1;
            while (j<=pow){
                lists2d.get(a).add(b);
                b*=d;
                j++;
            }
        }

        divisors = new ArrayList<>();
        recursion(lists2d,0,1);

        return divisors;
    }

    void recursion(ArrayList<ArrayList<Integer>> lists,int index,int div){
        if(index==lists.size()-1){
            for(int i=0;i<lists.get(index).size();i++){
                divisors.add(div*lists.get(index).get(i));
            }
        }
        else{
            for(int i=0;i<lists.get(index).size();i++){
                recursion(lists,index+1,div*lists.get(index).get(i));
            }
        }
    }

}
