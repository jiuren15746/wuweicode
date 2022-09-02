package datastructure.skiplist.v5;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

import datastructure.skiplist.v5.impl.SkipListV5Impl;
import java.util.Comparator;
import org.testng.annotations.Test;

public class SkipListV5ImplTest {

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

    @Test
    public void getFirst_emptyList() {
        SkipListV5<Object> skipList1 = createSkipList(true);
        assertNull(skipList1.getFirst());

        SkipListV5<Object> skipList2 = createSkipList(false);
        assertNull(skipList2.getFirst());



        // 插入1-100
    }

    @Test
    public void insert_twoSameKey() {
        SkipListV5<Object> skipList1 = createSkipList(true);
        // 插入两个相同的key
        long key = 12345678L;
        assertEquals(skipList1.insert(key, new Object()), true);
        assertEquals(skipList1.insert(key, new Object()), false);
    }

    @Test
    public void insert_1_to_100() {
        SkipListV5<Object> skipList1 = createSkipList(true);

        for (long key = 1; key <= 100; ++key) {
            skipList1.insert(key, "" + key);
        }
        assertEquals(skipList1.size(), 100);

        for (long key = 1; key <= 100; ++key) {
            assertEquals(skipList1.getFirst(), "" + key);
            skipList1.removeFirst();
        }
        assertEquals(skipList1.size(), 0);
    }


    @Test
    public void test_desc() {

    }
}
