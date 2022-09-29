package datastructure.tree.bplustree;

import lombok.Getter;
import org.testng.collections.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class BPlusTree<V> {
    @Getter
    private final int maxDegree;
    @Getter
    private final int minDegree;

    @Getter
    protected Node root;
    //========

    public BPlusTree(int maxDegree) {
        this.maxDegree = maxDegree;
        this.minDegree = maxDegree / 2;
        this.root = new Node(this, true);
    }

    public void insert(long key, V value) {
        Object[] searchResult = findPosition(key);
        Node leaf = (Node) searchResult[0];
        int pos = (int) searchResult[1];
        leaf.insertAt(pos, key, value);
    }

    public void print() {
        for (List<Node> visitQueue = Lists.newArrayList(root); visitQueue.size() > 0;) {
            StringBuilder sb = new StringBuilder();
            for (int i = visitQueue.size(); i > 0; --i) {
                Node node = visitQueue.remove(0);
                sb.append(node).append("  ");
                if (!node.isLeaf()) {
                    for (int d = 0; d < node.getDegree(); d++) {
                        visitQueue.add((Node)node.getChildrenOrData()[d]);
                    }
                }
            }
            System.out.println("=== " + sb.toString());
        }
    }

    public void checkParentRelationship() {
        checkEachNode(BPlusTree::checkChildRelationship);
    }

    private static void checkChildRelationship(Node node) {
        if (!node.isLeaf()) {
            for (int i = 0; i < node.getDegree(); ++i) {
                Node child = (Node) node.getChildrenOrData()[i];
                assertTrue(child.getParent() == node);
            }
        }
    }

    private void checkEachNode(Consumer<Node> visitNodeLogic) {
        for (List<Node> visitQueue = Lists.newArrayList(root); visitQueue.size() > 0;) {
            for (int i = visitQueue.size(); i > 0; --i) {
                Node node = visitQueue.remove(0);
                if (!node.isLeaf()) {
                    for (int d = 0; d < node.getDegree(); d++) {
                        visitQueue.add((Node)node.getChildrenOrData()[d]);
                    }
                }
                visitNodeLogic.accept(node);
            }
        }
    }

    /**
     * 查找key对应的位置。
     * @param key
     * @return 返回叶子节点以及key在叶子节点的位置或应该插入的位置。
     */
    private Object[] findPosition(long key) {
        Node node = root;
        while (!node.isLeaf()) {
            int pos = node.binarySearch(key);
            pos = pos == 0 ? pos : pos - 1;
            node = (Node) node.getChildrenOrData()[pos];
        }
        int pos = node.getDegree() == 0 ? 0 : node.binarySearch(key);
        return new Object[] {node, pos};
    }

}
