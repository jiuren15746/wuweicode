package datastructure.skiplist.v2;

import lombok.Data;


public class SkipList2 {
    // 最顶层的头结点
    private HeadNode topHead;

    public SkipList2() {
        topHead = new HeadNode(0, null, null);
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

    // 数据/索引节点，双向。
    @Data
    static public class DataNode {
        // 最底层level为零
        protected int level = 0;
        protected Integer value = null;

        protected DataNode pre = null;
        protected DataNode next = null;
        protected DataNode down = null;

        protected DataNode() {}

        public DataNode(int level) {
            this.level = level;
        }
        public DataNode(int level, Integer value, DataNode next, DataNode pre) {
            this.level = level;
            this.value = value;
            this.next = next;
            this.pre = pre;
        }
//        public DataNode(int level, Integer value, DataNode next, DataNode down) {
//            this.level = level;
//            this.value = value;
//            this.next = next;
//            this.down = down;
//        }
        @Override
        public String toString() {
            return "DataNode(L" + level + ", value=" + value + ")";
        }
    }

    // 把headNode 也认为是一种DataNode。便于查找方法的实现。
    @Data
    static public class HeadNode extends DataNode {
        private HeadNode() {}

        public HeadNode(int level, DataNode next, DataNode pre) {
            super(level, null, next, pre);
        }
//        public HeadNode(int level, DataNode next, HeadNode down) {
//            super(level, null, next, down);
//        }
        public void setValue() {
            throw new UnsupportedOperationException("Can't set value to head node.");
        }
        @Override
        public String toString() {
            return "HeadNode(L" + level + ")";
        }
    }
}
