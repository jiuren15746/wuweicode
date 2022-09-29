package datastructure.tree.bplustree;

import org.testng.annotations.Test;

public class BPlusTreeTest {

    @Test
    public void test_split() {

        long[] array = {10,15,21,37,44,51,59,63,72,85,91,97};

        final int maxDegree = 3;
        BPlusTree tree = new BPlusTree(maxDegree);

        for (long key : array) {
            System.out.println("Insert " + key);
            tree.insert(key, "" + key);
            tree.checkParentRelationship();
        }
//        tree.print();
//
//        tree.insert(40, "40");
//        tree.insert(41, "41");
//        tree.print();
    }
}
