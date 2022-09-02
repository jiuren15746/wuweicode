package datastructure.skiplist.v5.impl;

import datastructure.skiplist.v5.SkipListV5;
import java.util.Comparator;
import java.util.List;
import org.testng.collections.Lists;


public class SkipListV5Impl<V> implements SkipListV5<V> {
    /**
     * 最高层的level=9. 一共有10层.
     */
    static public final int MAX_LEVEL = 9;

    /**
     * 头结点不存放具体的数据。高度等于跳表最大高度。这个非常重要！！！
     */
    private final Node<V> head;

    private final Comparator<Long> comparator;

    private long size;

    //========

    public SkipListV5Impl(Comparator<Long> comparator) {
        this.comparator = comparator;
        this.head = new Node<>(-1, null, MAX_LEVEL);
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
    public void insert(long key, V value) {
        // 查找插入位置
        List<Node<V>> path = find(key);
        // 创建节点并插入
        int level = getRandomLevel();
        Node<V> newNode = new Node<>(key, value, level);
        // 维护每一层的链表
        for (int i = 0; i <= level; ++i) {
            Node<V> pre = path.get(path.size() - 1 - i);
            Node<V> next = pre.getNext(i);
            pre.setNext(level, newNode);
            newNode.setNext(level, next);
        }
        size++;
    }

    @Override
    public long getSize() {
        return size;
    }

    /**
     * 在跳表中查找元素。返回查找路径。
     * 如果跳表中有该节点，返回该节点的查找路径。
     * 如果跳表中没有该节点，返回目标位置的前一个节点的查找路径。
     * @return 每层返回一个节点。共 MAX_LEVEL + 1个。
     */
    private List<Node<V>> find(long target) {
        List<Node<V>> path = Lists.newArrayList();
        // 这里从逻辑上应该有两层循环：外层从高level向低level循环。内层循环在一个level内向右循环。只是代码上做了一点优化，只用了一层循环来实现。
        Node<V> curNode = head;
        Node<V> nextNode = null;
        for (int lv = MAX_LEVEL; lv >= 0; ) {
            // 向右走. 条件：curNode<target && nextNode非空 && nextNode<=target
            boolean forward = (curNode == head || comparator.compare(curNode.key, target) < 0)
                    && (nextNode = curNode.getNext(lv)) != null
                    && comparator.compare(nextNode.key, target) <= 0;
            if (forward) {
                curNode = nextNode;
            } else { // 向右走不动了，转下一层
                path.add(curNode);
                lv--;
            }
        }
        return path;
    }

    /**
     * 尽量使得五个节点才能晋升一个。
     */
    private int getRandomLevel() {
        int level = 0;
        while ((System.nanoTime() & 0xFF) % 5 == 0 && level < MAX_LEVEL) {
            level++;
        }
        return level;
    }
}
