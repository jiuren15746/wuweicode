package datastructure.skiplist.v2;

import datastructure.skiplist.v2.Nodes.DataNode;
import datastructure.skiplist.v2.Nodes.HeadNode;


/**
 * 跳表的数据结构。
 */
public class SkipList2 {
    // 最顶层的头结点
    private HeadNode topHead;

    public SkipList2() {
        topHead = new HeadNode(0);
    }

    /**
     * 返回该跳表顶层的level值。
     * 最底层的level为零。向上递增。
     * @return
     */
    public int getLevel() {
        return topHead.getLevel();
    }

    public HeadNode getTopHead() {
        return topHead;
    }
    public void setTopHead(HeadNode newHead) {
        this.topHead = newHead;
    }

    public int getSize() {
        DataNode head = getHead(0);
        int size = 0;
        while (head.getNext() != null) {
            head = head.getNext();
            size++;
        }
        return size;
    }

    /**
     * 返回指定level的头结点
     */
    private HeadNode getHead(int level) {
        if (getLevel() < level) {
            throw new IllegalArgumentException("level > skiplist.level");
        }
        HeadNode head = topHead;
        while (head.getLevel() > level) {
            head = (HeadNode) head.getDown();
        }
        return head;
    }

}
