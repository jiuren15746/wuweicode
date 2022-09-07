package datastructure.array.algo.heap.impl;

/**
 * 最小堆实现。定义：1. 完全二叉树，2.父节点<=子节点。
 */
public class BinaryMinHeap<V extends Comparable> {

    private Object[] array;
    private int size;
    //========

    public BinaryMinHeap() {
        this(8);
    }

    public BinaryMinHeap(int capacity) {
        array = new Object[capacity];
        size = 0;
    }

    public boolean isMaxHeap() {
        return false;
    }

    /**
     * 插入元素。
     * @param value
     */
    public void offer(V value) {
        expandIfNecessary();
        // 先放到最后一个位置
        array[size++] = value;
        // 如果比parent小，则上浮
        int pos = size - 1;
        int parentPos = (pos - 1) / 2;
        while (pos > 0 && ((V) array[pos]).compareTo(array[parentPos]) < 0) {
            swap(pos, parentPos);
            pos = parentPos;
        }
    }

    /**
     * 取出最小元素，即零号元素。
     * @return
     */
    public V poll() {
        V result = (V) array[0];
        // 取最后一个元素填充根节点位置
        array[0] = array[(size--) - 1];
        // 根节点下沉
        int pos = 0;
        for (int leftChild, minChild; (leftChild = 2 * pos + 1) < size; pos = minChild) {
            minChild = (leftChild + 1 < size && ((V) array[leftChild + 1]).compareTo(array[leftChild]) < 0) ? leftChild + 1 : leftChild;
            if (((V) array[pos]).compareTo(array[minChild]) <= 0) {
                break;
            }
            swap(pos, minChild);
        }
        return result;
    }

    public int size() {
        return size;
    }

    private void swap(int i, int j) {
        Object temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    private void expandIfNecessary() {
        if (size >= array.length) {
            Object[] newArray = new Object[array.length << 1];
            System.arraycopy(array, 0, newArray, 0, size);
            array = newArray;
            System.out.println("min heap expand, capacity=" + array.length);
        }
    }
}
