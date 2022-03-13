package datastructure.tree.binarytree;

import org.testng.collections.Lists;

import java.util.*;

/**
 * 二叉树层序遍历。给你一个二叉树，请你返回其按 层序遍历 得到的节点值。 （即逐层地，从左到右访问所有节点）。
 * https://leetcode-cn.com/problems/binary-tree-level-order-traversal/
 * 难度：中等
 *
 * 思路：层序遍历，即广度优先搜索，英文叫BFS（Breath First Search).
 *  + 使用Queue。
 *  + 两层循环。外层表示从上到下一层层的循环。内层表示一层内的循环。
 *  + 在遍历第L层开始时，queue中只有第L层的所有节点。随着遍历开始，L层节点出队列，L+1层节点入队列。
 */
public class LevelOrderTraverse {
    /**
     * 方法2，用Queue来实现。
     * 有一个小坑：在层内循环开始时取queue长度作为循环次数。
     */
    static public List<List<Integer>> levelOrder(TreeNode<Integer> root) {
        Queue<TreeNode<Integer>> queue = new ArrayDeque<>();
        if (root != null) {
            queue.add(root);
        }
        List<List<Integer>> result = new ArrayList<>();

        while (!queue.isEmpty()) {
            List<Integer> levelList = new ArrayList<>();
            result.add(levelList);
            // 遍历一层的节点，插入下一层的节点。这里有个坑：在循环开始时取queue长度作为循环次数。
            for (int count = queue.size(); count > 0; count--) {
                TreeNode<Integer> node = queue.poll();
                levelList.add(node.value);
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


    /**
     * 2022.1.3练习。
     * @param root
     * @return
     */
    static public List<List<Integer>> levelOrderTraverse(TreeNode<Integer> root) {
        List<List<Integer>> result = Lists.newArrayList();
        // 初始化queue
        Queue<TreeNode<Integer>> queue = new ArrayDeque<>();
        queue.offer(root);

        while (queue.size() > 0) {
            List<Integer> levelList = Lists.newArrayList();
            result.add(levelList);
            for (int k = queue.size(); k > 0; --k) {
                TreeNode<Integer> node = queue.poll();
                levelList.add(node.value);
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
        }
        return result;
    }

    /**
     * 2022.3.13练习。 改为返回简单的List。
     * 只使用一层循环即可。
     */
    static public List<Integer> levelOrderTraverse3(TreeNode<Integer> root) {
        List<Integer> result = new ArrayList<>();
        Queue<TreeNode<Integer>> levelOrderQueue = new LinkedList<>();
        levelOrderQueue.offer(root);

        while (levelOrderQueue.size() > 0) {
            TreeNode<Integer> node = levelOrderQueue.poll();
            result.add(node.getValue());
            if (null != node.getLeft()) {
                levelOrderQueue.offer(node.getLeft());
            }
            if (null != node.getRight()) {
                levelOrderQueue.offer(node.getRight());
            }
        }
        return result;
    }
}
