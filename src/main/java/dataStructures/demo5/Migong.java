package dataStructures.demo5;

/**
 * @ClassName: Migong
 * @Author: WuXiangShuai
 * @Time: 15:20 2019/9/16.
 * @Description: 迷宫问题，从一点到另一点
 */
public class Migong {

    public static void main(String[] args) {
        // 创建二维数组，模拟迷宫
        int map[][] = new int[8][7];
        // 使用 1 代表墙
        for (int i = 0; i < 7; i++) {
            map[0][i] = 1;
            map[7][i] = 1;
        }
        for (int i = 0; i < 7; i++) {
            map[i][0] = 1;
            map[i][6] = 1;
        }

        map[3][1] = 1;
        map[3][2] = 1;

        setWay(map, 1, 1);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 7; j++) {
                System.out.print(map[i][j] + "\t");
            }
            System.out.println();
        }
    }

    /**
     * 使用递归回溯找路
     * 1 代表墙，2 代表走过，3 代表走不通
     * @param map   地图
     * @param i     y轴位置
     * @param j     x轴位置
     * @return
     * 使用策略：下 - 右 - 上 - 左
     */
    public static boolean setWay(int[][] map, int i, int j) {
        if (2 == map[6][5]) {
            return true;
        } else {
            if (0 == map[i][j]) {
                // 按策略出发
                map[i][j] = 2; // 假定可以走通
                if (setWay(map, i + 1, j)) { // 下
                    return true;
                } else if (setWay(map, i, j + 1)) { // 右
                    return true;
                } else if (setWay(map, i - 1, j)) { // 上
                    return true;
                } else if (setWay(map, i, j - 1)) { // 左
                    return true;
                } else { // 该点无法走通
                    map[i][j] = 3;
                    return false;
                }
            }
        }
        return false;
    }

}
