package datastructure.tree.bplustree;

import datastructure.array.algo.binarysearch.BinarySearch;
import lombok.Data;

/**
 * 中间节点和叶子节点结构相同。
 * 要点：
 * # 父子节点之间有双向引用。
 */
@Data
class Node {
    private final BPlusTree tree;
    private final boolean isLeaf;

    /**
     * degree表示有多少个孩子
     */
    private int degree;

    private long[] keys;
    /**
     * 如果是中间节点，元素类型为Node。如果是叶子节点，元素类型是Object。
     */
    private Object[] childrenOrData;

    private Node pre;
    private Node next;
    private Node parent;
    //========

    public Node(BPlusTree tree, boolean isLeaf) {
        this.tree = tree;
        this.isLeaf = isLeaf;
        this.degree = 0;
        // maxDegree+1是为了便于split
        this.keys = new long[tree.getMaxDegree() + 1];
        this.childrenOrData = new Object[tree.getMaxDegree() + 1];
    }

    protected void insert(long key, Object value) {
        int pos = degree == 0 ? 0 : binarySearch(key);
        insertAt(pos, key, value);
    }

    /**
     * 在指定位置插入key和value。
     * @param pos 插入位置下标
     * @param key
     * @param value
     */
    protected void insertAt(int pos, long key, Object value) {
        System.out.println("insert " + key + " to " + this);

        if (pos == degree) {
            keys[degree] = key;
            childrenOrData[degree] = value;
        } else {
            System.arraycopy(keys, pos, keys, pos + 1, degree - pos);
            System.arraycopy(childrenOrData, pos, childrenOrData, pos + 1, degree - pos);
            long oldKey = keys[pos];
            keys[pos] = key;
            childrenOrData[pos] = value;
            if (pos == 0) {
                onFirstKeyChanged(oldKey, key);
            }
        }
        degree++;

        splitIfNecessary();
    }

    /**
     * 因为每个节点的第一个key晋升作为parent的key。所以节点第一个key变更后，要更新父节点的key。
     * @param oldKey
     * @param newKey
     */
    private void onFirstKeyChanged(long oldKey, long newKey) {
        for (Node node = parent; node != null;) {
            int pos = node.binarySearch(oldKey);
            node.keys[pos] = newKey;
            node = pos == 0 ? node.parent : null;
        }
    }

    /**
     * 如果节点degree>=maxDegree，对节点进行拆分。
     */
    private void splitIfNecessary() {
        if (degree <= tree.getMaxDegree()) {
            return;
        }

        System.out.print("Before split: " + this);

        // split new Node
        int newDegree = degree >> 1;
        int newNodeDegree = degree - newDegree;
        Node newNode = new Node(tree, isLeaf);
        System.arraycopy(keys, newDegree, newNode.keys, 0, newNodeDegree);
        System.arraycopy(childrenOrData, newDegree, newNode.childrenOrData, 0, newNodeDegree);
        newNode.degree = newNodeDegree;
        this.degree = newDegree;

        // populate children
        for (int i = degree; i < keys.length; ++i) {
            keys[i] = 0;
            if (!isLeaf) {
                ((Node) childrenOrData[i]).parent = newNode;
            }
            childrenOrData[i] = null;
        }

        System.out.println(", after split: " + this + ", " + newNode);

        // populate parent relationship
        if (null == parent) {
            newNode.parent = parent = new Node(tree, false);
            tree.root = parent;
            parent.insert(keys[0], this);
        }
        parent.insert(newNode.keys[0], newNode);
        newNode.parent = parent;

        // 维护pre, next
        newNode.pre = this;
        newNode.next = this.next;
        this.next = newNode;
    }

    protected int binarySearch(long target) {
        return BinarySearch.binarySearch(keys, 0, degree - 1, target);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(isLeaf ? "L[" : "[");
        for (int i = 0; i < degree; ++i) {
            sb.append(keys[i]).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("]");
        return sb.toString();
    }

}
