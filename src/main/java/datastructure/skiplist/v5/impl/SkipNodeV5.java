package datastructure.skiplist.v5.impl;

/**
 * 跳表节点。包含数据，以及多层索引指针。
 * 在跳表的每一层，都是双向链表.
 */
class SkipNodeV5<V> {

    // 头结点的value没有意义
    protected final long key;
    protected final V value;
    /**
     * 多层next指针和pre指针
     */
    protected SkipNodeV5<V>[] next;
    protected SkipNodeV5<V>[] pre;
    //========

    public SkipNodeV5(long key, V value, int level) {
        this.key = key;
        this.value = value;
        this.next = new SkipNodeV5[level + 1];
        this.pre = new SkipNodeV5[level + 1];
    }

    public SkipNodeV5<V> getNext(int level) {
        return level < next.length ? next[level] : null;
    }
    public SkipNodeV5<V> getPre(int level) {
        return level < pre.length ? pre[level] : null;
    }

    public void setNext(int level, SkipNodeV5<V> nextNode) {
        next[level] = nextNode;
    }
    public void setPre(int level, SkipNodeV5<V> preNode) {
        pre[level] = preNode;
    }

    /**
     * 返回该节点Level. 最底层level为0.
     */
    public int getLevel() {
        return next.length - 1;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SkipNode(");
        sb.append(value).append(", next=[");

        for (int level = 0; level < next.length; ++level) {
            SkipNodeV5<V> next = getNext(level);
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
