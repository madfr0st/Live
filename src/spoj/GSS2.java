package spoj;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class GSS2 {

    static class SegmentTree {
        static int[] tree;

        void createTree(int input[],Operation operation){
            tree = create(input,operation);
        }

        void printTree(){
            for(int i=0;i<tree.length;i++){
                System.out.print(tree[i]+" ");
            }
            System.out.println();
        }

        private int[] create(int input[], Operation operation){
            int height = (int)Math.ceil(Math.log(input.length)/Math.log(2));
            int segmentTree[] = new int[(int)(Math.pow(2, height+1)-1)];
            constructTree(segmentTree,input,0,input.length-1,0, operation);
            return segmentTree;
        }

        private void constructTree(int segmentTree[], int input[], int low, int high,int pos, Operation operation){
            if(low == high){
                segmentTree[pos] = input[low];
                return;
            }
            int mid = (low + high)/2;
            constructTree(segmentTree,input,low,mid,2*pos+1, operation);
            constructTree(segmentTree,input,mid+1,high,2*pos+2, operation);
            segmentTree[pos] = operation.perform(segmentTree[2*pos+1], segmentTree[2*pos+2]);
        }

        public int rangeQuery(int []segmentTree,int qlow,int qhigh,int len, Operation operation){
            return rangeQuery(segmentTree,0,len-1,qlow,qhigh,0, operation);
        }

        private int rangeQuery(int segmentTree[],int low,int high,int qlow,int qhigh,int pos, Operation operation){
            if(qlow <= low && qhigh >= high){
                return segmentTree[pos];
            }
            if(qlow > high || qhigh < low){
                return 0;
            }
            int mid = (low+high)/2;
            return operation.perform(rangeQuery(segmentTree,low,mid,qlow,qhigh,2*pos+1, operation),
                    rangeQuery(segmentTree,mid+1,high,qlow,qhigh,2*pos+2, operation));
        }

        public void updateValueForSumOperation(int input[],int segmentTree[],int newVal,int index){
            int diff = newVal - input[index];
            input[index] = newVal;
            updateVal(segmentTree,0,input.length-1,diff,index,0);
        }

        private void updateVal(int segmentTree[],int low,int high,int diff,int index, int pos){
            if(index < low || index > high){
                return;
            }
            segmentTree[pos] += diff;
            if(low >= high){
                return;
            }
            int mid = (low + high)/2;
            updateVal(segmentTree,low,mid,diff,index,2*pos+1);
            updateVal(segmentTree,mid+1,high,diff,index,2*pos+2);
        }

    }
    interface Operation{
        int perform(int a, int b);
    }

    static class SumOperation implements Operation {
        @Override
        public int perform(int a, int b) {
            return Math.max(a,b);
        }
    }

    static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
    static BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException{
        int size = Integer.parseInt(inp.readLine());
        String[] s1 = inp.readLine().split(" ");
        int[] given = new int[size];
        for(int i=0;i<size;i++){
            given[i] =Integer.parseInt(s1[i]);
        }
        Set<Integer> set = new HashSet<>();
        ArrayList<Integer> index = new ArrayList<>();
        for(int i=0;i<size;i++){
            if(set.contains(given[i])){
                set.clear();
                index.add(i);
            }
            else{
                set.add(given[i]);
            }
        }
        index.add(given.length);
        int left = 0;
        int right = 0;

        SumOperation operation = new SumOperation();
        SegmentTree[] segmentTrees = new SegmentTree[index.size()];

        System.out.println(index);

        for(int i=0;i<index.size();i++){
            right = index.get(i)-1;

            int a = right-left+1;
            int[] temp = new int[a];
            for(int j=left;j<=right;j++){
                temp[j-left] = given[j];
            }
            segmentTrees[i] = new SegmentTree();
            segmentTrees[i].createTree(temp,operation);
            left = right + 1;
        }


        int q = Integer.parseInt(inp.readLine());
        for(int t=0;t<q;t++){
            s1 = inp.readLine().split(" ");
            int a = Integer.parseInt(s1[0]);
            int b = Integer.parseInt(s1[1]);
            b--;
            a--;
            left = Collections.binarySearch(index,a);
            right = Collections.binarySearch(index,b);
            
        }
    }

}
