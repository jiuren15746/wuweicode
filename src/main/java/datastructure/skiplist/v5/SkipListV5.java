package datastructure.skiplist.v5;

/**
 * 跳表接口定义。
 * @param <V>
 */
public interface SkipListV5<V> {

    /**
     * 返回第一个节点。
     * @return
     */
    Node<V> getFirst();

    /**
     * 删除第一个节点。
     * @return 是否删除成功
     */
    boolean removeFirst();

    /**
     * 插入一个数值。
     * @param key 排序使用的key
     * @param value key关联的值
     */
    void insert(long key, V value);

    /**
     * 返回跳表最顶层level（最底层level为零，向上递增）。
     * @return
     */
    int getTopLevel();

    /**
     * 返回元素个数
     * @return
     */
    int getSize();
}
