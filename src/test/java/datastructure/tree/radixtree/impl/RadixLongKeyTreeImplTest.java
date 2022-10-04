package datastructure.tree.radixtree.impl;

import static org.testng.Assert.assertEquals;

import datastructure.skiplist.sparseskiplist.SkipListV6;
import org.testng.annotations.Test;

public class RadixLongKeyTreeImplTest {
    private RadixLongKeyTreeImpl<String> tree = new RadixLongKeyTreeImpl<>();

    @Test
    public void testFind_100w() {
        for (int i = 0; i < 5; ++i) {
            RadixLongKeyTreeImpl<String> tree = new RadixLongKeyTreeImpl<>();

            for (long key = 1; key <= 1000000L; ++key) {
                tree.put(key, "" + key);
            }

            long start = System.currentTimeMillis();
            for (long key = 1; key <= 1000000L; ++key) {
                assertEquals(tree.find(key), "" + key);
            }
            long duration = System.currentTimeMillis() - start;
            System.out.println("duration=" + duration + " ms");
        }
    }


    @Test
    public void testPutAndFind_KeyGreaterThanZero() {
        long key = System.nanoTime();
        System.out.println("key=" + key + ", binary=" + longToBinary(key));
        tree.put(key, "" + key);
        assertEquals(tree.find(key), "" + key);
    }

    @Test
    public void testPutAndFind_KeyLessThanZero() {
        long key = -2;
        tree.put(key, "" + key);
        assertEquals(tree.find(key), "-2");
    }

    @Test
    public void testPutAndFind_zero() {
        long key = 0;
        tree.put(key, "" + key);
        assertEquals(tree.find(key), "0");
    }

    @Test
    public void testPutAndFind_min() {
        long key = Long.MIN_VALUE;
        tree.put(key, "" + key);
        assertEquals(tree.find(key), "" + Long.MIN_VALUE);
    }

    @Test
    public void testPutAndFind_max() {
        long key = Long.MAX_VALUE;
        tree.put(key, "" + key);
        assertEquals(tree.find(key), "" + Long.MAX_VALUE);
    }

    private static String longToBinary(long key) {
        String str = Long.toBinaryString(key);
        for (int i = 64 - str.length(); i >= 1; --i) {
            str = "0" + str;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); ++i) {
            if (i > 0 && i % 6 == 0) {
                sb.append(",");
            }
            sb.append(str.charAt(i));
        }
        return sb.toString();
    }
}
