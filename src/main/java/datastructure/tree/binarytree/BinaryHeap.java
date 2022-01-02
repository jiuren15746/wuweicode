package datastructure.tree.binarytree;

/**
 * 二叉堆的接口。
 */
public interface BinaryHeap {
    /**
     * 是最大堆么
     */
    boolean isMapHeap();

    /**
     * 加入元素。
     */
    void offer(int value);

    /**
     * 弹出根节点。
     */
    int poll();
}
