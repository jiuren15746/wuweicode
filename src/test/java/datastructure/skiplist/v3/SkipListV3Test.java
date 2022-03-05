package datastructure.skiplist.v3;

import datastructure.skiplist.v3.SkipListInterface3.SkipNode;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;

public class SkipListV3Test {

    @Test
    public void testFind() {
        SkipNode n1 = new SkipNode(1);
        SkipNode n3 = new SkipNode(3);
        SkipNode n5 = new SkipNode(5);
        SkipNode n7 = new SkipNode(7);
        SkipNode n9 = new SkipNode(9);
        SkipNode head = new SkipNode(Integer.MIN_VALUE);

        n1.next = new SkipNode[] {n3, n5, n9};
        n3.next = new SkipNode[] {n5};
        n5.next = new SkipNode[] {n7, n9};
        n7.next = new SkipNode[] {n9};
        n9.next = new SkipNode[] {null, null, null};
        head.next = new SkipNode[] {n1, n1, n1};

        SkipListV3 skipList = new SkipListV3(head);

        // find
        int target = 7;
        List<SkipNode> path = skipList.find(target);
        assertEquals(path.size(), 3);
        assertEquals(path.get(0).value, 1);
        assertEquals(path.get(1).value, 5);
        assertEquals(path.get(2).value, 7);

        target = 4;
        path = skipList.find(target);
        assertEquals(path.size(), 3);
        assertEquals(path.get(0).value, 1);
        assertEquals(path.get(1).value, 1);
        assertEquals(path.get(2).value, 3);

        target = -100;
        path = skipList.find(target);
        assertEquals(path.size(), 3);
        assertEquals(path.get(0).value, Integer.MIN_VALUE);
        assertEquals(path.get(1).value, Integer.MIN_VALUE);
        assertEquals(path.get(2).value, Integer.MIN_VALUE);
    }
}
