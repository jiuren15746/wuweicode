package datastructure.array.algo.binarysearch;

import static datastructure.array.algo.binarysearch.BinarySearch.search;
import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;


public class BinarySearchTest {
    @Test
    public void test() {
        char[] array = new char[]{'a', 'b', 'd', 'h', 'l', 'n'};

        char target = 'h';
        int pos = search(array, 0, array.length - 1, target);
        assertEquals(pos, 3);

        target = 'k';
        pos = search(array, 0, array.length - 1, target);
        assertEquals(pos, 4);

        target = 'c';
        pos = search(array, 0, array.length - 1, target);
        assertEquals(pos, 2);

        target = 'w';
        pos = search(array, 0, array.length - 1, target);
        assertEquals(pos, 6);

        target = 'a' - 1;
        pos = search(array, 0, array.length - 1, target);
        assertEquals(pos, 0);
    }
}
