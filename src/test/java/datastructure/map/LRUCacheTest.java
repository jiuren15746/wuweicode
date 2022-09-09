package datastructure.map;

import static org.testng.Assert.*;
import org.testng.annotations.Test;
import org.testng.collections.Lists;


public class LRUCacheTest {

    @Test
    public void put_key不重复() {
        LRUCache cache = new LRUCache(32);
        for (int key = 0; key < 8; ++key) {
            cache.put(key, "" + key);
        }

        assertEquals(cache.getLruOrderedKeys(), Lists.newArrayList(0,1,2,3,4,5,6,7));
    }
}
