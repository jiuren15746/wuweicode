package datastructure.skiplist.v5.impl;

import datastructure.skiplist.v5.SkipListV5;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.testng.collections.Lists;


public class SkipListV5Impl<V> implements SkipListV5<V> {
    /**
     * 最高层的level=9. 一共有10层.
     */
    public final int maxLevel;

    /**
     * 头结点不存放具体数据。高度等于跳表最大高度。这个非常重要！！！
     */
    private final Node<V> head;

    /**
     * 用于实现升序或降序
     */
    private final Comparator<Long> comparator;

    private long size;
    //========

    public SkipListV5Impl(Comparator<Long> comparator) {
        this.maxLevel = 9;
        this.comparator = comparator;
        this.head = new Node<>(-1, null, maxLevel);
    }

    public SkipListV5Impl(int maxLevel, Comparator<Long> comparator) {
        this.maxLevel = maxLevel;
        this.comparator = comparator;
        this.head = new Node<>(-1, null, maxLevel);
    }

    @Override
    public V getFirst() {
        Node<V> firstNode = head.getNext(0);
        return firstNode != null ? firstNode.value : null;
    }

    @Override
    public void removeFirst() {
        Node<V> firstNode = head.getNext(0);
        if (null == firstNode) {
            return;
        }
        for (int i = 0; i <= firstNode.getLevel(); i++) {
            head.setNext(i, firstNode.getNext(i));
        }
        size--;
    }

    @Override
    public V find(long key, AtomicInteger counter) {
        List<Node<V>> path = find0(key, counter);
        Node<V> lastNode = path.get(path.size() - 1);
        return lastNode.key == key ? lastNode.value : null;
    }

    @Override
    public boolean insert(long key, V value) {
        // 查找插入位置
        List<Node<V>> path = find0(key, null);
        Node<V> lastNode = path.get(path.size() - 1);

        if (lastNode.key == key) {
            return false;
        } else {
            insert0(key, value, path);
            return true;
        }
    }

    @Override
    public V insertIfAbsent(long key, V value) {
        // 查找插入位置
        List<Node<V>> path = find0(key, null);
        Node<V> lastNode = path.get(path.size() - 1);

        if (lastNode.key == key) {
            return lastNode.value;
        } else {
            insert0(key, value, path);
            return value;
        }
    }

    @Override
    public long size() {
        return size;
    }

    @Override
    public void print() {
        for (int lv = maxLevel; lv >= 0; lv--) {
            long count = 0;
            for (Node<V> current = head.getNext(lv); current != null; current = current.getNext(lv)) {
                count++;
            }
            System.out.println("At level=" + lv + ", list size = " + count);
        }
    }

    /**
     * 在跳表中查找元素。返回查找路径。
     * 如果跳表中有该节点，返回该节点的查找路径。
     * 如果跳表中没有该节点，返回目标位置的前一个节点的查找路径。
     * @return 每层返回一个节点。共 MAX_LEVEL + 1个。
     */
    private List<Node<V>> find0(long target, AtomicInteger counter) {
        List<Node<V>> path = Lists.newArrayList();
        // 这里从逻辑上应该有两层循环：外层从高level向低level循环。内层循环在一个level内向右循环。只是代码上做了一点优化，只用了一层循环来实现。
        Node<V> curNode = head;
        Node<V> nextNode;
        for (int lv = maxLevel; lv >= 0; ) {
            // 向右走
            if ((nextNode = curNode.getNext(lv)) != null && compare(nextNode, target, counter) <= 0) {
                curNode = nextNode;
            } else {
                // 走不动了，转下一层
                path.add(curNode);
                lv--;
            }
        }
        return path;
    }

    /**
     * 创建节点并插入
     */
    private void insert0(long key, V value, List<Node<V>> path) {
        int level = getRandomLevel();
        Node<V> newNode = new Node<>(key, value, level);
        // 维护每一层的链表
        for (int i = 0; i <= level; ++i) {
            Node<V> pre = path.get(path.size() - 1 - i);
            Node<V> next = pre.getNext(i);
            pre.setNext(i, newNode);
            newNode.setNext(i, next);
            newNode.setPre(i, pre);
            if (next != null) {
                next.setPre(i, newNode);
            }
        }
        size++;
    }

    /**
     * 尽量使得五个节点才能晋升一个。
     */
    private int getRandomLevel() {
        int level = 0;
        while ((System.nanoTime() & 0xFF) % 5 == 0 && level < maxLevel) {
            level++;
        }
        return level;
    }

    private int compare(Node<V> node, long target, AtomicInteger counter) {
        if (node == this.head) {
            return -1;
        } else {
            if (null != counter) {
                counter.incrementAndGet();
            }
            return comparator.compare(node.key, target);
        }
    }
}
