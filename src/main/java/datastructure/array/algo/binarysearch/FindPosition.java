package datastructure.array.algo.binarysearch;

import lombok.Data;

import java.util.Comparator;

/**
 * 向有序数组中插入数字，找到数字在数组中的位置。
 */
public class FindPosition {
    @Data
    static public class PositionResult {
        private final boolean isExist;
        private final int position;

        public PositionResult(boolean isExist, int position) {
            this.isExist = isExist;
            this.position = position;
        }
    }

    /**
     * 找到target在array数组中的位置。
     * 如果target在array数组中，返回其在数组中的下标。
     * 如果target不在数组中，返回将其插入数组应该所在的位置。
     */
    static public PositionResult findInsertPosition(int[] array, int target) {
        return findInsertPosition(array, target, ((x,y) -> x - y));
    }

    /**
     * 使用指定的比较函数，找到target在array数组中的位置。
     * 如果target在array数组中，返回其在数组中的下标。
     * 如果target不在数组中，返回将其插入数组应该所在的位置。
     */
    static public PositionResult findInsertPosition(int[] array,
                                                    int target,
                                                    Comparator<Integer> compareFunc) {
        // array is empty
        if (array == null || array.length == 0) {
            return new PositionResult(false, 0);
        }

        int start = 0;
        int end = array.length - 1;

        while (start <= end) {
            // target值在数组范围外
            if (compareFunc.compare(target, array[start]) < 0) {
                return new PositionResult(false, start);
            }
            if (compareFunc.compare(target, array[end]) > 0) {
                return new PositionResult(false, end + 1);
            }

            // 与mid位置比较，缩小范围
            int mid = start + (end - start) / 2;
            int midCompareResult = compareFunc.compare(target, array[mid]);
            if (midCompareResult == 0) {
                return new PositionResult(true, mid);
            } else if (midCompareResult > 0) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }

        throw new RuntimeException("不可能");
    }

    /**
     * 数组中插入元素的结果。包括：新数组，插入元素在新数组中的位置。
     */
    @Data
    static public class InsertResult {
        private final int[] newArray;
        // 插入的元素在新数组中的位置
        private final int position;

        public InsertResult(int[] newArray, int position) {
            this.newArray = newArray;
            this.position = position;
        }
    }

    /**
     * 把value插入array数组，返回新插入的数组，和value在新数组中的位置。
     * @param array
     * @param value
     * @return
     */
    static public InsertResult insert(int[] array, int value) {
        int pos = findInsertPosition(array, value).getPosition();

        int[] newArray = new int[array.length + 1];
        newArray[pos] = value;
        for (int i = 0; i < array.length; ++i) {
            if (i < pos) {
                newArray[i] = array[i];
            } else {
                newArray[i+1] = array[i];
            }
        }
        return new InsertResult(newArray, pos);
    }
}
