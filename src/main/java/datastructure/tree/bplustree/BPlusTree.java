package datastructure.tree.bplustree;

import lombok.Getter;

import static org.testng.Assert.assertEquals;


public class BPlusTree<V> {
    @Getter
    private final int maxDegree;

    @Getter
    protected Node root;
    //========

    public BPlusTree(int maxDegree) {
        this.maxDegree = maxDegree;
        this.root = new Node(this, true);
    }

    public void insert(long key, V value) {
        Object[] searchResult = findPosition(key);
        Node leaf = (Node) searchResult[0];
        int pos = (int) searchResult[1];
        leaf.insertAt(pos, key, value);
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



    public static void main(String[] args) {
        final int maxDegree = 4;
        BPlusTree tree = new BPlusTree(maxDegree);
        assertEquals(tree.getRoot().getDegree(), 0);

        long[] array = {8, 20, 10, 6, 30};
        for (long key : array) {
            tree.insert(key, "" + key);
        }

        assertEquals(tree.getRoot().getDegree(), 2);

        // todo 插入3，查找位置
        tree.insert(3, "" + 3);
    }
}
