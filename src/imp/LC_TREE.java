package imp;

import javax.imageio.IIOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Queue;

public class LC_TREE {

    static int preIndex = 0;

    static BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
    public static void main(String[] args) throws IIOException {
        Integer[] arr = {-10, 9, 20, null, null, 15, 7};
        TreeNode root = buildTree(arr);

        System.out.println("Inorder traversal of built tree:");
        printLevelOrder(root);  // Expected: 9 -10 15 20 7


    }
    public static int maxPathSum(TreeNode root) {

        return 0;
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
            System.out.println("null");
            return;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode current = queue.poll();

            if (current == null) {
                System.out.print("null ");
                continue; // don’t enqueue children of null
            }

            System.out.print(current.val + " ");
            queue.offer(current.left);   // may be null
            queue.offer(current.right);  // may be null
        }
    }
    public static TreeNode buildTree(Integer[] levelOrder) {
        if (levelOrder == null || levelOrder.length == 0 || levelOrder[0] == null) {
            return null;
        }

        TreeNode root = new TreeNode(levelOrder[0]);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        int i = 1;
        while (!queue.isEmpty() && i < levelOrder.length) {
            TreeNode current = queue.poll();

            // Left child
            if (i < levelOrder.length && levelOrder[i] != null) {
                current.left = new TreeNode(levelOrder[i]);
                queue.add(current.left);
            }
            i++;

            // Right child
            if (i < levelOrder.length && levelOrder[i] != null) {
                current.right = new TreeNode(levelOrder[i]);
                queue.add(current.right);
            }
            i++;
        }

        return root;
    }
}


