package datastructure.tree.bplustree;

import datastructure.array.algo.binarysearch.BinarySearch;
import lombok.Data;

/**
 * 中间节点和叶子节点结构相同。
 * 要点：
 * # 父子节点之间有双向引用。
 */
@Data
class BPlusNode {
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

    private BPlusNode pre;
    private BPlusNode next;
    private BPlusNode parent;
    //========

    public BPlusNode(BPlusTree tree, boolean isLeaf) {
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

    protected Object delete(long key) {
        int pos = degree == 0 ? 0 : binarySearch(key);
        return deleteAt(pos);
    }

    /**
     * 在指定位置插入key和value。
     * @param pos 插入位置下标
     * @param key
     * @param value 如果是中间节点，value类型为Node；否则value是其他数据类型。
     */
    protected void insertAt(int pos, long key, Object value) {
        System.out.println("insert " + key + " to " + this);

        // insert after tail
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

        // populate child relationship
        if (!isLeaf) {
            ((BPlusNode)value).parent = this;
        }

        splitIfNecessary();
    }

    /**
     * 在指定位置删除key。
     * @param pos 位置下标
     */
    protected Object deleteAt(int pos) {
        long oldKey = keys[pos];
        Object oldValue = childrenOrData[pos];

        // delete data
        if (pos == degree - 1) {
            // delete at tail
            keys[degree - 1] = 0;
            childrenOrData[degree - 1] = null;
        } else {
            System.arraycopy(keys, pos + 1, keys, pos, degree - pos - 1);
            System.arraycopy(childrenOrData, pos + 1, childrenOrData, pos, degree - pos - 1);
            if (pos == 0) {
                onFirstKeyChanged(oldKey, keys[0]);
            }
        }
        degree--;

        borrowOrMergeIfNecessary();
        return oldValue;
    }

    /**
     * 因为每个节点的第一个key晋升作为parent的key。所以节点第一个key变更后，要更新父节点的key。
     * @param oldKey
     * @param newKey
     */
    private void onFirstKeyChanged(long oldKey, long newKey) {
        for (BPlusNode node = parent; node != null;) {
            int pos = node.binarySearch(oldKey);
            node.keys[pos] = newKey;
            node = pos == 0 ? node.parent : null;
        }
    }

    /**
     * 如果节点degree>maxDegree，对节点进行拆分。
     */
    private void splitIfNecessary() {
        if (degree <= tree.getMaxDegree()) {
            return;
        }

        System.out.print("Before split: " + this);

        // new sibling Node
        int newDegree = degree >> 1;
        int newNodeDegree = degree - newDegree;
        BPlusNode siblingNode = new BPlusNode(tree, isLeaf);
        System.arraycopy(keys, newDegree, siblingNode.keys, 0, newNodeDegree);
        System.arraycopy(childrenOrData, newDegree, siblingNode.childrenOrData, 0, newNodeDegree);
        siblingNode.degree = newNodeDegree;
        this.degree = newDegree;

        // populate pre/next
        siblingNode.pre = this;
        siblingNode.next = this.next;
        this.next = siblingNode;

        // populate parent relationship
        if (null == parent) {
            tree.root = new BPlusNode(tree, false);
            tree.root.insert(keys[0], this);
        }
        parent.insert(siblingNode.keys[0], siblingNode);

        // populate children
        for (int i = degree; i < keys.length; ++i) {
            keys[i] = 0;
            if (!isLeaf) {
                ((BPlusNode) childrenOrData[i]).parent = siblingNode;
            }
            childrenOrData[i] = null;
        }

        System.out.println(", after split: " + this + ", " + siblingNode);
    }

    /**
     * 如果节点degree<minDegree，对节点进行拆分。
     */
    private void borrowOrMergeIfNecessary() {
        if (degree >= tree.getMinDegree()) {
            return;
        }

        // try borrow one data from sibling
        if (borrowFromSibling()) {
            return;
        }

        // merge with sibling
        BPlusNode sibling;
        if ((sibling = leftSibling()) != null) {
            mergeToSibling(sibling, sibling.degree);

        } else if ((sibling = rightSibling()) != null) {
            mergeToSibling(sibling, 0);
        }
    }

    private boolean borrowFromSibling() {
        if (pre != null && pre.parent == parent && pre.degree > tree.getMinDegree()) {
            int preIdx = pre.keys.length - 1;
            insertAt(0, pre.keys[preIdx], pre.childrenOrData[preIdx]);
            pre.deleteAt(preIdx);
            return true;
        }
        else if (next != null && next.parent == parent && next.degree > tree.getMinDegree()) {
            int nextIdx = 0;
            insertAt(degree, next.keys[nextIdx], next.childrenOrData[nextIdx]);
            next.deleteAt(nextIdx);
            return true;
        }
        return false;
    }

    private void mergeToSibling(BPlusNode sibling, int siblingIdx) {
        // merge
        System.arraycopy(keys, 0, sibling.keys, siblingIdx, degree);
        System.arraycopy(childrenOrData, 0, sibling.childrenOrData, siblingIdx, degree);
        sibling.degree += degree;

        // populate pre/next
        if (sibling == pre) {
            sibling.next = next;
            if (next != null) {
                next.pre = sibling;
            }
        } else {
            sibling.pre = pre;
            if (pre != null) {
                pre.next = sibling;
            }
        }
        pre = next = null;

        // populate children
        if (!isLeaf) {
            for (BPlusNode child : (BPlusNode[]) childrenOrData) {
                child.parent = sibling;
            }
        }

        // delete index from parent
        parent.delete(keys[0]);
    }

    private BPlusNode leftSibling() {
        return pre != null && pre.parent == this.parent ? pre : null;
    }
    private BPlusNode rightSibling() {
        return next != null && next.parent == this.parent ? next : null;
    }

    protected int binarySearch(long target) {
        return BinarySearch.binarySearch(keys, 0, degree - 1, target);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(isLeaf ? "L[" : "[");
        for (int i = 0; i < degree; ++i) {
            sb.append(keys[i]);
            if (i < degree - 1) {
                sb.append(",");
            }
        }
        sb.append("]");
        return sb.toString();
    }

}
