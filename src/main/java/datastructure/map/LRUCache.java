package datastructure.map;

import org.testng.collections.Lists;

import java.util.HashMap;
import java.util.List;

public class LRUCache {

    private static class LRUNode {
        private final int key;
        private Object value;
        private LRUNode pre;
        private LRUNode next;
        LRUNode(int key, Object value) {
            this.key = key;
            this.value = value;
        }
    }

    private HashMap<Integer, LRUNode> map;
    private LRUNode lruHead;
    private LRUNode lruTail;
    private int capacity;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>(capacity);
        this.lruHead = this.lruTail = new LRUNode(-1, null);
    }

    public void put(int key, Object value) {
        // 先插入
        LRUNode node = new LRUNode(key, value);
        appendNode(node);
        LRUNode existNode = map.put(key, node);
        if (existNode != null) {
            removeNode(existNode);
        }
        // 再evict
        if (map.size() + 1 > capacity) {
            LRUNode tobeDeleted = lruHead.next;
            removeNode(tobeDeleted);
            map.remove(tobeDeleted.key);
            System.out.println("evict element, key=" + tobeDeleted.key);
        }
    }

    public Object get(int key) {
        LRUNode node = map.get(key);
        if (node != null) {
            removeNode(node);
            appendNode(node);
        }
        return node != null ? node.value : null;
    }

    public Object remove(int key) {
        LRUNode node = map.remove(key);
        if (node != null) {
            removeNode(node);
        }
        return node != null ? node.value : null;
    }

    private void removeNode(LRUNode node) {
        node.pre.next = node.next;
        if (node.next != null) {
            node.next.pre = node.pre;
        }
    }

    private void appendNode(LRUNode node) {
        lruTail.next = node;
        node.pre = lruTail;
        lruTail = node;
    }

    protected List<Integer> getLruOrderedKeys() {
        List<Integer> keys = Lists.newArrayList(map.size());
        for (LRUNode node = lruHead.next; node != null; node = node.next) {
            keys.add(node.key);
        }
        return keys;
    }
}
