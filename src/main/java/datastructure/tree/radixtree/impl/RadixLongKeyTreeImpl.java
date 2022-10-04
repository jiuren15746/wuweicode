package datastructure.tree.radixtree.impl;

import datastructure.tree.radixtree.RadixLongKeyTree;

import static org.testng.Assert.assertTrue;

/**
 * Radix tree的高度是固定的。对于一个long型key，每6bit为一组，拆分为N组N=11，树的最大高度就为N（包括N层中间节点，一层叶子节点）。
 * 因为每次路由的决策数字有6bit，所以一个中间节点有2^6即64个子节点。
 * 所有值都挂在叶子节点上。
 *
 * todo 叶子节点以双向链表串联
 * todo 保存最小和最大叶子节点
 */
public class RadixLongKeyTreeImpl<V> implements RadixLongKeyTree<V> {

    private static final int MAX_CHILD_SIZE = 64;

    /**
     * 根节点height为零。向下递增。叶子节点的height=10
     */
    private static final int MAX_HEIGHT = 10;

    private final Node<V> root;
    //========

    private static class Node<V> {

        private boolean isLeaf;

        /**
         * 节点高度，根节点高度为0. 实际用byte类型也可以。用于截图long类型key的二进制中的一段。
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
            Node<V> child = new Node<>(this.height + 1);
            values[routeKey] = child;
            System.out.println("add child, routeKey=" + routeKey + ", height=" + child.height);
            return child;
        }
    }

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
            byte routeKey = (byte)((key >> (64 - (i + 1) * 6)) & 0x3F);
            current = (null != current.values[routeKey]) ? (Node)current.values[routeKey] : current.addChild(routeKey);
        }
        return current;
    }

}
