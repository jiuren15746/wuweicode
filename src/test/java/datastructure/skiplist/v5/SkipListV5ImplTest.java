package datastructure.skiplist.v5;

import static org.testng.Assert.*;
import datastructure.skiplist.v5.impl.SkipListV5Impl;
import java.util.Comparator;
import org.testng.annotations.Test;

public class SkipListV5ImplTest {

    @Test
    public void test_asc() {
        Comparator<Long> comparator = new Comparator<Long>() {
            @Override
            public int compare(Long o1, Long o2) {
                if (o1.equals(o2)) {
                    return 0;
                } else if (o1 < o2) {
                    return -1;
                } else {
                    return 1;
                }
            }
        };

        SkipListV5<Object> skipList = new SkipListV5Impl<>(comparator);

        assertNull(skipList.getFirst());

        // 插入两个相同的key
        long key = 12345678L;
        assertEquals(skipList.insert(key, new Object()), true);
        assertEquals(skipList.insert(key, new Object()), false);
    }

    @Test
    public void test_desc() {

    }
}
