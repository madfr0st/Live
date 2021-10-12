package cf;

import java.util.*;

public class Solution1 {

    static class AVLTree {

        static class Node{
            Node left;
            Node right;
            int data;
            int id;
            int height;
            int size;

            public static Node newNode(int data){
                Node n = new Node();
                n.left = null;
                n.right = null;
                n.data = data;
                n.id = -1;
                n.height = 1;
                n.size = 1;
                return n;
            }

            @Override
            public String toString() {
                return "Node{" +
                        "left=" + left +
                        ", right=" + right +
                        ", data=" + data +
                        ", id=" + id +
                        ", height=" + height +
                        ", size=" + size +
                        '}';
            }
        }
        private Node leftRotate(Node root) {
            Node newRoot = root.right;
            root.right = root.right.left;
            newRoot.left = root;
            root.height = setHeight(root);
            root.size = setSize(root);
            newRoot.height = setHeight(newRoot);
            newRoot.size = setSize(newRoot);
            return newRoot;
        }

        private Node rightRotate(Node root) {
            Node newRoot = root.left;
            root.left = root.left.right;
            newRoot.right = root;
            root.height = setHeight(root);
            root.size = setSize(root);
            newRoot.height = setHeight(newRoot);
            newRoot.size = setSize(newRoot);
            return newRoot;
        }

        private int setHeight(Node root) {
            if (root == null) {
                return 0;
            }
            return 1 + Math.max((root.left != null ? root.left.height : 0), (root.right != null ? root.right.height : 0));
        }

        private int height(Node root) {
            if (root == null) {
                return 0;
            } else {
                return root.height;
            }
        }

        private int setSize(Node root) {
            if (root == null) {
                return 0;
            }
            return 1 + Math.max((root.left != null ? root.left.size : 0), (root.right != null ? root.right.size : 0));
        }

        public Node insert(Node root, int data) {
            if (root == null) {
                return Node.newNode(data);
            }
            if (root.data > data) {
                root.right = insert(root.right, data);
            } else {
                root.left = insert(root.left, data);
            }
            int balance = balance(root.left, root.right);
            if (balance > 1) {
                if (height(root.left.left) >= height(root.left.right)) {
                    root = rightRotate(root);
                } else {
                    root.left = leftRotate(root.left);
                    root = rightRotate(root);
                }
            } else if (balance < -1) {
                if (height(root.right.right) >= height(root.right.left)) {
                    root = leftRotate(root);
                } else {
                    root.right = rightRotate(root.right);
                    root = leftRotate(root);
                }
            } else {
                root.height = setHeight(root);
                root.size = setSize(root);
            }
            return root;
        }

        private int balance(Node rootLeft, Node rootRight) {
            return height(rootLeft) - height(rootRight);
        }

    }
    public List<Integer> countSmaller(int[] nums) {
        ArrayList<Integer> list = new ArrayList<>();

        int size = nums.length;
        AVLTree avlTree = new AVLTree();
        AVLTree.Node root = null;
        for(int i=0;i<size;i++){
            root = avlTree.insert(root,nums[i]);
            System.out.println(root);
        }
        DFS(size,root);

        return list;
    }

    static void DFS(int size, AVLTree.Node node){
        Map<Integer,Integer> map = new HashMap<>();
        DFSutil(node, (HashMap<Integer, Integer>) map);
        System.out.println(map);
    }
    static int DFSutil(AVLTree.Node node,HashMap<Integer,Integer> map){
        int sum = 0;
        if(node.right!=null){
            sum+=DFSutil(node.right,map);
            System.out.println(node);
            System.out.println(sum);
            if(map.containsKey(node.data)){
                map.put(node.data,Math.max(map.get(node.data),sum));
            }
            else {
                map.put(node.data,sum);
            }

        }
        else{
            map.put(node.data,0);
        }
        if(node.left!=null){
            sum+=DFSutil(node.left,map);
        }

        sum++;
        return sum;

    }

    public static void main(String[] args) {

        Solution1 solution1 = new Solution1();
        solution1.countSmaller(new int[]{5,-1,-1,-1});

    }

    static void print(int[] array) {
        for (int j = 0; j < array.length; j++) {
            System.out.print(array[j] + " ");
        }
        System.out.println();
    }

    static void print(int[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }
    }

    static void print(boolean[] array) {
        for (int j = 0; j < array.length; j++) {
            System.out.print(array[j] + " ");
        }
        System.out.println();
    }

    static void print(boolean[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }
    }

    static void print(long[] array) {
        for (int j = 0; j < array.length; j++) {
            System.out.print(array[j] + " ");
        }
        System.out.println();
    }

    static void print(long[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }
    }

    static void print(String[] array) {
        for (int j = 0; j < array.length; j++) {
            System.out.print(array[j] + " ");
        }
        System.out.println();
    }

    static void print(String[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }
    }

}
