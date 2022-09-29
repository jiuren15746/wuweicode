package datastructure.array.algo.binarysearch;

public class BinarySearch {
    /**
     * 在array指定长度范围内，二分查找value所在的位置。如果value不在数组中，返回应该插入的位置。start和end表示搜索范围，左右都包含。
     * 返回的位置可能在数组下标范围之外。
     */
    public static int searchPos(char[] array, int start, int end, char target) {
        while (start <= end) {
            // 每次调整范围后，target与新范围的左右边界值比较，非常重要！！！
            if (target <= array[start]) {
                return start;
            }
            if (target > array[end]) {
                return end + 1;
            }
            int midPos = (start + end) >> 1;
            int diff = target - array[midPos];
            if (diff == 0) {
                return midPos;
            } else if (diff > 0) {
                start = midPos + 1;
            } else {
                end = midPos - 1;
            }
        }
        throw new RuntimeException("Impossible");
    }

    /**
     * 在array指定长度范围内，二分查找target所在位置或应该插入的位置。start和end表示搜索范围，左右都包含。
     * 返回的位置可能在数组下标范围之外。
     */
    public static int binarySearch(long[] array, int start, int end, long target) {
        while (start <= end) {
            // 每次调整范围后，target与新范围的左右边界值比较，非常重要！！！
            if (target <= array[start]) {
                return start;
            }
            if (target > array[end]) {
                return end + 1;
            }
            int midPos = (start + end) >> 1;
            long diff = target - array[midPos];
            if (diff == 0) {
                return midPos;
            } else if (diff > 0) {
                start = midPos + 1;
            } else {
                end = midPos - 1;
            }
        }
        throw new RuntimeException("Impossible");
    }
}
