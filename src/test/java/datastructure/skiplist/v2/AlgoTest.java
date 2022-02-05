package datastructure.skiplist.v2;

import org.testng.annotations.Test;

import java.util.List;
import datastructure.skiplist.v2.SkipList2.*;
import static datastructure.skiplist.v2.SkipListAlgo.*;
import static org.testng.Assert.assertEquals;


public class AlgoTest {

    @Test
    public void testFind_空跳表() {
        SkipList2 skiplist = new SkipList2();

        List<DataNode> path = find(skiplist, 6);
        assertEquals(path.size(), 1);
        assertEquals(path.get(0), skiplist.getTopHead());
    }

    @Test
    public void test() {
        DataNode l0_10 = new DataNode(0, 10, null);
        DataNode l0_9 = new DataNode(0, 9, l0_10);
        DataNode l0_8 = new DataNode(0, 8, l0_9);
        DataNode l0_7 = new DataNode(0, 7, l0_8);
        DataNode l0_6 = new DataNode(0, 6, l0_7);
        DataNode l0_4 = new DataNode(0, 4, l0_6);
        DataNode l0_3 = new DataNode(0, 3, l0_4);
        DataNode l0_1 = new DataNode(0, 1, l0_3);
        HeadNode l0_head = new HeadNode(0, l0_1);

        DataNode l1_9 = new DataNode(1, 9, null, l0_9);
        DataNode l1_6 = new DataNode(1, 6, l1_9, l0_6);
        DataNode l1_3 = new DataNode(1, 3, l1_6, l0_3);
        HeadNode l1_head = new HeadNode(1, l1_3, l0_head);

        DataNode l2_9 = new DataNode(2, 9, null, l1_9);
        DataNode l2_6 = new DataNode(2, 6, l2_9, l1_6);
        HeadNode l2_head = new HeadNode(2, l2_6, l1_head);

        SkipList2 skiplist = new SkipList2();
        skiplist.setTopHead(l2_head);

        List<DataNode> path = find(skiplist, 4);
        System.out.println("path for 4: " + path);

        path = find(skiplist, 9);
        System.out.println("path for 9: " + path);

        path = find(skiplist, 100);
        System.out.println("path for 100: " + path);

        path = find(skiplist, -100);
        System.out.println("path for -100: " + path);
    }


    @Test
    public void testInsert_空跳表() {
        SkipList2 skiplist = new SkipList2();

        insert(skiplist, 1);
        insert(skiplist, 2);
        insert(skiplist, 3);
        insert(skiplist, 4);
        insert(skiplist, 5);

        assertEquals(skiplist.getSize(), 5);
    }
}
