package datastructure.skiplist.v3;

import java.util.List;

/**
 * 跳表接口。
 */
public interface SkipListInterface3 {

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
    List<SkipNode> find(int value);

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
     * 跳表节点。包含数据，以及多层索引指针。
     * 在调表的每一层，都是双向链表.
     */
    class SkipNode {

        protected final int value;
        /**
         * 多层next指针
         */
        protected SkipNode[] next;
        /**
         * 多层pre指针
         */
        protected SkipNode[] pre;

        public SkipNode(int value) {
            this.value = value;
        }
        public SkipNode(int value, int topLevel) {
            this.value = value;
            this.next = new SkipNode[topLevel + 1];
            this.pre = new SkipNode[topLevel + 1];
        }

        public SkipNode getNextAtLevel(int level) {
            return (null != next && next.length - 1 >= level) ? next[level] : null;
        }

        public void setNextAtLevel(int level, SkipNode nextNode) {
            next[level] = nextNode;
            if (null != nextNode) {
                nextNode.pre[level] = this;
            }
        }

        public SkipNode getPreAtLevel(int level) {
            return (null != pre && pre.length - 1 >= level) ? pre[level] : null;
        }

        /**
         * 返回该节点的高度. 最底层高度为0.
         * @return
         */
        public int getTopLevel() {
            return next.length - 1;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("SkipNode(");
            sb.append(value).append(", next=[");

            for (int level = 0; level < next.length; ++level) {
                SkipNode next = getNextAtLevel(level);
                if (null != next) {
                    sb.append(next.value);
                } else {
                    sb.append("null");
                }
                sb.append(",");
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append("])");

            return sb.toString();
        }
    }

}
