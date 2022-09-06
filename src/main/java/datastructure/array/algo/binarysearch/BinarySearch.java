package datastructure.array.algo.binarysearch;

public class BinarySearch {
    /**
     * 在array指定长度范围内，二分查找value所在的位置。如果value不在数组中，返回应该插入的位置。start和end表示搜索范围，左右都包含。
     * 返回的位置可能在数组下标范围之外。
     */
    public static int searchPos(char[] array, int start, int end, char target) {
        // 这里的判断是为了提高性能。如果target在指定范围之外，可以直接返回。不加的话走下面的逻辑，也能正确返回，只是效率不高。
        if (target < array[start]) {
            return start;
        }
        if (target > array[end]) {
            return end + 1;
        }
        while (start <= end) {
            int midPos = (start + end) >> 1;
            if (array[midPos] == target) {
                return midPos;
            } else if (array[midPos] < target) {
                start = midPos + 1;
            } else {
                end = midPos - 1;
            }
        }
        // start > end，返回start。举例思考一下。
        return start;
    }
}
