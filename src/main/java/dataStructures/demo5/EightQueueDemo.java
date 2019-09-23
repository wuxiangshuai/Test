package dataStructures.demo5;

/**
 * @ClassName: EightQueueDemo
 * @Author: WuXiangShuai
 * @Time: 15:52 2019/9/16.
 * @Description: 八皇后问题
 */
public class EightQueueDemo {

    static int max = 8;
    static int count = 0;
    static int chessboard[] = new int[max];

    public static void main(String[] args) {
        check(0);
        System.out.println("count = " + count);
    }

    public static void check(int n) {
        if (n == max) {
            print();
            return;
        }
        // 依次放入皇后，判断是否冲突
        for (int i = 0; i < max; i++) {
            // 将当前皇后放到该行的第一列
            chessboard[n] = i;
            if (judge(n)) {
                check(n + 1);
            }

        }
    }

    // 监测第n个皇后与之前的是否冲突
    public static boolean judge(int n) {
        for (int i = 0; i < n; i++) {
            // 1.判断第n个皇后是否与前n-1个皇后在同一列
            // 2.判断第n个皇后是否与第i个皇后在同一斜线
            if (chessboard[i] == chessboard[n] || Math.abs(n - i) == Math.abs(chessboard[n] - chessboard[i])) {
                return false;
            }
        }
        return true;
    }

    // 打印方法
    public static void print() {
        count++;
        for (int i = 0; i < chessboard.length; i++) {
            System.out.print(chessboard[i] + " ");
        }
        System.out.println();
    }

}
