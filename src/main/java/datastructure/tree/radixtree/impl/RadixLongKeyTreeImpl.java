package datastructure.tree.radixtree.impl;

import static org.testng.Assert.assertTrue;

import datastructure.tree.radixtree.RadixLongKeyTree;

/**
 * Radix tree的高度是固定的。对于一个long型key，每6bit为一组，拆分为N组N=11，树的最大高度就为N（包括N层中间节点，一层叶子节点）。
 * 因为每次路由的决策数字有6bit，所以一个中间节点有2^6即64个子节点。
 * 所有值都挂在叶子节点上。
 */
public class RadixLongKeyTreeImpl<V> implements RadixLongKeyTree<V> {

    private static final int MAX_CHILD_SIZE = 64;
    // 中间节点的height范围[0,10]。叶子节点的height=11
    private static final int MAX_HEIGHT = 11;
    private final Node<V> root;
    //========

    private static class Node<V> {
        // 节点高度，根节点高度为0. 实际用byte类型也可以。
        private int height;
        private boolean isLeaf;
        // 如果是中间节点，指向下级节点数组。如果是叶子节点，指向value。
        private Object childrenOrValue;
        //========
        private Node<V> addChild(byte routeKey, V value) {
            assertTrue(!isLeaf);
            Node<V> child = new Node<>();
            child.height = this.height + 1;
            child.isLeaf = (child.height == MAX_HEIGHT);
            child.childrenOrValue = child.isLeaf ? value : new Node[MAX_CHILD_SIZE];
            ((Node[]) this.childrenOrValue)[routeKey] = child;
            System.out.println("add child, routeKey=" + routeKey + ", height=" + child.height);
            return child;
        }
    }

    public RadixLongKeyTreeImpl() {
        root = new Node<>();
        root.height = 0;
        root.isLeaf = false;
        root.childrenOrValue = new Node[MAX_CHILD_SIZE];
    }

    @Override
    public V find(long key) {
        Node<V> current = root;
        for (int i = 0; i < MAX_HEIGHT && null != current; ++i) {
            byte routeKey = (byte) (key & 0x3F);
            current = ((Node[]) current.childrenOrValue)[routeKey];
            key = (key >>> 6);
        }
        return (null != current) ? (V) current.childrenOrValue : null;
    }

    @Override
    public void put(long key, V value) {
        Node<V> current = root;
        for (int i = 0; i < MAX_HEIGHT; ++i) {
            byte routeKey = (byte) (key & 0x3F);
            Node<V> child = ((Node[]) current.childrenOrValue)[routeKey];
            if (null == child) {
                child = current.addChild(routeKey, value);
            }
            current = child;
            key = (key >>> 6);
        }
    }

}
