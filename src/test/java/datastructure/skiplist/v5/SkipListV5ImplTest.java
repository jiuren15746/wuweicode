package datastructure.skiplist.v5;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

import datastructure.skiplist.v5.impl.SkipListV5Impl;
import java.util.Comparator;
import org.testng.annotations.Test;

public class SkipListV5ImplTest {

    @Test
    public void getFirst_emptyList() {
        SkipListV5<Object> skipList1 = createSkipList(true);
        assertNull(skipList1.getFirst());

        SkipListV5<Object> skipList2 = createSkipList(false);
        assertNull(skipList2.getFirst());
    }

    @Test
    public void insert_twoSameKey() {
        SkipListV5<Object> skipList1 = createSkipList(true);
        // 插入两个相同的key
        long key = 12345678L;
        assertEquals(skipList1.insert(key, new Object()), true);
        assertEquals(skipList1.insert(key, new Object()), false);

        SkipListV5<Object> skipList2 = createSkipList(false);
        // 插入两个相同的key
        assertEquals(skipList2.insert(key, new Object()), true);
        assertEquals(skipList2.insert(key, new Object()), false);
    }

    @Test
    public void asc_insert_1_to_100() {
        SkipListV5<Object> skipList = createSkipList(true);

        for (long key = 1; key <= 100; ++key) {
            skipList.insert(key, "" + key);
        }
        assertEquals(skipList.size(), 100);

        for (long key = 1; key <= 100; ++key) {
            assertEquals(skipList.getFirst(), "" + key);
            skipList.removeFirst();
        }
        assertEquals(skipList.size(), 0);
    }

    @Test
    public void desc_insert_1_to_100() {
        SkipListV5<Object> skipList = createSkipList(false);

        for (long key = 1; key <= 100; ++key) {
            skipList.insert(key, "" + key);
        }
        assertEquals(skipList.size(), 100);

        for (long key = 100; key >= 1; --key) {
            assertEquals(skipList.getFirst(), "" + key);
            skipList.removeFirst();
        }
        assertEquals(skipList.size(), 0);
    }

    @Test
    public void asc_find() {
        SkipListV5<Object> skipList = createSkipList(true);

        for (long key = 1; key <= 100; ++key) {
            skipList.insert(key, "" + key);
        }
        for (long key = 1; key <= 100; ++key) {
            assertEquals(skipList.find(key), "" + key);
        }

        assertEquals(skipList.find(101), null);
    }

    @Test
    public void asc_insert_1_to_100w() {
        SkipListV5<Object> skipList = createSkipList(true);

        for (long key = 1; key <= 1000000L; ++key) {
            skipList.insert(key, "" + key);
        }
        assertEquals(skipList.size(), 1000000);

        skipList.print();
    }

    @Test
    public void asc_find_1_to_100w() {
        SkipListV5<Object> skipList = createSkipList(true);

        for (long key = 1; key <= 1000000L; ++key) {
            skipList.insert(key, "" + key);
        }

        long start = System.currentTimeMillis();
        for (long key = 1; key <= 1000000L; ++key) {
            assertEquals(skipList.find(key), "" + key);
        }
        long duration = System.currentTimeMillis() - start;
        System.out.println(duration);
    }

    private SkipListV5<Object> createSkipList(boolean asc) {
        Comparator<Long> comparator = new Comparator<Long>() {
            @Override
            public int compare(Long o1, Long o2) {
                if (o1.equals(o2)) {
                    return 0;
                } else if (o1 < o2) {
                    return asc ? -1 : 1;
                } else {
                    return asc ? 1 : -1;
                }
            }
        };
        return new SkipListV5Impl<>(comparator);
    }
}
