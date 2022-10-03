package datastructure.tree.bplustree;

import lombok.Getter;
import org.testng.collections.Lists;

import java.util.List;
import java.util.function.Consumer;

import static org.testng.Assert.assertTrue;

/**
 * B+树实现。
 * 要点：
 *  + 节点degree介于 [minDegree, maxDegree]之间
 *  + 父子节点之间双向引用的维护。
 *  + 对于中间节点，某些情况下需要对二分查找的下标做调整
 *  + 如果节点degree>maxDegree，对节点进行拆分
 *  + 如果节点degree<minDegree，从sibling节点借数据，或者与sibling节点合并
 *
 * @param <V>
 * @author wuwei
 */
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
        this.minDegree = (int) Math.ceil(maxDegree / 2.0);
        this.root = new Node(this, true);
    }

    public void insert(long key, V value) {
        System.out.println("Insert " + key);
        Object[] searchResult = findPosition(key);
        Node leaf = (Node) searchResult[0];
        int pos = (int) searchResult[1];
        leaf.insertAt(pos, key, value);
    }

    public V delete(long key) {
        System.out.println("Delete " + key);
        Object[] searchResult = findPosition(key);
        Node leaf = (Node) searchResult[0];
        int pos = (int) searchResult[1];

        if (leaf.getKeys()[pos] == key) {
            return (V) leaf.deleteAt(pos);
        } else {
            return null;
        }
    }

    /**
     * 查找key对应的叶子节点以及节点内位置。
     * @param key
     * @return 返回叶子节点以及key在叶子节点的位置或应该插入的位置。
     */
    private Object[] findPosition(long key) {
        Node node = root;
        while (!node.isLeaf()) {
            int pos = node.binarySearch(key);
            // !!!对于中间节点，某些情况下需要对二分查找的下标做调整
            pos = (node.getKeys()[pos] == key || pos == 0) ? pos : pos - 1;
            node = (Node) node.getChildrenOrData()[pos];
        }
        int pos = node.getDegree() == 0 ? 0 : node.binarySearch(key);
        return new Object[] {node, pos};
    }

    /**
     * 层次遍历，打印每个节点
     */
    public void print() {
        System.out.println("=== print tree");
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
            System.out.println("=== " + sb);
        }
    }

    public void checkNodeRelationship() {
        checkEachNode(BPlusTree::checkNodeRelationship);
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

    private static void checkNodeRelationship(Node node) {
        // check children relationship
        if (!node.isLeaf()) {
            for (int i = 0; i < node.getDegree(); ++i) {
                Node child = (Node) node.getChildrenOrData()[i];
                assertTrue(child.getParent() == node);
            }
        }
        // check pre/next relationship
        assertTrue(node.getPre() == null || node.getPre().getNext() == node);
        assertTrue(node.getNext() == null || node.getNext().getPre() == node);
    }

}
