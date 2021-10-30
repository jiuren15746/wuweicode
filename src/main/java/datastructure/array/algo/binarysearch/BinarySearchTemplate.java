package datastructure.array.algo.binarysearch;

/**
 * 二分查找算法的模板。背下来。
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
            // target值在数组范围外
            if (target < array[start] || target > array[end]) {
                return -1;
            }

            // mid位置
            int mid = start + (end - start) / 2;

            // 比较，缩小范围
            if (target == array[mid]) {
                return mid;
            } else if (target > array[mid]) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }

        throw new RuntimeException("不可能");
    }
}
