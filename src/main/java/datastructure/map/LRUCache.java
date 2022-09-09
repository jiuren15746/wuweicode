package datastructure.map;

import org.testng.collections.Lists;

import java.util.HashMap;
import java.util.List;

public class LRUCache {
    /**
     * !!! LRUNode作为map的value。同时又通过pre和next维护双向链表。
     * 有些实现采用独立的hashmap和LinkedList。因为LinkedList.remove()的时间复杂度为O(n)。会导致整体复杂度上升为O(n).
     */
    private static class LRUNode {
        private final int key;
        private Object value;
        private LRUNode pre;
        private LRUNode next;
        LRUNode(int key, Object value) {
            this.key = key;
            this.value = value;
        }
        @Override
        public String toString() {
            return "(" + key + "," + value + ")";
        }
    }

    private HashMap<Integer, LRUNode> map;
    // head指向最近最少访问的节点. tail指向最近刚刚访问的节点
    private LRUNode lruHead;
    private LRUNode lruTail;
    private int capacity;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>(capacity);
        // 双向链表有一个dummy节点，不用修改head指针，编码简单很多
        this.lruHead = this.lruTail = new LRUNode(-1, null);
    }

    public void put(int key, Object value) {
        // 先插入
        LRUNode node = new LRUNode(key, value);
        LRUNode existNode = map.put(key, node);
        appendNode(node);
        if (existNode != null) {
            removeNode(existNode);
        }
        // 再evict
        if (map.size() > capacity) {
            LRUNode tobeDeleted = lruHead.next;
            removeNode(tobeDeleted);
            map.remove(tobeDeleted.key);
            System.out.println("evict element, key=" + tobeDeleted.key);
        }
    }

    /**
     * get的时间复杂度为O(1).
     */
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
        // tail指针调整
        if (node == lruTail) {
            lruTail = node.pre;
        }
    }

    private void appendNode(LRUNode node) {
        lruTail.next = node;
        // 这一步很关键，漏掉会导致bug
        node.next = null;
        node.pre = lruTail;
        // tail指针调整
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
