package datastructure.skiplist.v2;

import lombok.Data;

public class Nodes {

    // 数据节点和索引节点共用
    @Data
    static public class DataNode {
        protected Integer value = null;
        protected DataNode next = null;
        protected DataNode down = null;

        // 最底层level为零
        protected int level = 0;

        public DataNode() {}

        public DataNode(int level) {
            this.level = level;
        }
        public DataNode(int level, Integer value, DataNode next) {
            this.level = level;
            this.value = value;
            this.next = next;
        }
        public DataNode(int level, Integer value, DataNode next, DataNode down) {
            this.level = level;
            this.value = value;
            this.next = next;
            this.down = down;
        }
        @Override
        public String toString() {
            return "DataNode(level=" + level + ", value=" + value + ")";
        }
    }

    // 把headNode 也认为是一种DataNode。便于查找方法的实现。
    @Data
    static public class HeadNode extends DataNode {
        public HeadNode() {}
        public HeadNode(int level, DataNode next) {
            super(level, null, next);
        }
        public HeadNode(int level, DataNode next, HeadNode down) {
            super(level, null, next, down);
        }
        public void setValue() {
            throw new UnsupportedOperationException("Can't set value to head node.");
        }
        @Override
        public String toString() {
            return "HeadNode(level=" + level + ")";
        }
    }
}
