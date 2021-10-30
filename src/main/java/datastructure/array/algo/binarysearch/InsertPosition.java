package datastructure.array.algo.binarysearch;

import lombok.Data;

/**
 * 向有序数组中插入数字，找到数字在数组中的位置。
 */
public class InsertPosition {

    static public class InsertPositionResult {
        private boolean isExist;
        private int position;

        public InsertPositionResult(boolean isExist, int position) {
            this.isExist = isExist;
            this.position = position;
        }

        public boolean isExist() {
            return isExist;
        }
        public int getPosition() {
            return position;
        }
    }

    static public InsertPositionResult findInsertPosition(int[] array, int target) {
        // array is empty
        if (array == null || array.length == 0) {
            return new InsertPositionResult(false, 0);
        }

        int start = 0;
        int end = array.length - 1;

        while (start <= end) {
            // target值在数组范围外
            if (target < array[start]) {
                return new InsertPositionResult(false, start);
            }
            if (target > array[end]) {
                return new InsertPositionResult(false, end + 1);
            }

            // mid位置
            int mid = start + (end - start) / 2;

            // 比较，缩小范围
            if (target == array[mid]) {
                return new InsertPositionResult(true, mid);
            } else if (target > array[mid]) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }

        throw new RuntimeException("不可能");
    }
}
