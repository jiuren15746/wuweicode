package datastructure.tree.binarytree;


/**
 * 用最大堆实现堆排序。
 * https://www.geeksforgeeks.org/heap-sort/?ref=lbp
 * 这个网站很好，讲的很清楚。还介绍Heap的应用场景。
 *
 * 时间复杂度：nlogn
 * 空间复杂度：0
 */
public class HeapSort {

    static public void sort(int[] array) {
        int size = array.length;

        // build max-heap
        for (int i = size/2 - 1; i >= 0; --i) {
            heapify(array, size, i);
        }

        // 头尾交换（最大的放到最后），然后缩小数组，最后新的头节点执行heapify操作。
        while (size > 1) {
            swap(array, 0, (size--) - 1);
            heapify(array, size, 0);
        }
    }

    /**
     * Heapify 堆指定的位置（pos）。一个节点和子节点比较，如果不符合最大堆定义，就和子节点交换。
     * 重复这个过程，直到这个节点下沉到合理的位置。
     *
     * @param array 数组
     * @param size  数组长度
     * @param pos   heapify的位置
     */
    public static void heapify(int[] array, int size, int pos) {
        while (2 * pos + 1 < size) { // 有子节点
            int leftIdx = 2 * pos + 1;
            int rightIdx = leftIdx + 1;
            int biggerIdx = leftIdx;
            if (rightIdx < size && array[rightIdx] > array[leftIdx]) {
                biggerIdx = rightIdx;
            }

            if (array[pos] >= array[biggerIdx]) {
                break;
            }
            swap(array, pos, biggerIdx);
            pos = biggerIdx;
        }
    }

    static private void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
