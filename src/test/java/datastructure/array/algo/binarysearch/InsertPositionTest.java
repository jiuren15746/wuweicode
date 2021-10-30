package datastructure.array.algo.binarysearch;

import org.testng.annotations.Test;

import static datastructure.array.algo.binarysearch.InsertPosition.InsertPositionResult;
import static datastructure.array.algo.binarysearch.InsertPosition.findInsertPosition;
import static org.testng.Assert.assertEquals;

public class InsertPositionTest {
    @Test
    public void target在数组头() {
        int[] array = new int[]{1, 4, 4, 5, 7, 7, 8, 9, 9, 10};
        int target = 1;

        InsertPositionResult result = findInsertPosition(array, target);

        assertEquals(result.isExist(), true);
        assertEquals(result.getPosition(), 0);
    }

    @Test
    public void target在数组尾() {
        int[] array = new int[]{1, 4, 4, 5, 7, 7, 8, 9, 9, 10};
        int target = 10;

        InsertPositionResult result = findInsertPosition(array, target);

        assertEquals(result.isExist(), true);
        assertEquals(result.getPosition(), array.length - 1);
    }

    @Test
    public void target比数组头还小() {
        int[] array = new int[]{1, 4, 4, 5, 7, 7, 8, 9, 9, 10};
        int target = -100;

        InsertPositionResult result = findInsertPosition(array, target);

        assertEquals(result.isExist(), false);
        assertEquals(result.getPosition(), 0);
    }

    @Test
    public void target比数组尾还大() {
        int[] array = new int[]{1, 4, 4, 5, 7, 7, 8, 9, 9, 10};
        int target = 100;

        InsertPositionResult result = findInsertPosition(array, target);

        assertEquals(result.isExist(), false);
        assertEquals(result.getPosition(), array.length);
    }
}
