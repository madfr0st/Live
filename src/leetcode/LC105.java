package leetcode;

import com.sun.source.tree.Tree;

import javax.imageio.IIOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

public class LC105 {

    static int preIndex = 0;

    static BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
    public static void main(String[] args) throws IIOException {
        int[] pre = new int[]{-1};
        int[] in = new int[]{-1};
        TreeNode root = buildTree(pre,in);
        printInOrder(root);
    }
    public static TreeNode buildTree(int[] preorder, int[] inorder) {

        Map<Integer,Integer> map_in = new HashMap<>();

        for(int i=0;i<preorder.length;i++){
            map_in.put(inorder[i],i);
        }
        int preIndex = 0;
        return build(preorder, 0, inorder.length - 1, map_in);
    }

    private static TreeNode build(int[] preorder, int inStart, int inEnd, Map<Integer,Integer> map_in) {
        if (inStart > inEnd) {
            return null;
        }

        // Pick current node from preorder
        int rootVal = preorder[preIndex++];
        TreeNode root = new TreeNode(rootVal);

        // If no children
        if (inStart == inEnd) {
            return root;
        }

        // Find root in inorder
        int inIndex = map_in.get(rootVal);

        // Build left and right subtrees
        root.left = build(preorder, inStart, inIndex - 1,map_in);
        root.right = build(preorder, inIndex + 1, inEnd,map_in);

        return root;
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

    public static void printInOrder(TreeNode root) {
        if (root == null) {
            return; // base case
        }
        // Left subtree

        // Visit root
        System.out.print(root.val + " ");

        printInOrder(root.left);

        // Right subtree
        printInOrder(root.right);
    }


    public static void printLevelOrder(TreeNode root) {
        if (root == null) {
            return;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode current = queue.poll();
            System.out.print(current.val + " ");

            if (current.left != null) {
                queue.offer(current.left);
            }
            if (current.right != null) {
                queue.offer(current.right);
            }
        }
    }
}


