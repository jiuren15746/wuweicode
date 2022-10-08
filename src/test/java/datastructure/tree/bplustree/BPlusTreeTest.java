package datastructure.tree.bplustree;

import org.testng.annotations.Test;

public class BPlusTreeTest {

    @Test
    public void test_insert_split() {
        final int maxDegree = 4;
        long[] array = {10,15,21,37,44,51,59,63,72,85,91,97};

        BPlusTree tree = new BPlusTree(maxDegree);
        for (long key : array) {
            tree.insert(key, "" + key);
        }
        tree.checkNodeRelationship();
        tree.print();
    }

    @Test
    public void delete_merge() {
        final int maxDegree = 4;
        long[] array = {10,15,21,37,44,51,59,63,72,85,91,97};

        BPlusTree tree = new BPlusTree(maxDegree);
        for (long key : array) {
            tree.insert(key, "" + key);
        }
        tree.checkNodeRelationship();
        tree.print();

        tree.delete(10);
        tree.print();
    }
}
