package datastructure.array.algo.twopointers;

import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static datastructure.array.algo.twopointers.TwoNumSum.twoNumSum;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class TwoNumSumTest {

    @Test
    public void test() {
        int[] array = {2, 1, 3, 7, 11, 8, 15, 6, 8};
        int target = 9;
        List<int[]> result = twoNumSum(array, target);

        assertEquals(result.size(), 4);
        assertTrue(Arrays.equals(result.get(0), new int[]{0, 6}));
        assertTrue(Arrays.equals(result.get(1), new int[]{0, 5}));
        assertTrue(Arrays.equals(result.get(2), new int[]{1, 4}));
        assertTrue(Arrays.equals(result.get(3), new int[]{2, 3}));
    }
}
