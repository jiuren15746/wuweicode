package datastructure.tree.binarytree;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static datastructure.tree.binarytree.LevelOrderTraverse.levelOrder;
import static datastructure.tree.binarytree.LevelOrderTraverse.levelOrderTraverse;


public class LevelOrderTraverseTest {

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
        List<List<Integer>> result2 = levelOrderTraverse(root);
        System.out.println(result2);
        Assert.assertEquals(result, result2);
    }
}
