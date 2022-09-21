package datastructure.tree.bplustree;
import datastructure.tree.bplustree.Node.*;

public class BPlusTree {

    int m;
    InternalNode root;
    LeafNode firstLeaf;
    //========

    public BPlusTree(int m) {
        this.m = m;
        this.root = null;
    }
}
