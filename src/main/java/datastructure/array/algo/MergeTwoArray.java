package datastructure.array.algo;

public class MergeTwoArray {

    public static int[] mergeArray(int[] a, int[] b) {
        int[] result = new int[a.length + b.length];
        int k = 0;
        for (int i = 0, j = 0, aLen = a.length, bLen = b.length; i < aLen || j < bLen; ) {
            if (i >= aLen) {
                result[k++] = b[j++];
            } else if (j >= bLen) {
                result[k++] = a[i++];
            } else {
                result[k++] = a[i] < b[j] ? a[i++] : b[j++];
            }
        }
        return result;
    }
}
