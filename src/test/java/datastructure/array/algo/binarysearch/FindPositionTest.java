package datastructure.array.algo.binarysearch;

import org.testng.annotations.Test;

import static datastructure.array.algo.binarysearch.FindPosition.PositionResult;
import static datastructure.array.algo.binarysearch.FindPosition.findInsertPosition;
import static org.testng.Assert.assertEquals;

public class FindPositionTest {

    @Test
    public void target在数组头() {
        int[] array = new int[]{1, 4, 4, 5, 7, 7, 8, 9, 9, 10};
        int target = 1;

        PositionResult result = findInsertPosition(array, target);

        assertEquals(result.isExist(), true);
        assertEquals(result.getPosition(), 0);
    }

    @Test
    public void target在数组尾() {
        int[] array = new int[]{1, 4, 4, 5, 7, 7, 8, 9, 9, 10};
        int target = 10;

        PositionResult result = findInsertPosition(array, target);

        assertEquals(result.isExist(), true);
        assertEquals(result.getPosition(), array.length - 1);
    }

    @Test
    public void target比数组头还小() {
        int[] array = new int[]{1, 4, 4, 5, 7, 7, 8, 9, 9, 10};
        int target = -100;

        PositionResult result = findInsertPosition(array, target);

        assertEquals(result.isExist(), false);
        assertEquals(result.getPosition(), 0);
    }

    @Test
    public void target比数组尾还大() {
        int[] array = new int[]{1, 4, 4, 5, 7, 7, 8, 9, 9, 10};
        int target = 100;

        PositionResult result = findInsertPosition(array, target);

        assertEquals(result.isExist(), false);
        assertEquals(result.getPosition(), array.length);
    }

    @Test
    public void target在数组中间() {
        int[] array = new int[]{1, 5, 7, 9, 15, 17, 20};
        int target = 4;

        PositionResult result = findInsertPosition(array, target);
        assertEquals(result.isExist(), false);
        assertEquals(result.getPosition(), 1);

        target = 10;
        result = findInsertPosition(array, target);
        assertEquals(result.isExist(), false);
        assertEquals(result.getPosition(), 4);
    }


    @Test
    public void target比数组头还小_使用绝对值比较() {
        int[] array = new int[]{-4, 5, -7, 7, 8, 9, -10};
        int target = 1;

        PositionResult result = findInsertPosition(
                array, target,
                (a, b) -> Math.abs(a) - Math.abs(b));

        assertEquals(result.isExist(), false);
        assertEquals(result.getPosition(), 0);
    }

    @Test
    public void target比数组尾还大_使用绝对值比较() {
        int[] array = new int[]{-4, 5, -7, 7, 8, 9, -10};
        int target = -20;

        PositionResult result = findInsertPosition(
                array, target,
                (a, b) -> Math.abs(a) - Math.abs(b));

        assertEquals(result.isExist(), false);
        assertEquals(result.getPosition(), array.length);
    }

    @Test
    public void target在数组中间_使用绝对值比较() {
        int[] array = new int[]{-4, 5, -7, 7, 8, 9, -10};
        int target = 6;

        PositionResult result = findInsertPosition(
                array, target,
                (a, b) -> Math.abs(a) - Math.abs(b));

        assertEquals(result.isExist(), false);
        assertEquals(result.getPosition(), 2);
    }

}
