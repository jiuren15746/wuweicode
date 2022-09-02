package datastructure.skiplist.v5;

/**
 * 跳表节点。包含数据，以及多层索引指针。
 * 在跳表的每一层，都是双向链表.
 */
class Node<V> {

    // 头结点的value没有意义
    protected final long key;
    protected final V value;
    /**
     * 多层next指针
     */
    protected Node<V>[] next;
    //========

    public Node(long key, V value, int topLevel) {
        this.key = key;
        this.value = value;
        this.next = new Node[topLevel + 1];
    }

    public Node<V> getNextAtLevel(int level) {
        return level < next.length ? next[level] : null;
    }

    public void setNextAtLevel(int level, Node<V> nextNode) {
        next[level] = nextNode;
    }

    /**
     * 返回该节点的高度. 最底层高度为0.
     *
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
            Node<V> next = getNextAtLevel(level);
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
