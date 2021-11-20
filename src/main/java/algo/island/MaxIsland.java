package algo.island;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 最大岛屿的面积。
 */
public class MaxIsland {

    static public int maxIsland(int[][] matrix) {
        int maxArea = 0;
        for (;;) {
            int[] position = findIsland(matrix);
            if (position == null) {
                break;
            }
            AtomicInteger area = new AtomicInteger(0);
            explore(matrix, position[0], position[1], area);
            if (area.get() > maxArea) {
                maxArea = area.get();
            }
        }
        System.out.println("maxIsland=" + maxArea);
        return maxArea;
    }

    static private int[] findIsland(int[][] matrix) {
        for (int i = 0; i < matrix.length; ++i) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 1) {
                    return new int[]{i, j};
                }
            }
        }
        // If no island, return null
        return null;
    }

    static private void explore(int[][] matrix, int row, int col, AtomicInteger area) {
        // 不能超出matrix范围
        if (!(row >= 0 && row < matrix.length && col >= 0 && col < matrix[0].length)) {
            return;
        }
        // 不能到水里
        if (matrix[row][col] == 0) {
            return;
        }
        // 不能重复访问。 -1表示访问过
        if (matrix[row][col] == -1) {
            return;
        }

        // set visited
        matrix[row][col] = -1;
        area.incrementAndGet();

        // 深度访问上下左右
        explore(matrix, row-1, col, area);
        explore(matrix, row+1, col, area);
        explore(matrix, row, col-1, area);
        explore(matrix, row, col+1, area);
    }
}
