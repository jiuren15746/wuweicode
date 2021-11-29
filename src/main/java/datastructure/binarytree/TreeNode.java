package datastructure.binarytree;

public class TreeNode {
    final int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int value) {
        this.val = value;
    }
    TreeNode(int value, TreeNode left, TreeNode right) {
        this.val = value;
        this.left = left;
        this.right = right;
    }
}
