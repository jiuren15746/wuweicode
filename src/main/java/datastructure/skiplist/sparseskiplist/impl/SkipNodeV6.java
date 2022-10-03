package datastructure.skiplist.sparseskiplist.impl;

import datastructure.array.algo.binarysearch.BinarySearch;

import java.util.List;

/**
 * 松散跳表的节点定义。与普通跳表不同，松散跳表每个节点内保存一组连续key以及一组连续value。
 * https://www.modb.pro/db/498374
 * @param <V>
 */
class SkipNodeV6<V> {

    private SkipListV6Impl<V> skipList;

    /**
     * 一组连续递增key。
     * 头结点的keys和values没有意义。
     */
    protected final long[] keys;
    protected final Object[] values;
    protected int size;

    /**
     * 多层next指针和pre指针
     */
    protected SkipNodeV6<V>[] next;
    protected SkipNodeV6<V>[] pre;
    //========

    /**
     *
     * @param level level从零开始。比如level=5，那么pre/next数组长度应该为6.
     */
    public SkipNodeV6(SkipListV6Impl<V> skipList, int level) {
        this.skipList = skipList;
        this.size = 0;
        // keys长度=maxDegree+1，为了便于插入后再分裂
        this.keys = new long[skipList.maxDegree + 1];
        this.values = new Object[skipList.maxDegree + 1];

        this.next = new SkipNodeV6[level + 1];
        this.pre = new SkipNodeV6[level + 1];
    }

    /**
     * 插入key和value。
     * 如果已有key该怎么办？返回false。
     * @param key
     * @param value
     * @param path
     */
    protected boolean insert(long key, V value, List<SkipNodeV6<V>> path) {
        // 先插入
        if (size == 0) {
            keys[0] = key;
            values[0] = value;
        } else {
            int pos = BinarySearch.binarySearch(keys, 0, size - 1, key);
            if (keys[pos] == key) {
                return false;
            }
            // 不是在末尾插入，move elements to make space for key
            if (pos != size) {
                System.arraycopy(keys, pos, keys, pos + 1, size - pos);
                System.arraycopy(values, pos, values, pos + 1, size - pos);
            }
            keys[pos] = key;
            values[pos] = value;
        }
        size++;

        splitIfNecessary(path);
        return true;
    }

    protected V delete(long key) {
        int pos = BinarySearch.binarySearch(keys, 0, size - 1, key);
        if (keys[pos] != key) {
            return null;
        }
        V result = (V) values[pos];
        if (pos == size - 1) {
            keys[pos] = 0;
            values[pos] = null;
        } else {
            System.arraycopy(keys, pos + 1, keys, pos, size - pos - 1);
            System.arraycopy(values, pos + 1, values, pos, size - pos - 1);
        }
        size--;
        return result;
    }

    protected V find(long key) {
        int pos = BinarySearch.binarySearch(keys, 0, size - 1, key);
        if (keys[pos] == key) {
            return (V) values[pos];
        } else {
            return null;
        }
    }

    /**
     * 维护每一层的pre/next
     */
    protected void maintainPreNext(List<SkipNodeV6<V>> path) {
        for (int lv = 0; lv <= getLevel(); ++lv) {
            SkipNodeV6<V> pre = path.get(lv);
            SkipNodeV6<V> next = pre.getNext(lv);
            this.setPre(lv, pre);
            this.setNext(lv, next);
            pre.setNext(lv, this);
            if (next != null) {
                next.setPre(lv, this);
            }
        }
    }

    private void splitIfNecessary(List<SkipNodeV6<V>> path) {
        if (size <= skipList.maxDegree) {
            return;
        }
        // 指向分裂的起始位置
        int pos = size >> 1;
        SkipNodeV6 newNode = new SkipNodeV6(skipList, skipList.getRandomLevel());
        System.arraycopy(keys, pos, newNode.keys, 0, size - pos);
        System.arraycopy(values, pos, newNode.values, 0, size - pos);
        newNode.size = size - pos;
        this.size = pos;
        newNode.maintainPreNext(path);
    }

    public SkipNodeV6<V> getNext(int level) {
        return level < next.length ? next[level] : null;
    }
    public SkipNodeV6<V> getPre(int level) {
        return level < pre.length ? pre[level] : null;
    }

    public void setNext(int level, SkipNodeV6<V> nextNode) {
        next[level] = nextNode;
    }
    public void setPre(int level, SkipNodeV6<V> preNode) {
        pre[level] = preNode;
    }

    /**
     * 返回该节点Level. 最底层level为0.
     */
    public int getLevel() {
        return next.length - 1;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SkipNode(" + size + ")[");
        for (int i = 0; i < size; ++i) {
            sb.append(keys[i]);
            if (i < size - 1) {
                sb.append(",");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
