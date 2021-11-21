package algo.island;

/**
 * 岛屿数量问题。难度：中等
 * https://leetcode-cn.com/problems/number-of-islands/
 *
 * 难点：
 * 1. 避免重复访问
 * 2. 探索矩阵采用深度优先递归策略
 * 3. 最外层使用循环
 */
public class IslandCount {

    static public int islandCount(int[][] matrix) {
        int islandCount = 0;
        for (;;) {
            int[] position = findIsland(matrix);
            if (position != null) {
                islandCount++;
                explore(matrix, position[0], position[1]);
            } else {
                break;
            }
        }
        System.out.println("islandCount=" + islandCount);
        return islandCount;
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

    static private void explore(int[][] matrix, int row, int col) {
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

        // 深度访问上下左右
        explore(matrix, row-1, col);
        explore(matrix, row+1, col);
        explore(matrix, row, col-1);
        explore(matrix, row, col+1);
    }
}
