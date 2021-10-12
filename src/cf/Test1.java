package cf;

import java.util.*;

public class Test1 {


   static  class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }



  static class Solution {
    public TreeNode constructMaximumBinaryTree(int[] nums) {

      if(nums.length==1){
        return new TreeNode(nums[0]);
      }

      TreeNode root = new TreeNode();
      TreeNode left = new TreeNode();
      TreeNode right = new TreeNode();

      int max = -1;
      int at = 0;
      for(int i=0;i<nums.length;i++){
        if(max<nums[i]){
          max = nums[i];
          at = i;
        }
      }

      int[] l = new int[at];

      System.out.println(Arrays.toString(nums));
      int[] r = new int[nums.length-1-at];

      for(int i=0;i<l.length;i++){
        l[i] = nums[i];
      }

      for(int i=at+1;i<nums.length;i++){
        r[i-at-1] = nums[i];
      }





      if(l.length>0)
        left = constructMaximumBinaryTree(l);
      root.left = left;
      if(r.length>0) {
        right = constructMaximumBinaryTree(r);
        root.right = right;
      }
      root.val = max;




      return root;
    }
  }



  public static void main(String[] args) {
      Solution solution = new Solution();
      solution.constructMaximumBinaryTree(new int[]{3,2,1,6,0,5});
  }
}
