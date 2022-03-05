package datastructure.skiplist.v2;

import lombok.Data;

import java.util.List;

/**
 * 跳表接口。
 */
public interface SkipListInterface {

    /**
     * 返回跳表最顶层level（最底层level为零，向上递增）。
     *
     * @return
     */
    int getTopLevel();

    /**
     * 返回元素个数
     */
    int getSize();

    /**
     * 在跳表中查找元素。返回查找路径。
     * 如果跳表中有该节点，返回该节点的查找路径。
     * 如果跳表中没有该节点，返回目标位置的前一个节点的查找路径。
     */
    List<DataNode> find(final int value);

    /**
     * 插入一个数值。
     */
    void insert(int value);

    /**
     * 删除一个数值。
     * @return 是否删除成功，没找到认为删除失败，返回false
     */
    boolean delete(int value);


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
