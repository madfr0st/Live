package imp;

public class SegmentTree {

    /**
     * Provides interface to perform operations on range queue like sum, min or max
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
        createTreeUtil(segmentTree,input,0,input.length-1,0, operation);
        return segmentTree;
    }

    private void createTreeUtil(int[] segmentTree, int[] input, int low, int high,int pos, Operation operation){
        if(low == high){
            segmentTree[pos] = input[low];
            return;
        }
        int mid = (low + high)/2;
        createTreeUtil(segmentTree,input,low,mid,2*pos+1, operation);
        createTreeUtil(segmentTree,input,mid+1,high,2*pos+2, operation);
        segmentTree[pos] = operation.perform(segmentTree[2*pos+1], segmentTree[2*pos+2]);
    }

    /**
     *RangeQuery segment tree for certain range (when not applied with lazy)
     */

    public int rangeQuery(int []segmentTree,int qlow,int qhigh,int len, Operation operation){
        return rangeQueryUtil(segmentTree,0,len-1,qlow-1,qhigh-1,0, operation);
    }

    private int rangeQueryUtil(int[] segmentTree,int low,int high,int qlow,int qhigh,int pos, Operation operation){
        if(qlow <= low && qhigh >= high){
            return segmentTree[pos];
        }
        if(qlow > high || qhigh < low){
            return 0;
        }
        int mid = (low+high)/2;
        return operation.perform(rangeQueryUtil(segmentTree,low,mid,qlow,qhigh,2*pos+1, operation),
                rangeQueryUtil(segmentTree,mid+1,high,qlow,qhigh,2*pos+2, operation));
    }

    /**
     * Updates segment tree for certain index by given delta
     */
    public void deltaUpdateSegmentTree(int[] input, int[] segmentTree, int index, int delta, Operation operation){
        input[index] += delta;
        deltaUpdateSegmentTreeUtil(segmentTree, index, delta, 0, input.length - 1, 0, operation);
    }
    private void deltaUpdateSegmentTreeUtil(int[] segmentTree, int index, int delta, int low, int high, int pos, Operation operation){

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
        deltaUpdateSegmentTreeUtil(segmentTree, index, delta, low, mid, 2 * pos + 1, operation);
        deltaUpdateSegmentTreeUtil(segmentTree, index, delta, mid + 1, high, 2 * pos + 2, operation);
        segmentTree[pos] = operation.perform(segmentTree[2*pos+1], segmentTree[2*pos + 2]);
    }

    /**
     * Updates segment tree for certain index by given Value
     */
    public void valueUpdateSegmentTree(int[] input, int[] segmentTree, int index, int value, Operation operation){
        input[index] = value;
        valueUpdateSegmentTreeUtil(segmentTree, index, value, 0, input.length - 1, 0, operation);
    }
    private void valueUpdateSegmentTreeUtil(int[] segmentTree, int index, int value, int low, int high, int pos, Operation operation){

        //if index to be updated is less than low or higher than high just return.
        if(index < low || index > high){
            return;
        }

        //if low and high become equal, then index will be also equal to them and update
        //that value in segment tree at pos
        if(low == high){
            segmentTree[pos] = value;
            return;
        }
        //otherwise keep going left and right to find index to be updated
        //and then update current tree position if min of left or right has
        //changed.
        int mid = (low + high)/2;
        valueUpdateSegmentTreeUtil(segmentTree, index, value, low, mid, 2 * pos + 1, operation);
        valueUpdateSegmentTreeUtil(segmentTree, index, value, mid + 1, high, 2 * pos + 2, operation);
        segmentTree[pos] = operation.perform(segmentTree[2*pos+1], segmentTree[2*pos + 2]);
    }

    /**
     * Queries given range lazily (delta)
     */
    public int deltaRangeQueryLazy(int[] segmentTree, int[] lazy, int qlow, int qhigh, int len,Operation operation) {
        return deltaRangeQueryLazyUtil(segmentTree, lazy, qlow-1, qhigh-1, 0, len - 1, 0,operation);
    }
    private int deltaRangeQueryLazyUtil(int[] segmentTree, int[] lazy, int qlow, int qhigh,
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
        return operation.perform(deltaRangeQueryLazyUtil(segmentTree, lazy, qlow, qhigh,
                low, mid, 2 * pos + 1,operation),
                deltaRangeQueryLazyUtil(segmentTree, lazy,  qlow, qhigh,
                        mid + 1, high, 2 * pos + 2,operation));

    }

    /**
     * Updates given range by given delta lazily
     */
    public void deltaUpdateSegmentTreeRangeLazy(int[] input, int[] segmentTree, int[] lazy, int startRange, int endRange,
                                                int delta, Operation operation) {
        deltaUpdateSegmentTreeLazyUtil(segmentTree, lazy, startRange-1, endRange-1, delta, 0,
                input.length - 1, 0, operation);
    }

    private void deltaUpdateSegmentTreeLazyUtil(int[] segmentTree,
                                            int[] lazy, int startRange, int endRange,
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
        deltaUpdateSegmentTreeLazyUtil(segmentTree, lazy, startRange, endRange,
                delta, low, mid, 2*pos+1, operation);
        deltaUpdateSegmentTreeLazyUtil(segmentTree, lazy, startRange, endRange,
                delta, mid+1, high, 2*pos+2, operation);
        segmentTree[pos] = operation.perform(segmentTree[2*pos + 1], segmentTree[2*pos + 2]);
    }

    /**
     * Queries given range lazily (value)
     */
    public int valueRangeQueryLazy(int[] segmentTree, int[] lazy, int qlow, int qhigh, int len,Operation operation) {
        return valueRangeQueryLazyUtil(segmentTree, lazy, qlow-1, qhigh-1, 0, len - 1, 0,operation);
    }
    private int valueRangeQueryLazyUtil(int[] segmentTree, int[] lazy, int qlow, int qhigh,
                                        int low, int high, int pos,Operation operation) {

        if(low > high) {
            return 0;
        }

        //make sure all propagation is done at pos. If not update tree
        //at pos and mark its children for lazy propagation.
        if (lazy[pos] != 0) {
            segmentTree[pos] = lazy[pos];
            if (low != high) { //not a leaf node
                lazy[2 * pos + 1] = lazy[pos];
                lazy[2 * pos + 2] = lazy[pos];
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
        return operation.perform(valueRangeQueryLazyUtil(segmentTree, lazy, qlow, qhigh,
                low, mid, 2 * pos + 1,operation),
                valueRangeQueryLazyUtil(segmentTree, lazy,  qlow, qhigh,
                        mid + 1, high, 2 * pos + 2,operation));

    }

    /**
     * Updates given range by given value lazily
     */
    public void valueUpdateSegmentTreeRangeLazy(int[] input, int[] segmentTree, int[] lazy, int startRange, int endRange,
                                                int value, Operation operation) {
        valueUpdateSegmentTreeLazyUtil(segmentTree, lazy, startRange-1, endRange-1, value, 0,
                input.length - 1, 0, operation);
    }

    private void valueUpdateSegmentTreeLazyUtil(int[] segmentTree,
                                                int[] lazy, int startRange, int endRange,
                                                int value, int low, int high, int pos, Operation operation) {
        if(low > high) {
            return;
        }

        //make sure all propagation is done at pos. If not update tree
        //at pos and mark its children for lazy propagation.
        if (lazy[pos] != 0) {
            segmentTree[pos] = lazy[pos];
            if (low != high) { //not a leaf node
                lazy[2 * pos + 1] = lazy[pos];
                lazy[2 * pos + 2] = lazy[pos];
            }
            lazy[pos] = 0;
        }

        //no overlap condition
        if(startRange > high || endRange < low) {
            return;
        }

        //total overlap condition
        if(startRange <= low && endRange >= high) {
            segmentTree[pos] = value;
            if(low != high) {
                lazy[2*pos + 1] = value;
                lazy[2*pos + 2] = value;
            }
            return;
        }

        //otherwise partial overlap so look both left and right.
        int mid = (low + high)/2;
        valueUpdateSegmentTreeLazyUtil(segmentTree, lazy, startRange, endRange,
                value, low, mid, 2*pos+1, operation);
        valueUpdateSegmentTreeLazyUtil(segmentTree, lazy, startRange, endRange,
                value, mid+1, high, 2*pos+2, operation);
        segmentTree[pos] = operation.perform(segmentTree[2*pos + 1], segmentTree[2*pos + 2]);
    }
}
