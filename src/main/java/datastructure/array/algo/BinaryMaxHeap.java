package datastructure.array.algo;

/**
 * 二叉堆（最大堆，父节点比子节点大）。可以用来实现优先级队列。
 * 最大堆定义：1. 是完全二叉树，2. 每个节点都比子节点大。
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

        // array[0]下沉
        for (int i = 0;;) {
            int leftChild = 2*i + 1;
            int rightChild =2*i + 2;
            int biggerChildIdx;

            // 没有子节点
            if (leftChild >= size) {
                break;
            } else if (rightChild >= size) { // 只有左子节点
                biggerChildIdx = leftChild;
            } else { // 左右子节点都有
                biggerChildIdx = array[leftChild] >= array[rightChild] ? leftChild : rightChild;
            }

            if (array[i] < array[biggerChildIdx]) {
                swap(i, biggerChildIdx);
                i = biggerChildIdx;
            } else {
                break;
            }
        }
        return result;
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
