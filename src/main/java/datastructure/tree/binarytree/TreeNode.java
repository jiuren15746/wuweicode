package datastructure.tree.binarytree;

/**
 * 二叉树节点。
 */
public class TreeNode {
    final int val;
    // 左子树
    TreeNode left;
    // 右子树
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
