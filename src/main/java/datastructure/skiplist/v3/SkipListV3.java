package datastructure.skiplist.v3;

import org.testng.collections.Lists;

import java.util.List;
import java.util.Random;

/**
 * http://legendtkl.com/2016/06/05/skiplist/
 */
public class SkipListV3 implements SkipListInterface3 {
    static public final int MAX_LEVEL = 5;

    /**
     * 跳表的高度。起始高度为0.
     */
    private int topLevel = 0;

    /**
     * 头结点不存放具体的数据。高度等于跳表最大高度。这个非常重要！！！
     */
    protected final SkipNode head;
    //========

    public SkipListV3() {
        topLevel = 0;
        // 头节点的值最小
        head = new SkipNode(Integer.MIN_VALUE, MAX_LEVEL);
    }

    public SkipListV3(SkipNode head) {
        this.head = head;
        topLevel = head.next.length - 1;
    }

    @Override
    public int getTopLevel() {
        return topLevel;
    }

    @Override
    public int getSize() {
        int size = 0;
        SkipNode node = head;
        while (node != null && (node = node.getNextAtLevel(0)) != null) {
            size++;
        }
        return size;
    }

    @Override
    public List<SkipNode> find(int target) {
        List<SkipNode> path = Lists.newArrayList();
        // 这里从逻辑上应该有两层循环：外层从高level向低level循环。内层循环在一个level内向右循环。只是代码上做了一点优化，只用了一层循环来实现。
        SkipNode curNode = head;
        SkipNode nextNode = null;
        for (int lv = topLevel; lv >= 0; ) {
            // 向右走. 条件：curNode<target && nextNode非空 && nextNode<=target
            if (curNode.value < target && (nextNode = curNode.getNextAtLevel(lv)) != null && nextNode.value <= target) {
                curNode = nextNode;
            } else { // 向右走不动了，转下一层
                path.add(curNode);
                lv--;
            }
        }
        return path;
    }

    @Override
    public void insert(int target) {
        List<SkipNode> path = find(target);
        int randomLevel = getRandomLevel();
        SkipNode newNode = new SkipNode(target, randomLevel);

        for (int level = 0; level <= randomLevel; ++level) {
            if (level <= topLevel) {
                SkipNode pre = path.get(path.size() - 1 - level);
                SkipNode next = pre.getNextAtLevel(level);
                newNode.setNextAtLevel(level, next);
                pre.setNextAtLevel(level, newNode);
            } else {
                head.setNextAtLevel(level, newNode);
                topLevel++;
            }
        }
    }

    @Override
    public boolean delete(int target) {
        List<SkipNode> path = find(target);
        SkipNode node = path.get(path.size() - 1);
        if (node.value != target) {
            return false;
        }

        for (int level = 0; level <= node.getTopLevel(); ++level) {
            SkipNode pre = node.getPreAtLevel(level);
            SkipNode next = node.getNextAtLevel(level);
            if (null != pre) {
                pre.setNextAtLevel(level, next);
            }
        }
        return true;
    }


    private int getRandomLevel() {
        Random random = new Random();
        int level = 0;
        while (random.nextInt() % 2 == 0
                && level < MAX_LEVEL && level < topLevel + 1) {
            level++;
        }
        return level;
    }
}
