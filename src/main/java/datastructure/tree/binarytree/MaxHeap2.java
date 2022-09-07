package datastructure.tree.binarytree;

import datastructure.array.algo.heap.BinaryHeap;
import java.util.Arrays;
import java.util.Random;

public class MaxHeap2 implements BinaryHeap {

    private int[] array = new int[8];
    private int size;

    @Override
    public boolean isMaxHeap() {
        return true;
    }

    @Override
    public void offer(int value) {
        expandIfNecessary();
        // 先放在最后
        int pos = size++;
        array[pos] = value;
        // 上浮
        int parentPos = (pos - 1) / 2;
        while (pos > 0 && value > array[parentPos]) {
            swap(pos, parentPos);
            pos = parentPos;
            parentPos = (pos - 1) / 2;
        }
    }

    @Override
    public int poll() {
        final int result = array[0];
        // 把尾巴节点放到头节点
        array[0] = array[size - 1];
        size--;
        // 下沉
        int pos = 0;
        // 有两个子节点
        if (2 * pos + 2 < size) {

        }

        return 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public String toString() {
        int[] tempArray = Arrays.copyOf(array, size);
        return Arrays.toString(tempArray);
    }

    private void expandIfNecessary() {
        if (array.length == size) {
            int[] copy = new int[size * 2];
            System.arraycopy(array, 0, copy, 0, size);
            array = copy;
            System.out.println("array expand.");
        }
    }

    private void swap(int pos1, int pos2) {
        int temp = array[pos1];
        array[pos1] = array[pos2];
        array[pos2] = temp;
    }


    static public void main(String[] args) {
        int max = 1000;
        Random random = new Random();

        MaxHeap2 maxHeap = new MaxHeap2();

        for (int i = 0; i < 30; ++i) {
            int value = random.nextInt(max);
            maxHeap.offer(value);
        }
        System.out.println(maxHeap);
    }
}
