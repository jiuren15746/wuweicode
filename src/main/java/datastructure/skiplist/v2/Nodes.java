package datastructure.skiplist.v2;

import lombok.Data;

public class Nodes {
    /**
     * 数据/索引节点，双向链表。
     */
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

        @Override
        public String toString() {
            return "DataNode(L" + level + ", value=" + value + ")";
        }
    }

    /**
     * 头结点的value为null。因此独立出来一个类型。但是继承DataNode。
     */
    @Data
    static public class HeadNode extends DataNode {
        private HeadNode() {}

        // todo 头结点为啥有pre
        public HeadNode(int level) {
            super(level);
        }

        public void setValue() {
            throw new UnsupportedOperationException("Can't set value to head node.");
        }
        @Override
        public String toString() {
            return "HeadNode(L" + level + ")";
        }
    }
}
