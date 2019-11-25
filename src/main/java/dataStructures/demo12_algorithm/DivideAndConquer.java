package dataStructures.demo12_algorithm;

/**
 * @ClassName: DivideAndConquer
 * @Author: WuXiangShuai
 * @Time: 14:28 2019/11/25.
 * @Description: 分治算法 解决 汉诺塔问题
 */
public class DivideAndConquer {

    public static void main(String[] args) {
        hanoiTower(5, 'A', 'B', 'C');
    }

    /**
     * 汉诺塔的移动方法
     * @param num 盘子个数
     * @param a 起始柱（需要移动的塔所在的最初的位置）
     * @param b 中间柱（起临时叠放作用）
     * @param c 目标柱（最终叠放到的位置）
     */
    public static void hanoiTower(int num, char a, char b, char c) {
        // 如果只有一个盘
        if (num == 1) {
            System.out.println("第 1 个盘从 " + a + " 移动到 " + c);
        } else {
            // 若是 n >= 2 的情况，总是将塔看做下面一个 盘，上面 n 个盘的情况
            // 1、把最上面的 n 个盘移动到 b 柱
            hanoiTower(num - 1, a, c, b);
            // 2、把最下面的盘移动到 c 柱
            System.out.println("第 " + num + " 个盘从 " + a + " 移动到 " + c);
            // 3、把其他盘从 b 柱移动到 c 柱
            hanoiTower(num - 1, b, a, c);
        }
    }

}
