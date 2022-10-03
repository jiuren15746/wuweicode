package datastructure.skiplist.sparseskiplist;

import datastructure.skiplist.sparseskiplist.impl.SkipListV6Impl;
import org.testng.annotations.Test;

import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;

import static org.testng.Assert.assertEquals;

public class SkipListV6ImplTest {

    @Test
    public void asc_find_1_to_100w() {
        int[] maxDegrees = {16, 32, 64, 128, 256, 512};
        for (int maxDegree : maxDegrees) {
            SkipListV6<Object> skipList = createSkipList(true, maxDegree);

            for (long key = 1; key <= 1000000L; ++key) {
                skipList.insert(key, "" + key);
            }

            long start = System.currentTimeMillis();
            for (long key = 1; key <= 1000000L; ++key) {
                assertEquals(skipList.find(key), "" + key);
            }
            long duration = System.currentTimeMillis() - start;
            System.out.println("maxDegree=" + maxDegree + ", " + duration + " ms");
        }
    }

    private static SkipListV6<Object> createSkipList(boolean asc, int maxDegree) {
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
        return new SkipListV6Impl<>(5, maxDegree, comparator);
    }
}
