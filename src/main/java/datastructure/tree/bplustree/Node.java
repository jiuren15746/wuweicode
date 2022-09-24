package datastructure.tree.bplustree;

import lombok.Data;

/**
 * 中间节点和叶子节点结构相同。
 */
@Data
class Node {
    private final BPlusTree tree;
    private final boolean isLeaf;

//    private final int maxDegree;
//    private final int minDegree;
    /**
     * degree表示有多少个孩子? todo
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

    /**
     * 在array指定长度范围内，二分查找value所在的位置。如果value不在数组中，返回应该插入的位置。start和end表示搜索范围，左右都包含。
     * 返回的位置可能在数组下标范围之外。
     */
    public int binarySearch(long target) {
        int start = 0;
        int end = degree - 1;

        while (start <= end) {
            // 每次调整范围后，target与新范围的左右边界值比较，非常重要！！！
            if (target <= keys[start]) {
                return start;
            }
            if (target > keys[end]) {
                return end + 1;
            }
            int midPos = (start + end) >> 1;
            long diff = target - keys[midPos];
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

    /**
     *
     * @param key
     * @param value
     */
    public void insert(long key, Object value) {
        if (degree == 0) {
            keys[0] = key;
            childrenOrData[0] = value;
            degree++;
            return;
        }

        final int pos = binarySearch(key);

        // 插入到结尾后面
        if (pos == degree) {
            keys[degree] = key;
            childrenOrData[degree] = value;
        } else {
            System.arraycopy(keys, pos, keys, pos + 1, degree - pos);
            System.arraycopy(childrenOrData, pos, childrenOrData, pos + 1, degree - pos);
            keys[pos] = key;
            childrenOrData[pos] = value;
        }
        degree++;

        splitIfNecessary();
    }


    private void splitIfNecessary() {
        if (degree <= tree.getMaxDegree()) {
            return;
        }

        int newDegree = degree >> 1;
        int newNodeDegree = degree - newDegree;

        // split new Node
        Node newNode = new Node(tree, isLeaf);
        System.arraycopy(keys, newDegree, newNode.keys, 0, newNodeDegree);
        System.arraycopy(childrenOrData, newDegree, newNode.childrenOrData, 0, newNodeDegree);
        newNode.degree = newNodeDegree;
        this.degree = newDegree;

        // 维护pre, next, parent
        this.next = newNode;
        newNode.pre = this;
        newNode.parent = parent;

        if (null == parent) {
            newNode.parent = parent = new Node(tree, false);
            tree.root = parent;
            parent.insert(keys[0], this);
        }
        parent.insert(newNode.keys[0], newNode);
    }

}
