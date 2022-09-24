//package datastructure.tree.radixtree.impl;
//
//import lombok.Data;
//import lombok.Getter;
//import lombok.Setter;
//
///**
// * @author winston
// */
//public class Node {
//    /**
//     * 树的高度为11. 范围[0,10]
//     */
//    public static final int MAX_HEIGHT = 10;
//
//    /**
//     * 节点高度，根节点高度为0.
//     */
//    @Getter
//    protected final int height;
//
//    public Node(int height) {
//        this.height = height;
//    }
//}
//
//class LeafNode<V> extends Node {
//    @Getter
//    @Setter
//    private V value;
//
//    @Getter
//    @Setter
//    private LeafNode<V> pre;
//
//    @Getter
//    @Setter
//    private LeafNode<V> next;
//    //========
//
//    public LeafNode(int height, V value) {
//        super(height);
//        this.value = value;
//    }
//}
//
//class InternalNode<V> extends Node {
//    private static final int MAX_CHILD_SIZE = 64;
//    /**
//     * 指向下级节点数组
//     */
//    private final Node[] children;
//    //========
//
//    public InternalNode(int height) {
//        super(height);
//        children = new Node[MAX_CHILD_SIZE];
//    }
//
//    Node addChild(byte routeKey, V value) {
//        if (height + 1 == MAX_HEIGHT) {
//
//        }
//
//        RadixLongKeyTreeImpl.Node<V> child = new RadixLongKeyTreeImpl.Node<>();
//        child.height = this.height + 1;
//        boolean isLeaf = (child.height == MAX_HEIGHT);
//        child.childrenOrValue = child.isLeaf ? value : new RadixLongKeyTreeImpl.Node[MAX_CHILD_SIZE];
//        ((RadixLongKeyTreeImpl.Node[]) this.childrenOrValue)[routeKey] = child;
//        System.out.println("add child, routeKey=" + routeKey + ", height=" + child.height);
//        return child;
//    }
//}
