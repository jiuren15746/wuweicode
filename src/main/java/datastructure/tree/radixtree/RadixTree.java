package datastructure.tree.radixtree;

public interface RadixTree<V> {

    /**
     * Insert a new string key and its value to the tree.
     *
     * @param key
     *            The string key of the object
     * @param value
     *            The value that need to be stored corresponding to the given
     *            key.
     * @throws DuplicateKeyException
     */
    void insert(String key, V value);

}
