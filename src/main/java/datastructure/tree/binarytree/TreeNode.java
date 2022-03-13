package datastructure.tree.binarytree;

import lombok.Data;

/**
 * 二叉树节点。
 */
@Data
public class TreeNode<T> {
    final T value;
    // 左子树
    TreeNode<T> left;
    // 右子树
    TreeNode<T> right;

    TreeNode(T value) {
        this.value = value;
    }
    TreeNode(T value, TreeNode left, TreeNode right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }
}
