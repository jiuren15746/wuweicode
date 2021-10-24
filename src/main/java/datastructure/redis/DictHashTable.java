package datastructure.redis;

import lombok.Data;
import org.testng.Assert;

import java.util.Objects;

/**
 * Hash table 实现。s
 */
public class DictHashTable {

    static public int INITIAL_SIZE = 4;

    private DictEntry[] buckets;
    // number of buckets
    private long bucketSize;
    private long sizeMask;
    // number of entries
    private long size;

    public DictHashTable() {
        this.buckets = new DictEntry[INITIAL_SIZE];
        this.bucketSize = INITIAL_SIZE;
        this.sizeMask = INITIAL_SIZE - 1;
        this.size = 0;
    }

    @Data
    static class DictEntry {
        private final Object key;
        private Object value;
        private DictEntry next;

        public DictEntry(Object key, Object value) {
            this.key = key;
            this.value = value;
        }
    }


    public void put(Object key, Object value) {
        Assert.assertNotNull(key, "key is null");
        Assert.assertNotNull(value, "value is null");

        int idx = (int) (key.hashCode() & sizeMask);
        DictEntry head = buckets[idx];

        DictEntry node = findNode(head, key);
        if (null != node) {
            // simply update
            node.setValue(value);
        } else {
            // insert at head
            DictEntry entry = new DictEntry(key, value);
            entry.setNext(head);
            buckets[idx] = entry;
        }
        size++;
    }

    /**
     * 查找指定key对应的value。
     */
    public Object get(Object key) {
        int idx = (int) (key.hashCode() & sizeMask);
        DictEntry head = buckets[idx];
        DictEntry node = findNode(head, key);
        if (null != node) {
            return node.getValue();
        } else {
            return null;
        }
    }

    /**
     * 在head指向的bucket内，查找key对应的entry。
     * 如果bucket为空，即head==null，返回null。
     * 如果能找到，返回entry和其前一个entry。
     * 如果找不到，返回null。
     */
    static private DictEntry findNode(DictEntry head, Object key) {
        for (DictEntry current = head; current != null; current = current.getNext()) {
            if (current.getKey().hashCode() == key.hashCode()
                    && Objects.equals(current.getKey(), key)) {
                return current;
            }
        }
        return null;
    }


    static public void main(String[] args) throws Exception {

        DictHashTable ht = new DictHashTable();

        for (int i = 0; i < 10000; ++i) {
            String key = System.nanoTime() + "";
            String value = key;
            ht.put(key, value);
        }

        ht.put("aaa", "aaa-value");
        ht.put("bbb", "bbb-value");
        ht.put("ccc", "ccc-value");

        ht.put("aaa", "aaa-value1");
        ht.put("bbb", "bbb-value2");
        ht.put("ccc", "ccc-value3");

        ht.put(null, "abc");
        Thread.sleep(Integer.MAX_VALUE);
    }
}
