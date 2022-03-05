package datastructure.skiplist.v3;

import org.testng.collections.Lists;

import java.util.List;

public class SkipListV3 implements SkipListInterface3 {

    private int topLevel;
    private SkipNode head;


    public SkipListV3() {
        topLevel = 0;
        // 头节点的值最小
        head = new SkipNode(Integer.MIN_VALUE);
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
        while (node != null && node.getNext(0) != null) {
            node = node.getNext(0);
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
                    && node.getNext(level) != null
                    && node.getNext(level).value <= target) {
                node = node.getNext(level);
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
    public void insert(int value) {

    }
}
