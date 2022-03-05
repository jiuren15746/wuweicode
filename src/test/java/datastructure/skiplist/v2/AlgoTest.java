package datastructure.skiplist.v2;

import datastructure.skiplist.v2.Nodes.DataNode;
import datastructure.skiplist.v2.Nodes.HeadNode;
import org.testng.annotations.Test;

import java.util.List;

import static datastructure.skiplist.v2.SkipListAlgo.*;
import static org.testng.Assert.*;


public class AlgoTest {

    @Test
    public void testFind_空跳表() {
        SkipList2 skiplist = new SkipList2();

        List<DataNode> path = find(skiplist, 6);
        assertEquals(path.size(), 1);
        assertEquals(path.get(0), skiplist.getTopHead());
    }

    @Test
    public void testInsert() {
        SkipList2 skiplist = new SkipList2();
        insert(skiplist, 1);
        insert(skiplist, 2);
        insert(skiplist, 3);
        insert(skiplist, 4);
        insert(skiplist, 5);
        insert(skiplist, 8);
        insert(skiplist, 9);
        insert(skiplist, 10);

        assertEquals(skiplist.getSize(), 8);

        // 查找存在的数字
        List<DataNode> path = find(skiplist, 9);
        System.out.println("path for 9: " + path);

        // 查找不存在的数字，最后的位置
        path = find(skiplist, 100);
        System.out.println("path for 100: " + path);
        assertEquals(path.get(path.size() - 1).getValue().intValue(), 10);

        // 查找不存在的数字，中间位置
        path = find(skiplist, 7);
        System.out.println("path for 7: " + path);
        assertEquals(path.get(path.size() - 1).getValue().intValue(), 5);

        // 查找不存在的数字，开头位置
        path = find(skiplist, -1);
        System.out.println("path for -1: " + path);
        for (DataNode node : path) {
            assertTrue(node instanceof HeadNode);
        }
    }

    @Test
    public void testDelete() {
        SkipList2 skiplist = new SkipList2();
        insert(skiplist, 1);
        insert(skiplist, 2);
        insert(skiplist, 3);
        insert(skiplist, 4);
        insert(skiplist, 5);
        insert(skiplist, 8);
        insert(skiplist, 9);
        insert(skiplist, 10);

        assertEquals(skiplist.getSize(), 8);

        assertFalse(delete(skiplist, 6));

        // 删除8，然后再查找
        assertTrue(delete(skiplist, 8));
        List<DataNode> path = find(skiplist, 8);
        assertEquals(path.get(path.size() - 1).getValue().intValue(), 5);
    }

    @Test
    public void test插入然后清空() {
        SkipList2 skiplist = new SkipList2();

        for (int i = 1; i <= 100; ++i) {
            insert(skiplist, i);
        }
        int level = skiplist.getLevel();

        for (int i = 1; i <= 100; ++i) {
            delete(skiplist, i);
        }
        assertEquals(skiplist.getSize(), 0);
        // 删除所有节点后，level不变
        assertEquals(skiplist.getLevel(), level);
    }
}
