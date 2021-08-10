import java.util.*;


class Solution {
    public List<Integer> countSmaller(int[] nums) {
        NodeTree nodeTree = new NodeTree(nums[nums.length - 1], 0);
        int[] check = new int[3000];
        HashMap<Integer, Integer> hashMap = new HashMap<>(nums.length);
        hashMap.put(nums.length - 1, 0);
        for (int i = nums.length - 2; i >= 0; i--) {
            nodeTree.Insert(nodeTree, nums[i], 0, hashMap, i);
        }
        List<Integer> list = new ArrayList<>(nums.length);

        for (int i = 0; i < nums.length; i++) {
            list.add(hashMap.get(i));
        }
        return list;

    }
    public static void main(String[] args){
        Solution solution = new Solution();
        ArrayList<Integer> list = (ArrayList<Integer>) solution.countSmaller(new int[]{1,2,4});

        System.out.println(list);
    }
}


class NodeTree {
    int val, smallerCount;
    NodeTree left, right;

    NodeTree(int val, int smallerCount) {
        this.val = val;
        this.smallerCount = smallerCount;
        left = null;
        right = null;
    }

    public NodeTree Insert(NodeTree root, int val, int prevCount, HashMap<Integer, Integer> hashMap, int index) {
        if (root == null) {
            NodeTree tree = new NodeTree(val, 0);
            hashMap.put(index, prevCount);
            return tree;
        }
        if (root.val >= val) {
            root.smallerCount++;
            root.left = Insert(root.left, val, prevCount, hashMap, index);
        } else {
            root.right = Insert(root.right, val, prevCount + root.smallerCount + 1, hashMap, index);
        }
        return root;
    }
}