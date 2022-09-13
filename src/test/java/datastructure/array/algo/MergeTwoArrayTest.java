package datastructure.array.algo;

import org.testng.annotations.Test;

import java.util.Arrays;

import static datastructure.array.algo.MergeTwoArray.mergeArray;
import static org.testng.Assert.assertTrue;


public class MergeTwoArrayTest {

    @Test
    public void merge_a是空数组() {
        int[] a = {};
        int[] b = {1, 2, 3, 4, 5};
        int[] result = mergeArray(a, b);
        assertTrue(Arrays.equals(result, new int[]{1, 2, 3, 4, 5}));
    }

    @Test
    public void merge_b是空数组() {
        int[] a = {1, 2, 3, 4, 5};
        int[] b = {};
        int[] result = mergeArray(a, b);
        assertTrue(Arrays.equals(result, new int[]{1, 2, 3, 4, 5}));
    }

    @Test
    public void merge_ab相同() {
        int[] a = {1, 2, 3, 4, 5};
        int[] b = {1, 2, 3, 4, 5};
        int[] result = mergeArray(a, b);
        assertTrue(Arrays.equals(result, new int[]{1, 1, 2, 2, 3, 3, 4, 4, 5, 5}));
    }

    @Test
    public void merge_a整体比b小() {
        int[] a = {0, 1, 2};
        int[] b = {7, 8, 9, 10};
        int[] result = mergeArray(a, b);
        assertTrue(Arrays.equals(result, new int[]{0, 1, 2, 7, 8, 9, 10}));
    }

    @Test
    public void merge_a整体比b大() {
        int[] a = {6, 7, 8};
        int[] b = {1, 2, 3};
        int[] result = mergeArray(a, b);
        assertTrue(Arrays.equals(result, new int[]{1, 2, 3, 6, 7, 8}));
    }
}
