package leetcode;

import javax.imageio.IIOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class LC98 {

    static BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
    public static void main(String[] args) throws IIOException {

        TreeNode node = new TreeNode(5);
        node.left = new TreeNode(1);
//        node.left.left = new TreeNode(6);
        node.right = new TreeNode(4);
        node.right.left = new TreeNode(3);
        node.right.right = new TreeNode(6);

        System.out.println(isValidBST(node));


    }
    public static boolean isValidBST(TreeNode root) {
        ArrayList<Integer> list = new ArrayList<>();
        dfsUtil(list,root);
//         System.out.println(list);
        for(int i=1;i<list.size();i++){
            if(list.get(i-1)>list.get(i)){
                return false;
            }
        }
        return true;
    }

    public static void dfsUtil( ArrayList<Integer> list,TreeNode root) {
        if(root!=null){
            if(root.left!=null){
                dfsUtil(list,root.left);
            }
            list.add(root.val);

            if(root.right!=null){
                dfsUtil(list,root.right);
            }
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


