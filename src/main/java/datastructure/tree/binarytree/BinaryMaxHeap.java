package datastructure.tree.binarytree;

/**
 * 二叉堆（最大堆，父节点比子节点大）。可以用来实现优先级队列。
 * 最大堆定义：1. 是完全二叉树，2. 每个节点>=子节点。
 */
public class BinaryMaxHeap {

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

    public void offer(int value) {
        int i = size;
        array[size++] = value;

        while (i > 0) {
            int parentIdx = (i - 1) / 2;
            if (array[i] > array[parentIdx]) {
                swap(i, parentIdx);
                i = parentIdx;
            } else {
                break;
            }
        }
    }

    public int poll() {
        if (size <= 0) {
            throw new ArrayIndexOutOfBoundsException();
        }
        int result = array[0];
        // 把最后一个元素放到array[0]
        array[0] = array[size - 1];
        size--;

        maxHeapify(0);
        return result;
    }

    /**
     * 最大堆合法化操作。idx位置的节点如果小于子节点，和最大的子节点交换。并循环，直到满足最大堆定义。
     * @param idx
     */
    private void maxHeapify(int idx) {
        for (; ; ) {
            int childl = 2 * idx + 1;
            int childr = 2 * idx + 2;

            if (childr < size) { // 两个子节点
                int biggerChildIdx = array[childl] > array[childr] ? childl : childr;
                if (array[idx] < array[biggerChildIdx]) {
                    swap(idx, biggerChildIdx);
                    idx = biggerChildIdx;
                    continue;
                }
            } else if (childl < size) { // 只有左子节点
                if (array[idx] < array[childl]) {
                    swap(idx, childl);
                }
            }
            return;
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
