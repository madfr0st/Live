package leetcode;

import javax.imageio.IIOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class LC1448 {

    static BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
    public static void main(String[] args) throws IIOException {

        TreeNode node = new TreeNode(9);
        node.left = new TreeNode();
        node.left.left = new TreeNode(6);
        node.right = new TreeNode(3);
//        node.right.left = new TreeNode(1);
//        node.right.right = new TreeNode(5);

        System.out.println(goodNodes(node));


    }
    public static int goodNodes(TreeNode root) {
        AtomicInteger ans = new AtomicInteger();
        Map<TreeNode,Integer> maxCount = new HashMap<>();

        maxCount.put(root,root.val);
        goodNotesUtil(maxCount,root);

        System.out.println(maxCount);

        maxCount.forEach((key,val)->{
            if(key.val>=val){
                System.out.println(key.val+" "+val);
                ans.getAndIncrement();
            }
        });



        return ans.get();
    }

    public static void goodNotesUtil(Map<TreeNode,Integer> maxCount, TreeNode root){
        if(root.left!=null){
            System.out.println(root.val);
            maxCount.put(root.left,Math.max(maxCount.get(root),root.left.val));
            goodNotesUtil(maxCount,root.left);
        }
        if(root.right!=null){
            maxCount.put(root.right,Math.max(maxCount.get(root),root.right.val));
            goodNotesUtil(maxCount,root.right);
        }
    }









    public static class TreeNode {
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
}


