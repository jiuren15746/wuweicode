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
     * @return 插入是否成功。如果有相同的key，返回false。
     */
    boolean insert(long key, V value);

    /**
     * 查找key对应的节点的value. 查不到返回null.
     */
    V find(long key);

    /**
     * 返回元素个数
     * @return
     */
    long size();

    /**
     * 返回指定level的key个数。
     * @param level
     * @return
     */
    long getKeysCount(int level);

    void print();
}
