package datastructure.array.algo.binarysearch;

/**
 * 二分查找算法的模板。
 */
public class BinarySearchTemplate {

    /**
     * 在有序数组中查找target的位置。
     *
     * @param array 有序数组
     * @param target 要查找的数值
     * @return 要查找数值在数组中的位置. -1表示找不到，或者数组为空数组。
     */
    static public int binarySearch(int[] array, int target) {
        // array is empty
        if (array == null || array.length == 0) {
            return -1;
        }

        int start = 0;
        int end = array.length - 1;

        while (start <= end) {
            // mid位置
            int mid = start + (end - start) / 2;

            if (target == array[mid]) {
                return mid;
            }
            // 范围缩小到右半边, start右移
            else if (target > array[mid]) {
                start = mid + 1;
            }
            // 范围缩小到左半边，end左移
            else {
                end = mid - 1;
            }
        }
        // 没找到
        return -1;
    }
}
