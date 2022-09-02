package datastructure.tree.radixtree;

public interface RadixLongKeyTree<V> {


    V find(long key);

    /**
     * Insert a long key and its value to the tree.
     *
     * @param key
     *            The string key of the object
     * @param value
     *            The value that need to be stored corresponding to the given
     *            key.
     * @throws DuplicateKeyException
     */
    void put(long key, V value);

}
