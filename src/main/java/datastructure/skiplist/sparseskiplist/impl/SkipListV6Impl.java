package datastructure.skiplist.sparseskiplist.impl;

import datastructure.skiplist.sparseskiplist.SkipListV6;
import org.testng.collections.Lists;

import java.util.Comparator;
import java.util.List;

import static org.testng.Assert.assertEquals;

/**
 * 经过测量，maxDegree=256时，插入性能最好。
 * @param <V>
 */
public class SkipListV6Impl<V> implements SkipListV6<V> {

    public static final int MAX_DEGREE = 16;

    /**
     * level从零开始。比如maxLevel=5，则一共有6层。
     */
    public final int maxLevel;

    protected final int maxDegree;

    /**
     * 头结点不存放具体数据。高度等于跳表最大高度。这个非常重要！！！
     */
    private final SkipNodeV6<V> head;

    /**
     * 用于实现升序或降序
     */
    private final Comparator<Long> comparator;

    private long size;
    //========

    public SkipListV6Impl(int maxLevel, int maxDegree, Comparator<Long> comparator) {
        this.maxLevel = maxLevel;
        // todo assert that maxDegree is power of 2
        this.maxDegree = maxDegree;
        this.comparator = comparator;
        this.head = new SkipNodeV6<>(this, maxLevel);
        this.size = 0;
    }

    @Override
    public boolean insert(long key, V value) {
        // 查找插入位置
        List<SkipNodeV6<V>> path = find0(key);

        // 如果插入的key比第一个节点的keys[0]小，path全部都是head节点。如果不做调整，将会在第一个节点前插入一个新节点，新节点只有一个key。
        // 为了让节点包含尽可能多的key，调整path，使插入到第一个节点。
        for (int lv = 0; lv <= maxLevel; ++lv) {
            SkipNodeV6<V> node = path.get(lv);
            if (node == head && node.getNext(lv) != null) {
                path.set(lv, node.getNext(lv));
            }
        }

        SkipNodeV6<V> lastNode = path.get(0);

        if (lastNode == head) {
            SkipNodeV6<V> newNode = new SkipNodeV6<>(this, getRandomLevel());
            newNode.insert(key, value, path);
            newNode.maintainPreNext(path);
        } else {
            lastNode.insert(key, value, path);
        }
        size++;
        return true;
    }

    @Override
    public V delete(long key) {
        return null;
    }

    @Override
    public V find(long key) {
        // 查找插入位置
        List<SkipNodeV6<V>> path = find0(key);
        SkipNodeV6<V> lastNode = path.get(0);
        return lastNode.find(key);
    }

    @Override
    public V insertIfAbsent(long key, V value) {
        return null;
    }

    @Override
    public long size() {
        return size;
    }

    @Override
    public void print() {
        for (int lv = maxLevel; lv >= 0; lv--) {
            System.out.print("===lv" + lv);
            int count = 0;
            for (SkipNodeV6 node = head; node != null; node = node.getNext(lv)) {
                count++;
            }
            System.out.print(", node_count=" + count);

            for (SkipNodeV6 node = head; node != null; node = node.getNext(lv)) {
                System.out.print(" " + node);
            }
            System.out.println("");
        }
    }

    /**
     * 在跳表中查找元素。返回查找路径。
     * 如果跳表中有该节点，返回该节点的查找路径。
     * 如果跳表中没有该节点，返回目标位置的前一个节点的查找路径。
     * @return 节点数组。共 MAX_LEVEL + 1个。[maxLevel]表示最高层找到的节点。[maxLevel-1]表示最高层下一层找到的节点。[0]表示在最底层找到的节点。
     */
    private List<SkipNodeV6<V>> find0(long target) {
        List<SkipNodeV6<V>> path = Lists.newArrayList(maxLevel+1);
        // 这里从逻辑上应该有两层循环：外层从高level向低level循环。内层循环在一个level内向右循环。只是代码上做了一点优化，只用了一层循环来实现。
        SkipNodeV6<V> curNode = head;
        SkipNodeV6<V> nextNode;
        for (int lv = maxLevel; lv >= 0; ) {
            if ((nextNode = curNode.getNext(lv)) != null && compareFirst(nextNode, target) <= 0) {
                // 向右走: 有next节点 && nextNode.keys[0] <= target
                curNode = nextNode;
            } else {
                path.add(0, curNode);
                lv--;
            }
        }
        return path;
    }

    /**
     * 拿节点的头元素与target比较。
     */
    private int compareFirst(SkipNodeV6<V> node, long target) {
        return node == head ? -1 : comparator.compare(node.keys[0], target);
    }

    /**
     * 拿节点的最后一个元素与target比较。
     */
    private int compareLast(SkipNodeV6<V> node, long target) {
        return node == head ? -1 : comparator.compare(node.keys[node.size - 1], target);
    }

    /**
     * 尽量使得五个节点才能晋升一个。
     */
    protected int getRandomLevel() {
        int level = 0;
        for (; level < maxLevel && (System.nanoTime() & 0xFF) % 5 == 0; level++) {
        }
        return level;
    }


    public static void main(String[] args) {
//        SkipListV6<Object> skipList = createSkipList(true);
//        for (long i = 0; i < 100; ++i) {
//            long key = (long) (Math.random() * 10000L);
//            skipList.insert(key, "" + key);
//            Assert.assertEquals(skipList.find(key), "" + key);
//        }
//        skipList.print();

        SkipListV6<Object> skipList = createSkipList(true);

        long start = System.currentTimeMillis();
        for (long key = 1; key <= 1000000L; ++key) {
            skipList.insert(key, "" + key);
        }
        System.out.println((System.currentTimeMillis() - start) + " ms");
        assertEquals(skipList.size(), 1000000);

//        skipList.print();

//        SkipListV6<Object> skipList = createSkipList(true);
//        skipList.insert(200, "" + 200);
//        skipList.insert(100, "" + 100);
//        skipList.print();

//        assertEquals(skipList.size(), 100);
//
//        for (long key = 1; key <= 100; ++key) {
//            assertEquals(skipList.getFirst(), "" + key);
//            skipList.removeFirst();
//        }
//        assertEquals(skipList.size(), 0);
    }


    private static SkipListV6<Object> createSkipList(boolean asc) {
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
        return new SkipListV6Impl<>(5, 16, comparator);
    }
}
