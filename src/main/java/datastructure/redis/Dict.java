package datastructure.redis;

import org.testng.Assert;

public class Dict {

    private DictHashTable ht0;
    private DictHashTable ht1;
    private int currentIdx;

    private long rehashIdx;

    public Dict() {
        this.ht0 = new DictHashTable();
        this.ht1 = new DictHashTable();
        this.rehashIdx = -1;
        this.currentIdx = 0;
    }

    public void put(Object key, Object value) {
        if (currentIdx == 0) {
            ht0.put(key, value);
        } else {
            ht1.put(key, value);
        }
    }

    public Object get(Object key) {
        return (currentIdx == 0) ? ht0.get(key) : ht1.get(key);
    }

    public int size() {
        return (currentIdx == 0) ? ht0.size() : ht1.size();
    }


    public void reHash() {
        DictHashTable from = (currentIdx == 0) ? ht0 : ht1;

        DictHashTable to = new DictHashTable(from.size());
        if (currentIdx == 0) {
            ht1 = to;
        } else {
            ht0 = to;
        }

        for (DictHashTable.DictEntry bucket : from.getBuckets()) {
            for (DictHashTable.DictEntry current = bucket; current != null; current = current.getNext()) {
                to.put(current.getKey(), current.getValue());
            }
        }

        from.clear();
        currentIdx = (currentIdx == 0) ? 1 : 0;
    }


    static public void main(String[] args) throws Exception {

        Dict dict = new Dict();

        for (int i = 0; i < 10000; ++i) {
            String key = System.nanoTime() + "";
            String value = key;
            dict.put(key, value);
        }

        dict.put("aaa", "aaa-value");
        dict.put("bbb", "bbb-value");
        dict.put("ccc", "ccc-value");
        dict.put("aaa", "aaa-value1");
        dict.put("bbb", "bbb-value2");
        dict.put("ccc", "ccc-value3");

        Assert.assertEquals(dict.size(), 10003);

        Assert.assertEquals(dict.get("aaa"), "aaa-value1");
        Assert.assertEquals(dict.get("bbb"), "bbb-value2");
        Assert.assertEquals(dict.get("ccc"), "ccc-value3");

        dict.reHash();

        Assert.assertEquals(dict.get("aaa"), "aaa-value1");
        Assert.assertEquals(dict.get("bbb"), "bbb-value2");
        Assert.assertEquals(dict.get("ccc"), "ccc-value3");

        dict.reHash();

        Assert.assertEquals(dict.get("aaa"), "aaa-value1");
        Assert.assertEquals(dict.get("bbb"), "bbb-value2");
        Assert.assertEquals(dict.get("ccc"), "ccc-value3");
    }


}
