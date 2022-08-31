package datastructure.array.algo.binarysearch;

import static org.testng.Assert.*;

public class BinarySearch {

    /**
     * 在array指定长度范围内，二分查找value所在的位置。
     * 如果value不在数组中，返回应该插入的位置。
     * @param array 要搜索的数组
     * @param start 搜索范围的起始位置，包含
     * @param end 搜索范围的结束位置，包含
     */
    public static int search(char[] array, int start, int end, char target) {
        int midPos = 0;
        while (start <= end) {
            midPos = (start + end) / 2;
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


    public static void main(String[] args) {
        char[] array = new char[]{'a', 'b', 'd', 'h', 'l', 'n'};

        char target = 'h';
        int pos = search(array, 0, array.length - 1, target);
        assertEquals(pos, 3);

        target = 'k';
        pos = search(array, 0, array.length - 1, target);
        assertEquals(pos, 4);

        target = 'c';
        pos = search(array, 0, array.length - 1, target);
        assertEquals(pos, 2);
    }
}
