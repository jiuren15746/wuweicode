package datastructure.tree.binarytree;

/**
 * 二叉堆（最大堆，父节点比子节点大）。可以用来实现优先级队列。
 * 最大堆定义：1. 是完全二叉树，2. 每个节点>=子节点。
 *
 * 使用场景：只需要找到最大值，但是不需要全部排序。
 * 使用场景：交易所的撮合引擎。
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

    /**
     * 在最后插入新节点，然后上浮。上浮是为了满足最大堆定义。
     */
    @Override
    public void offer(int value) {
        // 放到数组最后
        int pos = size++;
        int parentPos = (pos - 1) / 2;
        array[pos] = value;
        swim(size - 1);
        // 然后上浮
        while (pos > 0 && value > array[parentPos]) {
            swap(pos, parentPos);
            pos = parentPos;
        }
    }

    /**
     * 取最大堆的根节点，即最大的节点。
     * 然后把最后一个节点move到根节点，然后下沉。下沉也是为了满足最大堆定义。
     * @return
     */
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
        // 有子节点
        while (2 * pos + 1 < size) {
            int biggerChild = 2 * pos + 1;
            // 两个子节点，比较
            if (biggerChild + 1 < size && array[biggerChild + 1] > array[biggerChild]) {
                biggerChild += 1;
            }
            if (array[pos] >= array[biggerChild]) {
                break;
            } else {
                swap(pos, biggerChild);
                pos = biggerChild;
            }
        }
    }

    private void swap(int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    /**
     * 检查是否满足最大堆定义。
     * @return
     */
    public boolean check() {
        for (int idx = 0; idx < size; ++idx) {
            if (!isSatisfy(idx)) {
                return false;
            }
        }
        return true;
    }

    public boolean isSatisfy(int parentIdx) {
        int leftChild = 2 * parentIdx + 1;
        // 没有子节点
        if (leftChild >= size) {
            return true;
        }
        // 只有左子节点
        else if (leftChild + 1 >= size) {
            return array[parentIdx] >= array[leftChild];
        }
        // 有两个子节点
        else {
            return array[parentIdx] >= array[leftChild]
                    && array[parentIdx] >= array[leftChild + 1];
        }
    }
}
