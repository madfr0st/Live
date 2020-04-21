package imp;

public class SegTreeLong {

    /**
     * Provides longerface to perform operations on range queue like sum, min or max
     */

    interface Operation{
        long perform(long a, long b);
    }

    static class SumOperation implements Operation {
        @Override
        public long perform(long a, long b) {
            return a+b;
        }
    }

    static class MinOperation implements Operation {
        @Override
        public long perform(long a, long b){
            return Math.min(a,b);
        }
    }

    static class MaxOperation implements Operation {
        @Override
        public long perform(long a, long b){
            return Math.max(a,b);
        }
    }

    public long[] createTree(long[] input, Operation operation){
        long height = (long)Math.ceil(Math.log(input.length)/Math.log(2));
        long[] segmentTree = new long[(int)(Math.pow(2, height+1)-1)];
        createTreeUtil(segmentTree,input,0,input.length-1,0, operation);
        return segmentTree;
    }

    private void createTreeUtil(long[] segmentTree, long[] input, int low, int high,int pos, Operation operation){
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

    public long rangeQuery(long []segmentTree,long qlow,long qhigh,int len, Operation operation){
        return rangeQueryUtil(segmentTree,0,len-1,qlow-1,qhigh-1,0, operation);
    }

    private long rangeQueryUtil(long[] segmentTree,int low,int high,long qlow,long qhigh,int pos, Operation operation){
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
    public void deltaUpdateSegTree(long[] input, long[] segmentTree, int index, long delta, Operation operation){
        index--;
        input[index] += delta;
        deltaUpdateSegTreeUtil(segmentTree, index, delta, 0, input.length - 1, 0, operation);
    }
    private void deltaUpdateSegTreeUtil(long[] segmentTree, long index, long delta, int low, int high, int pos, Operation operation){

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
        deltaUpdateSegTreeUtil(segmentTree, index, delta, low, mid, 2 * pos + 1, operation);
        deltaUpdateSegTreeUtil(segmentTree, index, delta, mid + 1, high, 2 * pos + 2, operation);
        segmentTree[pos] = operation.perform(segmentTree[2*pos+1], segmentTree[2*pos + 2]);
    }

    /**
     * Updates segment tree for certain index by given Value
     */
    public void valueUpdateSegTree(long[] input, long[] segmentTree, int index, long value, Operation operation){
        index--;
        input[index] = value;
        valueUpdateSegTreeUtil(segmentTree, index, value, 0, input.length - 1, 0, operation);
    }
    private void valueUpdateSegTreeUtil(long[] segmentTree, long index, long value, int low, int high, int pos, Operation operation){

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
        valueUpdateSegTreeUtil(segmentTree, index, value, low, mid, 2 * pos + 1, operation);
        valueUpdateSegTreeUtil(segmentTree, index, value, mid + 1, high, 2 * pos + 2, operation);
        segmentTree[pos] = operation.perform(segmentTree[2*pos+1], segmentTree[2*pos + 2]);
    }

    /**
     * Queries given range lazily (delta)
     */
    public long deltaRangeQueryLazy(long[] segmentTree, long[] lazy, long qlow, long qhigh, int len,Operation operation) {
        return deltaRangeQueryLazyUtil(segmentTree, lazy, qlow-1, qhigh-1, 0, len - 1, 0,operation);
    }
    private long deltaRangeQueryLazyUtil(long[] segmentTree, long[] lazy, long qlow, long qhigh,
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
    public void deltaUpdateSegTreeRangeLazy(long[] input, long[] segmentTree, long[] lazy, long startRange, long endRange,
                                                long delta, Operation operation) {
        deltaUpdateSegTreeLazyUtil(segmentTree, lazy, startRange-1, endRange-1, delta, 0,
                input.length - 1, 0, operation);
    }

    private void deltaUpdateSegTreeLazyUtil(long[] segmentTree,
                                                long[] lazy, long startRange, long endRange,
                                                long delta, int low, int high, int pos, Operation operation) {
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
        deltaUpdateSegTreeLazyUtil(segmentTree, lazy, startRange, endRange,
                delta, low, mid, 2*pos+1, operation);
        deltaUpdateSegTreeLazyUtil(segmentTree, lazy, startRange, endRange,
                delta, mid+1, high, 2*pos+2, operation);
        segmentTree[pos] = operation.perform(segmentTree[2*pos + 1], segmentTree[2*pos + 2]);
    }

    /**
     * Queries given range lazily (value)
     */
    public long valueRangeQueryLazy(long[] segmentTree, long[] lazy, long qlow, long qhigh, int len,Operation operation) {
        return valueRangeQueryLazyUtil(segmentTree, lazy, qlow-1, qhigh-1, 0, len - 1, 0,operation);
    }
    private long valueRangeQueryLazyUtil(long[] segmentTree, long[] lazy, long qlow, long qhigh,
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
    public void valueUpdateSegTreeRangeLazy(long[] input, long[] segmentTree, long[] lazy, long startRange, long endRange,
                                                long value, Operation operation) {
        valueUpdateSegTreeLazyUtil(segmentTree, lazy, startRange-1, endRange-1, value, 0,
                input.length - 1, 0, operation);
    }

    private void valueUpdateSegTreeLazyUtil(long[] segmentTree,
                                                long[] lazy, long startRange, long endRange,
                                                long value, int low, int high, int pos, Operation operation) {
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
        valueUpdateSegTreeLazyUtil(segmentTree, lazy, startRange, endRange,
                value, low, mid, 2*pos+1, operation);
        valueUpdateSegTreeLazyUtil(segmentTree, lazy, startRange, endRange,
                value, mid+1, high, 2*pos+2, operation);
        segmentTree[pos] = operation.perform(segmentTree[2*pos + 1], segmentTree[2*pos + 2]);
    }
}
