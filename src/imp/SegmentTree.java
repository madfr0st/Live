package imp;

public class SegmentTree {

    /**
     * Provides interface to perform operations on range queue like sum or min
     */

    interface Operation{
        int perform(int a, int b);
    }

    static class SumOperation implements Operation {
        @Override
        public int perform(int a, int b) {
            return a+b;
        }
    }

    static class MinOperation implements Operation {
        @Override
        public int perform(int a, int b){
            return Math.min(a,b);
        }
    }

    static class MaxOperation implements Operation {
        @Override
        public int perform(int a, int b){
            return Math.max(a,b);
        }
    }

    public int[] createTree(int[] input, Operation operation){
        int height = (int)Math.ceil(Math.log(input.length)/Math.log(2));
        int segmentTree[] = new int[(int)(Math.pow(2, height+1)-1)];
        constructTree(segmentTree,input,0,input.length-1,0, operation);
        return segmentTree;
    }

    private void constructTree(int[] segmentTree, int[] input, int low, int high,int pos, Operation operation){
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
        return rangeQuery(segmentTree,0,len-1,qlow-1,qhigh-1,0, operation);
    }

    private int rangeQuery(int[] segmentTree,int low,int high,int qlow,int qhigh,int pos, Operation operation){
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

    /**
     * Updates segment tree for certain index by given delta
     */
    public void updateSegmentTree(int[] input, int[] segmentTree, int index, int delta, Operation operation){
        input[index] += delta;
        updateSegmentTree(segmentTree, index, delta, 0, input.length - 1, 0, operation);
    }
    private void updateSegmentTree(int[] segmentTree, int index, int delta, int low, int high, int pos, Operation operation){

        //if index to be updated is less than low or higher than high just return.
        if(index < low || index > high){
            return;
        }

        //if low and high become equal, then index will be also equal to them and update
        //that value in segment tree at pos
        if(low == high){
            segmentTree[pos] += delta;
            return;
        }
        //otherwise keep going left and right to find index to be updated
        //and then update current tree position if min of left or right has
        //changed.
        int mid = (low + high)/2;
        updateSegmentTree(segmentTree, index, delta, low, mid, 2 * pos + 1, operation);
        updateSegmentTree(segmentTree, index, delta, mid + 1, high, 2 * pos + 2, operation);
        segmentTree[pos] = operation.perform(segmentTree[2*pos+1], segmentTree[2*pos + 2]);
    }

    /**
     * Updates segment tree for given range by given delta
     */
    public void updateSegmentTreeRange(int[] input, int[] segmentTree, int startRange, int endRange, int delta, Operation operation) {
        for(int i = startRange; i <= endRange; i++) {
            input[i] += delta;
        }
        updateSegmentTreeRange(segmentTree, startRange-1, endRange-1, delta, 0, input.length - 1, 0, operation);
    }
    private void updateSegmentTreeRange(int segmentTree[], int startRange, int endRange, int delta, int low, int high, int pos , Operation operation) {
        if(low > high || startRange > high || endRange < low ) {
            return;
        }

        if(low == high) {
            segmentTree[pos] += delta;
            return;
        }

        int middle = (low + high)/2;
        updateSegmentTreeRange(segmentTree, startRange, endRange, delta, low, middle, 2 * pos + 1, operation);
        updateSegmentTreeRange(segmentTree, startRange, endRange, delta, middle + 1, high, 2 * pos + 2, operation);
        segmentTree[pos] = operation.perform(segmentTree[2*pos+1], segmentTree[2*pos+2]);
    }

    /**
     * Queries given range lazily
     */
    public int rangeQueryLazy(int[] segmentTree, int[] lazy, int qlow, int qhigh, int len,Operation operation) {
        return rangeQueryLazy(segmentTree, lazy, qlow-1, qhigh-1, 0, len - 1, 0,operation);
    }
    private int rangeQueryLazy(int[] segmentTree, int[] lazy, int qlow, int qhigh,
                               int low, int high, int pos,Operation operation) {

        if(low > high) {
            return 0;
        }

        //make sure all propagation is done at pos. If not update tree
        //at pos and mark its children for lazy propagation.
        if (lazy[pos] != 0) {
            segmentTree[pos] += lazy[pos];
            if (low != high) { //not a leaf node
                lazy[2 * pos + 1] += lazy[pos];
                lazy[2 * pos + 2] += lazy[pos];
            }
            lazy[pos] = 0;
        }

        //no overlap
        if(qlow > high || qhigh < low){
            return 0;
        }

        //total overlap
        if(qlow <= low && qhigh >= high){
            return segmentTree[pos];
        }

        //partial overlap
        int mid = (low+high)/2;
        return operation.perform(rangeQueryLazy(segmentTree, lazy, qlow, qhigh,
                low, mid, 2 * pos + 1,operation),
                rangeQueryLazy(segmentTree, lazy,  qlow, qhigh,
                        mid + 1, high, 2 * pos + 2,operation));

    }

    /**
     * Updates given range by given delta lazily
     */
    public void updateSegmentTreeRangeLazy(int input[], int[] segmentTree, int[] lazy, int startRange, int endRange, int delta, Operation operation) {
        updateSegmentTreeRangeLazy(segmentTree, lazy, startRange-1, endRange-1, delta, 0, input.length - 1, 0, operation);
    }

    private void updateSegmentTreeRangeLazy(int[] segmentTree,
                                            int lazy[], int startRange, int endRange,
                                            int delta, int low, int high, int pos, Operation operation) {
        if(low > high) {
            return;
        }

        //make sure all propagation is done at pos. If not update tree
        //at pos and mark its children for lazy propagation.
        if (lazy[pos] != 0) {
            segmentTree[pos] += lazy[pos];
            if (low != high) { //not a leaf node
                lazy[2 * pos + 1] += lazy[pos];
                lazy[2 * pos + 2] += lazy[pos];
            }
            lazy[pos] = 0;
        }

        //no overlap condition
        if(startRange > high || endRange < low) {
            return;
        }

        //total overlap condition
        if(startRange <= low && endRange >= high) {
            segmentTree[pos] += delta;
            if(low != high) {
                lazy[2*pos + 1] += delta;
                lazy[2*pos + 2] += delta;
            }
            return;
        }

        //otherwise partial overlap so look both left and right.
        int mid = (low + high)/2;
        updateSegmentTreeRangeLazy(segmentTree, lazy, startRange, endRange,
                delta, low, mid, 2*pos+1, operation);
        updateSegmentTreeRangeLazy(segmentTree, lazy, startRange, endRange,
                delta, mid+1, high, 2*pos+2, operation);
        segmentTree[pos] = operation.perform(segmentTree[2*pos + 1], segmentTree[2*pos + 2]);
    }
}
