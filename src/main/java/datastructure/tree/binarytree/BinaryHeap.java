package datastructure.tree.binarytree;

import java.util.ArrayList;
import java.util.List;

/**
 * 二叉堆的接口。
 */
public interface BinaryHeap {
    /**
     * 是最大堆么
     */
    boolean isMaxHeap();

    /**
     * 加入元素。
     */
    void offer(int value);

    /**
     * 弹出根节点。
     */
    int poll();

    /**
     * 返回堆大小。
     */
    int size();

    /**
     * 返回有序列表。
     */
    default List<Integer> toSortedList() {
        List<Integer> list = new ArrayList<>();
        while (size() > 0) {
            list.add(poll());
        }
        return list;
    }
}
