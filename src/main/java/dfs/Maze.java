package dfs;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.testng.Assert;
import org.testng.collections.Lists;

import java.util.List;

public class Maze {
    @AllArgsConstructor
    @EqualsAndHashCode
    @ToString
    public static class Point {
        @Getter
        private int x;
        @Getter
        private int y;
    }

    /**
     * 如果迷宫中某个点的值为-1，表示该点不可达。
     */
    public static List<Point> dfs(int[][] maze, Point start, Point end) {
        // start and end are reachable
        Assert.assertTrue(maze[start.x][start.y] != -1);
        Assert.assertTrue(maze[end.x][end.y] != -1);
        // start is different from end
        Assert.assertTrue(!start.equals(end));

        List<Point> path = Lists.newArrayList(start);

        while (true) {
            Point current = path.get(path.size() - 1);
            if (current.equals(end)) {
                break;
            }

            Point up = new Point(current.x - 1, current.y);
            Point down = new Point(current.x + 1, current.y);
            Point left = new Point(current.x, current.y - 1);
            Point right = new Point(current.x, current.y + 1);

            if (isGo(maze, up, path)) {
                path.add(up);
                System.out.println("forward to " + up);
            } else if (isGo(maze, down, path)) {
                path.add(down);
                System.out.println("forward to " + down);
            } else if (isGo(maze, left, path)) {
                path.add(left);
                System.out.println("forward to " + left);
            } else if (isGo(maze, right, path)) {
                path.add(right);
                System.out.println("forward to " + right);
            } else {
                // 无路可走，回溯
                path.remove(path.size() - 1);
                System.out.println("回溯到 " + path.get(path.size() - 1));
            }
        }
        return path;
    }

    private static boolean isGo(int[][] maze, Point point, List<Point> path) {
        return (point.x >= 0 && point.x < maze.length)
            && (point.y >= 0 && point.y < maze[0].length)
            && maze[point.x][point.y] != -1
            && !path.contains(point);
    }


    public static void main(String[] args) {
        int[][] maze = new int[][] {
            {-1, -1, 0, 0, 0},
            {-1, -1, -1, -1, 0},
            {0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0}};

        Point start = new Point(0, 4);
        Point end = new Point(4, 0);

        List<Point> path = dfs(maze, start, end);
        System.out.println(path);
    }

}
