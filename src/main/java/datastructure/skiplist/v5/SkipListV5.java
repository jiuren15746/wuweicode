package datastructure.skiplist.v5;

/**
 * 跳表接口定义。
 * @param <V>
 */
public interface SkipListV5<V> {

    /**
     * 返回第一个节点的值。
     * @return
     */
    V getFirst();

    /**
     * 删除第一个节点。
     * @return 是否删除成功
     */
    void removeFirst();

    /**
     * 插入一个数值。
     * @param key 排序使用的key
     * @param value key关联的值
     */
    void insert(long key, V value);

    /**
     * 返回元素个数
     * @return
     */
    long getSize();
}
