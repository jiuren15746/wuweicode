package datastructure.binarytree;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * 二叉树层序遍历。给你一个二叉树，请你返回其按 层序遍历 得到的节点值。 （即逐层地，从左到右访问所有节点）。
 * https://leetcode-cn.com/problems/binary-tree-level-order-traversal/
 * 难度：中等
 */
public class LevelOrderTraverse {

    // 第一遍自己写的。没有用Queue，用队列+指针实现的。很冗长。
    static public List<List<Integer>> levelOrder(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }

        List<TreeNode> traversePath = new ArrayList<>();
        List<List<Integer>> result = new ArrayList<>();

        traversePath.add(root);
        int levelHead = 0;
        int levelTail = 0;
        int nextLevelHead = levelTail + 1;
        int nextLevelTail = levelTail;

        for (;;) {
            if (levelHead > traversePath.size()-1) {
                break;
            }
            List<Integer> levelList = new ArrayList<>();
            result.add(levelList);

            // 遍历一层的节点，插入下一层的节点
            while (levelHead <= levelTail) {
                // 遍历level层的一个节点
                TreeNode node = traversePath.get(levelHead);
                levelList.add(node.val);
                levelHead++;
                // 插入level+1层的子节点
                if (node.left != null) {
                    traversePath.add(node.left);
                    nextLevelTail++;
                }
                if (node.right != null) {
                    traversePath.add(node.right);
                    nextLevelTail++;
                }
            }
            // 遍历下一层
            levelHead = nextLevelHead;
            levelTail = nextLevelTail;
            nextLevelHead = levelTail + 1;
            nextLevelTail = levelTail;
        }

        return result;
    }

    /**
     * 方法2，用Queue来实现。
     * 有一个小坑：在循环开始时取queue长度作为循环次数。
     */
    static public List<List<Integer>> levelOrder2(TreeNode root) {
        Queue<TreeNode> queue = new ArrayDeque<>();
        if (root != null) {
            queue.add(root);
        }
        List<List<Integer>> result = new ArrayList<>();

        while (!queue.isEmpty()) {
            List<Integer> levelList = new ArrayList<>();
            result.add(levelList);
            // 遍历一层的节点，插入下一层的节点。这里有个坑：在循环开始时取queue长度作为循环次数。
            for (int count = queue.size(); count > 0; count--) {
                // 遍历level层的一个节点
                TreeNode node = queue.poll();
                levelList.add(node.val);
                // 插入level+1层的子节点
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
        }

        return result;
    }

    @Test
    public void test() {
        TreeNode root = new TreeNode(3);
        TreeNode node9 = new TreeNode(9);
        TreeNode node20 = new TreeNode(20);
        TreeNode node15 = new TreeNode(15);
        TreeNode node7 = new TreeNode(7);
        root.left = node9;
        root.right = node20;
        node20.left = node15;
        node20.right = node7;

        List<List<Integer>> result = levelOrder(root);
        System.out.println(result);
        List<List<Integer>> result2 = levelOrder2(root);
        System.out.println(result2);
        Assert.assertEquals(result, result2);
    }
}
