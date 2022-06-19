package datastructure.tree.binarytree;

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
        int parentPos = (pos - 1) / 2;
        array[pos] = value;
        // 上浮
        while (pos > 0 && value > array[parentPos]) {
            swap(pos, parentPos);
            pos = parentPos;
            parentPos = (pos - 1) / 2;
        }
    }

    @Override
    public int poll() {
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
        if (size == array.length) {
            int[] tmp = new int[size * 2];
            System.arraycopy(array, 0, tmp, 0, size);
            array = tmp;
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
