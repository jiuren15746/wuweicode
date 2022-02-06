package datastructure.skiplist.v2;

import org.testng.annotations.Test;

import java.util.List;
import datastructure.skiplist.v2.SkipList2.*;
import static datastructure.skiplist.v2.SkipListAlgo.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;


public class AlgoTest {

    @Test
    public void testFind_空跳表() {
        SkipList2 skiplist = new SkipList2();

        List<DataNode> path = find(skiplist, 6);
        assertEquals(path.size(), 1);
        assertEquals(path.get(0), skiplist.getTopHead());
    }

    @Test
    public void testInsert_空跳表() {
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

}
