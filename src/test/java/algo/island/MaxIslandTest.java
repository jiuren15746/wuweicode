package algo.island;

import org.testng.Assert;
import org.testng.annotations.Test;

public class MaxIslandTest {

    @Test
    public void test1() {
        int[][] matrix = {
                {1,1,1,1,0},
                {1,1,0,1,0},
                {1,1,0,0,0},
                {0,0,0,0,0}
        };
        Assert.assertEquals(MaxIsland.maxIsland(matrix), 9);
    }

    @Test
    public void test2() {
        int[][] matrix = {
                {1,1,0,0,0},
                {1,1,0,0,0},
                {0,0,1,0,0},
                {0,0,0,1,1}
        };
        Assert.assertEquals(MaxIsland.maxIsland(matrix), 4);
    }
}
