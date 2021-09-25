package dataStructures.demo12_algorithm;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * 马踏棋盘（骑士周游）算法
 */
public class HourseChessBoard {

    // x 表示棋盘的列数
    private static  int X;
    // y 表示棋盘的行数
    private static  int Y;
    // 创建一个数组，标记棋盘的各个位置是否被访问过
    private static boolean visited[];
    // 使用一个变量，标记是否棋盘所有位置都被访问
    private static boolean finished = false;

    public static void main(String[] args) {
        System.out.println("马儿开始周游~");
        X = 8;
        Y = 8;
        // 定义马儿初始位置，row 行，column 列
        int row = 3, column = 4;
        // 创建棋盘
        int[][] chessboard = new int[X][Y];
        visited = new boolean[X * Y];
        long start = System.currentTimeMillis();
        traversalChessboard(chessboard, row - 1,
                column - 1, 1);
        long end = System.currentTimeMillis();
        long temp = end - start;
        System.out.println("共耗时：" + temp + "");
        // 输出棋盘
        for (int[] rows : chessboard) {
            for (int step : rows)
                System.out.printf(step + "\t");
            System.out.println();
        }
    }

    /**
     * 完成马踏棋盘问题的算法
     * @param chessboard 棋盘
     * @param row 马儿当前位置的行，从 0 开始
     * @param column 马儿当前位置的列，从 0 开始
     * @param step 第几步，初始为 1
     */
    public static void traversalChessboard(int[][] chessboard, int row,
                                           int column, int step) {
        chessboard[row][column] = step;
        // 标记该位置已被访问，
        // row * x 为当前位置之前几行的元素和
        // column 为当前元素所在行的第几个位置
        visited[row * X + column] =true;
        // 获取当前位置可以走的下一个位置的集合
        ArrayList<Point> ps = next(new Point(column, row));
        // 对 ps 进行排序
        sort(ps);
        // 对可走的位置进行遍历
        for (Point p : ps)
            // 判断该点是否已经访问过
            if (!visited[p.y * X + p.x])
                traversalChessboard(chessboard, p.y, p.x, step + 1);
        // 判断马儿是否完成了任务，使用 step 和应走的步数比较，
        // 如果没有达到数量，则表示没有完成任务，而后将该位置 置空
        if (step < X * Y && !finished) {
            chessboard[row][column] = 0;
            visited[row * X + column] = false;
//        } else
//            finished = true;
        } else if (finished) {
            return;
        } else {
            boolean right = true;
            for (Point point : ps)
                if (chessboard[point.y][point.x] == 1)
                    right = false;
            if (right) {
                chessboard[row][column] = 0;
                visited[row * X + column] = false;
            } else
                finished = true;
        }
    }

    /**
     * 根据当前的位置(Point 对象)，计算马儿还能走哪些位置(Point)，
     * 将这些位置放入到一个集合中。
     * @param curPoint 当前马儿位置
     * @return 马儿接下来能走的位置
     */
    public static ArrayList<Point> next(Point curPoint) {
        // 创建一个 ArrayList
        ArrayList<Point> ps = new ArrayList<>();
        // 创建一个 Point
        Point point = new Point();
        // 0
        if ((point.x = curPoint.x - 2) >= 0
                && (point.y = curPoint.y - 1) >= 0)
            ps.add(new Point(point));
        // 1
        if ((point.x = curPoint.x - 1) >= 0
                && (point.y = curPoint.y - 2) >= 0)
            ps.add(new Point(point));
        // 2
        if ((point.x = curPoint.x + 1) < X
                && (point.y = curPoint.y - 2) >= 0)
            ps.add(new Point(point));
        // 3
        if ((point.x = curPoint.x + 2) < X
                && (point.y = curPoint.y - 1) >= 0)
            ps.add(new Point(point));
        // 4
        if ((point.x = curPoint.x + 2) < X
                && (point.y = curPoint.y + 1) < Y)
            ps.add(new Point(point));
        // 5
        if ((point.x = curPoint.x + 1) < X
                && (point.y = curPoint.y + 2) < Y)
            ps.add(new Point(point));
        // 6
        if ((point.x = curPoint.x - 1) >= 0
                && (point.y = curPoint.y + 2) < Y)
            ps.add(new Point(point));
        // 7
        if ((point.x = curPoint.x - 2) >= 0
                && (point.y = curPoint.y + 1) < Y)
            ps.add(new Point(point));
        return ps;
    }

    // 根据当前步的下一步的下一步位置集合长度进行非递减排序
    public static void sort(ArrayList<Point> ps) {
        ps.sort(new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                // 获取到 o1 的下一步的位置个数
                int count1 = next(o1).size();
                int count2 = next(o2).size();
                return count1 == count2 ? 0 :
                        count1 < count2 ? -1 : 1;
            }
        });
    }

}
