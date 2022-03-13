package datastructure.tree.binarytree;

/**
 * 最小堆实现。定义：1. 完全二叉树，2.父节点<=子节点。
 */
public class BinaryMinHeap implements BinaryHeap {

    private int[] array;
    private int size;

    public BinaryMinHeap(int capacity) {
        array = new int[capacity];
        size = 0;
    }

    @Override
    public boolean isMaxHeap() {
        return false;
    }

    @Override
    public void offer(int value) {
        array[size++] = value;
        swim(size-1);
    }

    @Override
    public int poll() {
        int result = array[0];
        // 取最后一个元素填充根节点位置。然后下沉。
        array[0] = array[(size--) - 1];
        sink(0);
        return result;
    }

    @Override
    public int size() {
        return size;
    }

    // 上浮
    private void swim(int pos) {
        while (pos > 0 && array[pos] < array[(pos - 1) / 2]) {
            swap(pos, (pos - 1) / 2);
            pos = (pos - 1) / 2;
        }
    }
    // 下沉
    private void sink(int pos) {
        while (2 * pos + 1 < size) {
            int i = 2 * pos + 1;
            if (i + 1 < size && array[i + 1] < array[i]) {
                i = i + 1;
            }
            if (array[pos] <= array[i]) {
                break;
            }
            swap(pos, i);
            pos = i;
        }
    }

    private void swap(int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
