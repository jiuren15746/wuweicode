package datastructure.map;

import org.testng.annotations.Test;
import org.testng.collections.Lists;

import static org.testng.Assert.assertEquals;

public class LRUCacheTest {

    @Test
    public void put_key不重复() {
        LRUCache cache = new LRUCache(32);
        for (int key = 0; key < 8; ++key) {
            cache.put(key, "" + key);
        }
        assertEquals(cache.getLruOrderedKeys(), Lists.newArrayList(0,1,2,3,4,5,6,7));
    }

    @Test
    public void put_key重复() {
        LRUCache cache = new LRUCache(32);
        for (int key = 0; key < 8; ++key) {
            cache.put(key, "" + key);
        }
        cache.put(6, "6_1");
        assertEquals(cache.get(6), "6_1");
        assertEquals(cache.getLruOrderedKeys(), Lists.newArrayList(0,1,2,3,4,5,7,6));
    }

    @Test
    public void get() {
        LRUCache cache = new LRUCache(32);
        for (int key = 0; key < 8; ++key) {
            cache.put(key, "" + key);
        }
        assertEquals(cache.get(0), "0");
        assertEquals(cache.get(6), "6");
        assertEquals(cache.get(3), "3");
        assertEquals(cache.getLruOrderedKeys(), Lists.newArrayList(1,2,4,5,7,0,6,3));
    }

    @Test
    public void get_notExist() {
        LRUCache cache = new LRUCache(32);
        for (int key = 0; key < 8; ++key) {
            cache.put(key, "" + key);
        }
        assertEquals(cache.get(100), null);
        assertEquals(cache.get(200), null);
        assertEquals(cache.get(300), null);
        assertEquals(cache.getLruOrderedKeys(), Lists.newArrayList(0,1,2,3,4,5,6,7));
    }

    @Test
    public void put_evict() {
        LRUCache cache = new LRUCache(8);
        for (int key = 0; key < 10; ++key) {
            cache.put(key, "" + key);
        }
        assertEquals(cache.getLruOrderedKeys(), Lists.newArrayList(2,3,4,5,6,7,8,9));
    }

    @Test
    public void remove() {
        LRUCache cache = new LRUCache(8);
        for (int key = 0; key < 8; ++key) {
            cache.put(key, "" + key);
        }
        cache.remove(0);
        cache.remove(1);
        cache.remove(2);

        assertEquals(cache.get(0), null);
        assertEquals(cache.get(1), null);
        assertEquals(cache.get(2), null);

        assertEquals(cache.getLruOrderedKeys(), Lists.newArrayList(3,4,5,6,7));
    }
}
