package datastructure.tree.radixtree.impl;

import datastructure.tree.radixtree.RadixLongKeyTree;

import static org.testng.Assert.assertTrue;

/**
 * Radix tree的高度是固定的。对于一个long型key，每6bit为一组，拆分为N组N=11，树的最大高度就为N-1。
 * 因为每次路由的决策数字有6bit，所以一个中间节点有2^6即64个子节点。
 * 所有值都挂在叶子节点上。
 *
 * Radix tree的特点：
 * + radix tree不保存key值。
 * + 不需要B+树的平衡操作，比如节点拆分、节点合并。
 *
 * 经过测试，100w个key的tree，Radix tree查找100w个key。只需要60ms。比起skipList好非常多。
 *
 * todo 叶子节点以双向链表串联
 * todo 保存最小和最大叶子节点
 * @author wuwei
 */
public class RadixLongKeyTreeImpl<V> implements RadixLongKeyTree<V> {

    private static class Node<V> {
        private boolean isLeaf;

        /**
         * 节点高度，根节点高度为0。叶子节点高度为10。用于截图long类型key的二进制中的一段。
         */
        private int height;

        /**
         * 如果是中间节点，指向下级节点数组。如果是叶子节点，指向value。
         */
        private Object[] values;
        //========

        public Node(int height) {
            this.height = height;
            this.isLeaf = (height == MAX_HEIGHT);
            values = new Object[isLeaf ? 16 : 64];
        }

        /**
         * 用于给中间节点创建下级节点。
         * @param routeKey
         * @return
         */
        private Node<V> addChild(byte routeKey) {
            assertTrue(!isLeaf);
            Node<V> child = new Node<>(height + 1);
            values[routeKey] = child;
            return child;
        }
    }

    /**
     * 根节点height为零。向下递增。叶子节点的height=10
     */
    private static final int MAX_HEIGHT = 10;

    private final Node<V> root;
    //========

    public RadixLongKeyTreeImpl() {
        root = new Node<>(0);
    }

    @Override
    public V find(long key) {
        Node<V> leaf = findLeafNode(key);
        byte routeKey = (byte) (key & 0x0F);
        return (V) leaf.values[routeKey];
    }

    @Override
    public void put(long key, V value) {
        Node<V> leaf = findLeafNode(key);
        byte routeKey = (byte) (key & 0x0F);
        leaf.values[routeKey] = value;
    }

    private Node<V> findLeafNode(long key) {
        Node<V> current = root;
        for (int i = 0; i < MAX_HEIGHT; ++i) {
            long shiftKey = key >> ((64 - (i + 1) * 6));
            byte routeKey = (byte)(shiftKey & 0x3F);
            current = (null != current.values[routeKey]) ? (Node)current.values[routeKey] : current.addChild(routeKey);
        }
        return current;
    }

}
