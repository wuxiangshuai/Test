package dataStructures.demo12_algorithm;

import java.util.Arrays;

/**
 * @ClassName: KnapsackProblem
 * @Author: WuXiangShuai
 * @Time: 17:33 2019/11/25.
 * @Description: 动态规划算法
 */
public class KnapsackProblem {

    public static void main(String[] args) {
        // 物品重量
        int w[] = {1, 4, 3};
        // 物品价值
        int val[] = {1500, 3000, 2000};
        // 背包承重
        int weight = 4;
        // 物品个数
        int n = w.length;
        // n个物品按不同策略放入承重为 wight 的背包内的价值
        int v[][] = new int[n + 1][weight + 1];
        // 记录放入商品的情况
        int putStatus[][] = new int[n + 1][weight + 1];
        // 动态规划
        for (int i = 1; i < v.length; i++) { // 商品行
            int[] commodity = v[i];
            for (int j = 1; j < commodity.length; j++) { // 重量列
                // 若当前商品重量小于等于背包重量
                if (w[i - 1] <= j) {
                    // 比较同列的上一行策略所赚取资金是否大于当前策略
                    // 若大于，则当前策略置为新策略
                    // 若小于，则当前策略置为同列上一行的策略
                    v[i][j] = Math.max(v[i - 1][j], val[i - 1] + v[i - 1][j - w[i - 1]]);
                    putStatus[i][j] = 1;
                } else
                    // 当前商品重于背包重量，当前策略置为同列上一行的策略
                    v[i][j] = v[i - 1][j];
            }
        }
        // 输出背包重量与可承载最大价值的表格
        for (int[] ints : v) {
            System.out.println(Arrays.toString(ints));
        }
        // 输出最大价值量的物品
        int i = putStatus.length - 1; // 行的最大下标
        int j = putStatus[0].length - 1; // 列的最大下标
        // 倒序遍历
        while (i > 0 && j > 0) {
            if (putStatus[i][j] == 1) {
                System.out.printf("第%d个商品放入背包\n", i);
                j -= w[i - 1];
            }
            i--;
        }
    }

}
