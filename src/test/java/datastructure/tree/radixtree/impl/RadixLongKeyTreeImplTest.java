package datastructure.tree.radixtree.impl;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

public class RadixLongKeyTreeImplTest {
    private RadixLongKeyTreeImpl<String> tree = new RadixLongKeyTreeImpl<>();

    @Test
    public void testPutAndFind_KeyGreaterThanZero() {
        long key = 2;
        tree.put(key, "" + key);
        assertEquals(tree.find(key), "2");
    }

    @Test
    public void testPutAndFind_KeyLessThanZero() {
        long key = -2;
        tree.put(key, "" + key);
        assertEquals(tree.find(key), "-2");
    }
}
