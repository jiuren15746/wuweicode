package datastructure.skiplist.v2;

import datastructure.skiplist.v2.SkipListInterface.DataNode;
import datastructure.skiplist.v2.SkipListInterface.HeadNode;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;

public class SkipList2Test {

    @Test
    public void testFind_空跳表() {
        SkipList2 skiplist = new SkipList2();
        assertEquals(skiplist.getTopLevel(), 0);
    }

    @Test
    public void testInsert() {
        SkipList2 skiplist = new SkipList2();
        skiplist.insert(1);
        skiplist.insert(2);
        skiplist.insert(3);
        skiplist.insert(4);
        skiplist.insert(5);
        skiplist.insert(8);
        skiplist.insert(9);
        skiplist.insert(10);

        assertEquals(skiplist.getSize(), 8);
    }

    @Test
    public void testFind() {
        SkipList2 skiplist = new SkipList2();
        skiplist.insert(1);
        skiplist.insert(2);
        skiplist.insert(3);
        skiplist.insert(4);
        skiplist.insert(5);
        skiplist.insert(8);
        skiplist.insert(9);
        skiplist.insert(10);

        assertEquals(skiplist.getSize(), 8);

        // 查找存在的数字
        List<DataNode> path = skiplist.find(9);
        System.out.println("path for 9: " + path);

        // 查找不存在的数字，最后的位置
        path = skiplist.find(100);
        System.out.println("path for 100: " + path);
        assertEquals(path.get(path.size() - 1).getValue().intValue(), 10);

        // 查找不存在的数字，中间位置
        path = skiplist.find(7);
        System.out.println("path for 7: " + path);
        assertEquals(path.get(path.size() - 1).getValue().intValue(), 5);

        // 查找不存在的数字，开头位置
        path = skiplist.find(-1);
        System.out.println("path for -1: " + path);
        for (DataNode node : path) {
            assertTrue(node instanceof HeadNode);
        }
    }

    @Test
    public void testDelete() {
        SkipList2 skiplist = new SkipList2();
        skiplist.insert(1);
        skiplist.insert(2);
        skiplist.insert(3);
        skiplist.insert(4);
        skiplist.insert(5);
        skiplist.insert(8);
        skiplist.insert(9);
        skiplist.insert(10);

        assertEquals(skiplist.getSize(), 8);

        assertFalse(skiplist.delete(6));

        // 删除8，然后再查找
        assertTrue(skiplist.delete(8));
        List<DataNode> path = skiplist.find(8);
        assertEquals(path.get(path.size() - 1).getValue().intValue(), 5);
    }

    @Test
    public void test插入然后清空() {
        SkipList2 skiplist = new SkipList2();

        for (int i = 1; i <= 100; ++i) {
            skiplist.insert(i);
        }
        int level = skiplist.getTopLevel();

        for (int i = 1; i <= 100; ++i) {
            skiplist.delete(i);
        }
        assertEquals(skiplist.getSize(), 0);
        // 删除所有节点后，level不变
        assertEquals(skiplist.getTopLevel(), level);
    }
}
