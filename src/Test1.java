import java.util.*;

public class Test1 {
    static class Tri implements Comparable<Tri>{
        int a;
        int b;
        int c;

        public Tri(int a, int b, int c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }

        @Override
        public String toString() {
            return "(" + a + ", " + b + "," + c + ")";
        }

        @Override
        public int compareTo(Tri tri) {
            if (this.a > tri.a) {
                return 1;
            } else if (this.a < tri.a) {
                return -1;
            } else {
                if (this.b > tri.b) {
                    return 1;
                } else if (this.b < tri.b) {
                    return -1;
                } else {
                    if (this.c > tri.c) {
                        return 1;
                    } else if (this.c < tri.c) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
            }
        }
    }

    static int[] solution(int Q, int[][] query) {

        int[] ans;

        int size = query.length;
        int count = Integer.MAX_VALUE;

        ArrayList<Integer> dim = new ArrayList<>();
        Map<Integer, Integer> map = new HashMap<>();
        ArrayList<Integer> list = new ArrayList<>();
        PriorityQueue<Tri> priorityQueue = new PriorityQueue<>(Collections.reverseOrder());


        for (int i = 0; i < size; i++) {
            int a = query[i][0];
            int b = query[i][1];

            if (a == 1) {
                if (map.containsKey(b)) {
                    dim.add(0,b);
                    //priorityQueue.add(new Tri(map.get(b),))

                } else {
                    dim.add(0,b);
                }
            } else {

                int num = 0;
                map.clear();
                int at = -1;
                int max = 0;
                int id = -1;
                for(int j=0;j<dim.size();j++){
                    int h = dim.get(j);
                    if(map.containsKey(h)){
                        map.put(h,map.get(h));
                        max = Math.max(max,map.get(h));
                        if(max==map.get(h)){
                            id = h;
                            at = j;
                        }
                    }
                    else{
                        map.put(h,1);
                        max = Math.max(max,1);
                        if(max==1){
                            id = h;
                            at = j;
                        }
                    }
                }

                dim.remove(at);
                list.add(id);

            }
            System.out.println(dim);
        }

        ans = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            ans[i] = list.get(i);
        }


        return ans;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(solution(3, new int[][]{{1, 62}, {1, 49}, {2, -1}})));
    }

}
