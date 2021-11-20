package algo.island;

import org.testng.Assert;
import org.testng.annotations.Test;

public class IslandCountTest {

    @Test
    public void test1() {
        int[][] matrix = {
                {1,1,1,1,0},
                {1,1,0,1,0},
                {1,1,0,0,0},
                {0,0,0,0,0}
        };
        Assert.assertEquals(IslandCount.islandCount(matrix), 1);
    }

    @Test
    public void test2() {
        int[][] matrix = {
                {1,1,0,0,0},
                {1,1,0,0,0},
                {0,0,1,0,0},
                {0,0,0,1,1}
        };
        Assert.assertEquals(IslandCount.islandCount(matrix), 3);
    }
}
