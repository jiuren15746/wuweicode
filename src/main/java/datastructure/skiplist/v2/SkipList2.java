package datastructure.skiplist.v2;

import java.util.ArrayList;
import java.util.List;

/**
 * 跳表的数据结构。最底层level为零，向上递增。
 *
 * 首先看一下为什么需要skiplist这种数据结构？现在有这么一个应用场景，
 * 我们需要持续有序地存储一些数据，并且满足下面的需要：1.查找；2.插入。
 * 这两个是最具有代表性的操作，为什么这么说呢？如果需要查找的效率尽量高，
 * 那么就应该使用线性存储（比如数组，查找复杂度O(1) ）；
 * 而频繁插入则应该使用链式存储（比如链表，插入复杂度O(1) ）。
 * 如果要把这两种结合起来的话，一种还不错的选择是二叉查找树（BST）。
 * 但是树本身有很多问题，比如如何保持平衡，如何动态调整。这个时候skiplist就出现了。
 * 简单来说，skiplist是一种可以满足树的大多数应用场景，同时又比树这种结构实现简单的数据结构。
 * 那么skiplist到底是什么样的呢？继续往下看。
 */
public class SkipList2 implements SkipListInterface {
    // 最顶层的头结点
    protected SkipListInterface.HeadNode topHead;

    public SkipList2() {
        topHead = new HeadNode(0);
    }

    /**
     * 返回最顶层的level
     */
    public int getTopLevel() {
        return topHead.getLevel();
    }

    /**
     * 返回元素个数
     */
    public int getSize() {
        int size = 0;
        for (DataNode head = getHead(0); head.getNext() != null; head = head.getNext()) {
            size++;
        }
        return size;
    }

    /**
     * 返回指定level的头结点
     */
    private HeadNode getHead(int level) {
        if (level > topHead.getLevel()) {
            throw new IllegalArgumentException("level > skiplist.level");
        }
        HeadNode head = topHead;
        while (head.getLevel() > level) {
            head = (HeadNode) head.getDown();
        }
        return head;
    }

    @Override
    public List<DataNode> find(final int target) {
        List<DataNode> path = new ArrayList<>();

        // 两层循环。外层循环用于不同level。内层循环在level内搜索。
        for (DataNode ptr = topHead; ptr != null;) {
            // 注意内层跳出条件
            while (true) {
                if (ptr.getValue() != null && ptr.getValue() == target) { // 相等
                    break;
                }
                // （节点值为null或小于target），且（下个节点为null或下个节点值大于target）
                if ((ptr.getValue() == null || ptr.getValue() < target)
                        && (ptr.getNext() == null
                        || ptr.getNext().getValue() > target)) {
                    break;
                }
                ptr = ptr.getNext();
            }
            // 节点加入path。下探到下层。
            path.add(ptr);
            ptr = ptr.getDown();
        }
        return path;
    }

    @Override
    public void insert(int value) {
        // 查找插入的路径
        List<DataNode> path = find(value);
        DataNode previous;
        DataNode newNode = null;

        // 从底层向上层，在每一层插入节点
        for (int level = 0; level == 0 || isCreateIndex(); ++level) {
            if (level > getTopLevel()) {
                previous = addLevel();
            } else {
                previous = path.get(getTopLevel() - level);
            }

            // 插入新节点
            DataNode temp = new DataNode(previous.getLevel(), value, previous.getNext(), previous);
            previous.setNext(temp);
            temp.setDown(newNode);
            newNode = temp;
        }
    }

    @Override
     public boolean delete(int target) {
        // 查找路径
        List<DataNode> path = find(target);
        // 没找到
        if (path.get(path.size() - 1).getValue().intValue() != target) {
            return false;
        }

        for (int level = 0; level <= getTopLevel(); ++level) {
            DataNode deleteNode = path.get(getTopLevel() - level);
            if (deleteNode.getValue() != null && deleteNode.getValue().intValue() == target) {
                deleteNode.getPre().setNext(deleteNode.getNext());
                if (deleteNode.getNext() != null) {
                    deleteNode.getNext().setPre(deleteNode.getPre());
                }
            }
        }
        return true;
    }

    /**
     * 给跳表增加一层.
     * @return 新的topHead
     */
     private HeadNode addLevel() {
        HeadNode newHead = new HeadNode(getTopLevel() + 1);
        newHead.setDown(topHead);
        topHead = newHead;
        System.out.println("add level, " + newHead);
        return newHead;
    }

    static private boolean isCreateIndex() {
        long time = System.nanoTime();
        return time % 2 == 1;
    }
}
