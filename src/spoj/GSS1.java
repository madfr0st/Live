package spoj;

import java.io.*;

public class GSS1 {
    static class SegmentTree {
        public int[] createTree(int input[], Operation operation){
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
                return  Integer.MIN_VALUE/10000;
            }
            int mid = (low+high)/2;
            int a = rangeQuery(segmentTree,low,mid,qlow,qhigh,2*pos+1, operation);
            int b = rangeQuery(segmentTree,mid+1,high,qlow,qhigh,2*pos+2, operation);

            //System.out.println(a+" "+b);
            return Math.max(a,b);
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

    static class MaxOperation implements Operation {

        @Override
        public int perform(int a, int b) {
            int c = a+b;
            if(a>b){
                return Math.max(c,a);
            }
            else{
                return Math.max(c,b);
            }
        }
    }

    static class MinOperation implements Operation {
        @Override
        public int perform(int a, int b){
            return Math.min(a,b);
        }
    }
    static BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args)throws IOException{
        int size = Integer.parseInt(inp.readLine());
        int s = 2;
        while (size>s){
            s*=2;
        }
        int[] given = new int[s];
        String[] s1 = inp.readLine().split(" ");
        for(int i=0;i<size;i++){
            given[i] = Integer.parseInt(s1[i]);
        }
        for(int i=size;i<s;i++){
            given[i] = Integer.MIN_VALUE/10000;
        }

        SegmentTree segmentTree = new SegmentTree();


        MaxOperation operation = new MaxOperation();
        int[] tree = segmentTree.createTree(given,operation);

//        for(int i=0;i<tree.length;i++){
//            System.out.print(tree[i]+" ");
//        }
        int q = Integer.parseInt(inp.readLine());
        for(int i=0;i<q;i++){
            s1 = inp.readLine().split(" ");
            int a = Integer.parseInt(s1[0]);
            int b = Integer.parseInt(s1[1]);
            a--;
            b--;
            int c = segmentTree.rangeQuery(tree,a,b,size,operation);
            out.write(c+"\n");
        }

        //System.out.println();
        out.flush();


    }
}
