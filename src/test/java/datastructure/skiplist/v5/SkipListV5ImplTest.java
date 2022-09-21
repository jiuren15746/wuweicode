package datastructure.skiplist.v5;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

import datastructure.skiplist.v5.impl.SkipListV5Impl;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;

import org.testng.annotations.Test;
import java.util.Scanner;

import java.util.ArrayList;
import java.util.List;


public class SkipListV5ImplTest {

    @Test
    public void getFirst_emptyList() {
        SkipListV5<Object> skipList1 = createSkipList(true);
        assertNull(skipList1.getFirst());

        SkipListV5<Object> skipList2 = createSkipList(false);
        assertNull(skipList2.getFirst());
    }

    @Test
    public void insert_twoSameKey() {
        SkipListV5<Object> skipList1 = createSkipList(true);
        // 插入两个相同的key
        long key = 12345678L;
        assertEquals(skipList1.insert(key, new Object()), true);
        assertEquals(skipList1.insert(key, new Object()), false);

        SkipListV5<Object> skipList2 = createSkipList(false);
        // 插入两个相同的key
        assertEquals(skipList2.insert(key, new Object()), true);
        assertEquals(skipList2.insert(key, new Object()), false);
    }

    @Test
    public void asc_insert_1_to_100() {
        SkipListV5<Object> skipList = createSkipList(true);

        for (long key = 1; key <= 100; ++key) {
            skipList.insert(key, "" + key);
        }
        assertEquals(skipList.size(), 100);

        for (long key = 1; key <= 100; ++key) {
            assertEquals(skipList.getFirst(), "" + key);
            skipList.removeFirst();
        }
        assertEquals(skipList.size(), 0);
    }

    @Test
    public void desc_insert_1_to_100() {
        SkipListV5<Object> skipList = createSkipList(false);

        for (long key = 1; key <= 100; ++key) {
            skipList.insert(key, "" + key);
        }
        assertEquals(skipList.size(), 100);

        for (long key = 100; key >= 1; --key) {
            assertEquals(skipList.getFirst(), "" + key);
            skipList.removeFirst();
        }
        assertEquals(skipList.size(), 0);
    }

    @Test
    public void asc_find() {
        SkipListV5<Object> skipList = createSkipList(true);

        for (long key = 1; key <= 100; ++key) {
            skipList.insert(key, "" + key);
        }
        skipList.print();

        for (long key = 1; key <= 100; ++key) {
            AtomicInteger count = new AtomicInteger();
            assertEquals(skipList.find(key, count), "" + key);
            System.out.println("size=" + skipList.size() + ", findKey=" + key + ", compareCount=" + count.get());
        }

        AtomicInteger count = new AtomicInteger();
        assertEquals(skipList.find(101, count), null);
        System.out.println("size=" + skipList.size() + ", findKey=101, compareCount=" + count.get());
    }

    @Test
    public void asc_insert_1_to_100w() {
        SkipListV5<Object> skipList = createSkipList(true);

        for (long key = 1; key <= 1000000L; ++key) {
            skipList.insert(key, "" + key);
        }
        assertEquals(skipList.size(), 1000000);

        skipList.print();
    }

    @Test
    public void asc_find_1_to_100w() {
        SkipListV5<Object> skipList = createSkipList(true);

        for (long key = 1; key <= 1000000L; ++key) {
            skipList.insert(key, "" + key);
        }

        long start = System.currentTimeMillis();
        long totalCompareCount = 0L;
        int maxCompareCount = 0;

        for (long key = 1; key <= 1000000L; ++key) {
            AtomicInteger count = new AtomicInteger();
            assertEquals(skipList.find(key, count), "" + key);
//            System.out.println("size=" + skipList.size() + ", findKey=" + key + ", compareCount=" + count.get());
            totalCompareCount += count.get();
            maxCompareCount = count.get() > maxCompareCount ? count.get() : maxCompareCount;
        }
        long duration = System.currentTimeMillis() - start;
        System.out.println(duration);
        System.out.println("avg compare count:" + totalCompareCount / 1000000);
        System.out.println("max compare count:" + maxCompareCount);
    }

    private SkipListV5<Object> createSkipList(boolean asc) {
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
        return new SkipListV5Impl<>(comparator);
    }


        public static void main(String[] args) {
            // Scanner input=new Scanner(System.in);
            // String str=input.next();
            System.out.println("hello world");

            Node node15 = new Node(15, null, null);
            Node node7 = new Node(7, null, null);
            Node node20 = new Node(20, node15, node7);
            Node node9 = new Node(9, null, null);
            Node root = new Node(3, node9, node20);

            List<List<Integer>> path = levelOrderTraverse(root);
            System.out.println(path);
        }

        public static List<List<Integer>> levelOrderTraverse(Node root) {
            List<Node> traverseQueue = new ArrayList<>();
            traverseQueue.add(root);
            List<List<Integer>> result = new ArrayList<>();

            for (int level = 0; traverseQueue.size() > 0; level++) {
                boolean forward = (level % 2 == 0);
                List<Integer> inLevelPath = new ArrayList<>();
                for (int i = traverseQueue.size(); i > 0; --i) {
                    Node current = forward ? traverseQueue.remove(0) : traverseQueue.remove(i - 1);
                    if (current.left != null) {
                        traverseQueue.add(current.left);
                    }
                    if (current.right != null) {
                        traverseQueue.add(current.right);
                    }
                    inLevelPath.add(current.value);
                }
                result.add(inLevelPath);
            }
            return result;
        }

        public static class Node {
            private int value;
            private Node left;
            private Node right;

            public Node(int value, Node left, Node right) {
                this.value = value;
                this.left = left;
                this.right = right;
            }

            public String toString() {
                return "" + value;
            }
        }


}
