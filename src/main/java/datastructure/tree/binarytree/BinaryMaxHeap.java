package datastructure.tree.binarytree;

/**
 * 二叉堆（最大堆，父节点比子节点大）。可以用来实现优先级队列。
 * 最大堆定义：1. 是完全二叉树，2. 每个节点>=子节点。
 */
public class BinaryMaxHeap implements BinaryHeap {

    private int[] array;
    private int size;

    public BinaryMaxHeap(int capacity) {
        this.array = new int[capacity];
        this.size = 0;
    }

    public BinaryMaxHeap(int[] elements) {
        this.array = elements;
        this.size = elements.length;
    }

    @Override
    public boolean isMaxHeap() {
        return true;
    }

    @Override
    public void offer(int value) {
        // 放到数组最后。然后上浮。
        array[size++] = value;
        swim(size - 1);
    }

    @Override
    public int poll() {
        if (size <= 0) {
            throw new ArrayIndexOutOfBoundsException();
        }
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
    private void swim(int idx) {
        while (idx > 0 && array[idx] > array[(idx - 1) / 2]) {
            swap(idx, (idx - 1) / 2);
            idx = (idx - 1) / 2;
        }
    }

    // 下沉
    private void sink(int pos) {
        while (2 * pos + 1 < size) { // 有子节点
            int i = 2 * pos + 1; // 较大子节点下标
            // 两个子节点
            if (i + 1 < size && array[i] < array[i + 1]) {
                i = i + 1;
            }
            if (array[pos] >= array[i]) {
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

    public int[] getElements() {
        int[] copy = new int[size];
        System.arraycopy(array, 0, copy, 0, size);
        return copy;
    }

    /**
     * 检查是否满足最大堆定义。
     * @return
     */
    public boolean check() {
        for (int i = 0; i < size; ++i) {
            int childl = 2*i + 1;
            int childr = 2*i + 2;
            if (childl < size && array[i] < array[childl]) {
                return false;
            }
            if (childr < size && array[i] < array[childr]) {
                return false;
            }
        }
        return true;
    }
}
