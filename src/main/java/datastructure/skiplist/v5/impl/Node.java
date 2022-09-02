package datastructure.skiplist.v5.impl;

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
//    protected Node<V>[] pre;
    //========

    public Node(long key, V value, int level) {
        this.key = key;
        this.value = value;
        this.next = new Node[level + 1];
//        this.pre = new Node[topLevel + 1];
    }

    public Node<V> getNext(int level) {
        return level < next.length ? next[level] : null;
    }
    public void setNext(int level, Node<V> nextNode) {
        next[level] = nextNode;
    }

//    public Node<V> getPre(int level) {
//        return level < next.length ? pre[level] : null;
//    }
//    public void setPre(int level, Node<V> preNode) {
//        pre[level] = preNode;
//    }

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
            Node<V> next = getNext(level);
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
