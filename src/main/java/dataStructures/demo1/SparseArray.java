package dataStructures.demo1;

/**
 * @ClassName: SparseArray
 * @Author: WuXiangShuai
 * @Time: 15:43 2019/9/10.
 * @Description: 稀疏数组
 */
public class SparseArray {

    public static void main(String[] args) {
        // 原始的 11 x 11 二维数组
        int chessArr[][] = new int[11][11];
        chessArr[1][2] = 1;
        chessArr[2][3] = 2;
        System.out.println("原始的二维数组：");
        for (int[] num: chessArr) {
            for (int val: num) {
                System.out.print(val + "\t");
            }
            System.out.println();
        }

        // 统计非 0 元素的个数
        int count0 = 0;
        for (int i = 0; i < chessArr.length; i++) {
            int[] ints = chessArr[i];
            for (int j = 0; j < ints.length; j++) {
                int anInt = ints[j];
                if (0 != anInt) {
                    count0++;
                }
            }
        }

        // 获取非 0 元素的位置
        int sarr[][] = new int[count0 + 1][3];
        sarr[0][0] = chessArr.length;
        sarr[0][1] = chessArr[0].length;
        sarr[0][2] = count0;
        for (int i = 0, row = 1; i < chessArr.length; i++) {
            int[] ints = chessArr[i];
            for (int j = 0; j < ints.length; j++) {
                int anInt = ints[j];
                if (0 != anInt) {
                    sarr[row][0] = i;
                    sarr[row][1] = j;
                    sarr[row][2] = anInt;
                    row++;
                }
            }
        }

        // 打印稀疏数组
        System.out.println("稀疏数组：");
        for (int[] num: sarr) {
            for (int val: num) {
                System.out.print(val + "\t");
            }
            System.out.println();
        }

        // 将稀疏数组恢复为二维数组
        int sarrNew[][] = new int[sarr[0][0]][sarr[0][1]];
        for (int i = 1; i <= sarr[0][2]; i++) {
            sarrNew[sarr[i][0]][sarr[i][1]] = sarr[i][2];
        }

        // 打印稀疏数组
        System.out.println("新二维数组：");
        for (int[] num: sarrNew) {
            for (int val: num) {
                System.out.print(val + "\t");
            }
            System.out.println();
        }
    }

}
