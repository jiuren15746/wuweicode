package datastructure.skiplist.v3;

import org.testng.collections.Lists;

import java.util.List;
import java.util.Random;

/**
 * http://legendtkl.com/2016/06/05/skiplist/
 */
public class SkipListV3 implements SkipListInterface3 {
    static public final int MAX_LEVEL = 5;
    private int topLevel;
    protected SkipNode head;


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
        while (node != null && node.getNextAtLevel(0) != null) {
            node = node.getNextAtLevel(0);
            size++;
        }
        return size;
    }

    @Override
    public List<SkipNode> find(int target) {
        SkipNode node = head;
        List<SkipNode> path = Lists.newArrayList();

        for (int level = topLevel; level >= 0;) {
            // 向右走
            if (node.value < target
                    && node.getNextAtLevel(level) != null
                    && node.getNextAtLevel(level).value <= target) {
                node = node.getNextAtLevel(level);
            }
            // 向右走不动了，转下一层
            else {
                path.add(node);
                level--;
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
