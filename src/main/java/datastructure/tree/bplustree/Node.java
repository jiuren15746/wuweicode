package datastructure.tree.bplustree;

import lombok.Data;

/**
 * 中间节点和叶子节点结构相同。
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

    private void onFirstKeyChanged(long oldKey, long newKey) {
        for (Node node = parent; node != null;) {
            int pos = node.binarySearch(oldKey);
            node.keys[pos] = newKey;
            node = pos == 0 ? node.parent : null;
        }
    }

    private void splitIfNecessary() {
        if (degree <= tree.getMaxDegree()) {
            return;
        }

        // split new Node
        int newDegree = degree >> 1;
        int newNodeDegree = degree - newDegree;
        Node newNode = new Node(tree, isLeaf);
        System.arraycopy(keys, newDegree, newNode.keys, 0, newNodeDegree);
        System.arraycopy(childrenOrData, newDegree, newNode.childrenOrData, 0, newNodeDegree);
        newNode.degree = newNodeDegree;
        this.degree = newDegree;

        // populate parent relationship
        if (null == parent) {
            newNode.parent = parent = new Node(tree, false);
            tree.root = parent;
            parent.insert(keys[0], this);
        }
        parent.insert(newNode.keys[0], newNode);
        newNode.parent = parent;

        // 维护pre, next, parent
        this.next = newNode;
        newNode.pre = this;
    }

    protected int binarySearch(long target) {
        return binarySearch2(keys, 0, degree - 1, target);
    }

    /**
     * 在array指定长度范围内，二分查找target所在位置或应该插入的位置。start和end表示搜索范围，左右都包含。
     * 返回的位置可能在数组下标范围之外。
     */
    protected static int binarySearch2(long[] array, int start, int end, long target) {
        while (start <= end) {
            // 每次调整范围后，target与新范围的左右边界值比较，非常重要！！！
            if (target <= array[start]) {
                return start;
            }
            if (target > array[end]) {
                return end + 1;
            }
            int midPos = (start + end) >> 1;
            long diff = target - array[midPos];
            if (diff == 0) {
                return midPos;
            } else if (diff > 0) {
                start = midPos + 1;
            } else {
                end = midPos - 1;
            }
        }
        throw new RuntimeException("Impossible");
    }

}
