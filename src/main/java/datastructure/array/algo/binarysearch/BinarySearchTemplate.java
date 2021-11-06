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

        // 1. 使用循环，不断缩小范围. 注意循环条件，和循环内的退出条件。
        int start = 0;
        int end = array.length - 1;

        while (start <= end) {
            // 2. target和三个位置的数字比较：start、end，mid
            // target值在数组范围外
            if (target < array[start] || target > array[end]) {
                return -1;
            }

            // 与mid位置比较，并缩小范围
            int mid = (start + end) / 2;
            int compareResult = target - array[mid];
            if (compareResult == 0) {
                return mid;
            } else if (compareResult > 0) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }

        throw new RuntimeException("不可能");
    }
}
