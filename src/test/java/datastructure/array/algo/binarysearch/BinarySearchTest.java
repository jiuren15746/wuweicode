package datastructure.array.algo.binarysearch;

import static datastructure.array.algo.binarysearch.BinarySearch.searchPos;
import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;


public class BinarySearchTest {

    @Test
    public void test_查找第零个元素() {
        char[] array = new char[]{'a', 'b', 'd', 'h', 'l', 'n'};

        char target = 'a';
        int pos = searchPos(array, 0, array.length - 1, target);
        assertEquals(pos, 0);
    }

    @Test
    public void test_查找最后一个元素() {
        char[] array = new char[]{'a', 'b', 'd', 'h', 'l', 'n'};

        char target = 'n';
        int pos = searchPos(array, 0, array.length - 1, target);
        assertEquals(pos, 5);
    }

    @Test
    public void test_比最大的还大() {
        char[] array = new char[]{'a', 'b', 'd', 'h', 'l', 'n'};

        char target = 'w';
        int pos = searchPos(array, 0, array.length - 1, target);
        assertEquals(pos, 6);
    }

    @Test
    public void test_比最小的还小() {
        char[] array = new char[]{'a', 'b', 'd', 'h', 'l', 'n'};

        char target = 'a' - 1;
        int pos = searchPos(array, 0, array.length - 1, target);
        assertEquals(pos, 0);
    }

    @Test
    public void test_查找中间元素() {
        char[] array = new char[]{'a', 'b', 'd', 'h', 'l', 'n'};

        char target = 'h';
        int pos = searchPos(array, 0, array.length - 1, target);
        assertEquals(pos, 3);
    }

    @Test
    public void test_查找target在中间位置但不存在() {
        char[] array = new char[]{'a', 'b', 'd', 'h', 'l', 'n'};

        char target;
        int pos;

        target = 'k';
        pos = searchPos(array, 0, array.length - 1, target);
        assertEquals(pos, 4);

        target = 'c';
        pos = searchPos(array, 0, array.length - 1, target);
        assertEquals(pos, 2);
    }
}
