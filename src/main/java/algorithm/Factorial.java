package algorithm;

/**
 * 阶乘
 * Q:给定一个整数 N，那么 N 的阶乘 N! 末尾有多少个 0？例如：N = 10，则 N！= 3628800，那么 N! 的末尾有两个0
 */
public class Factorial {
    public static void main(String[] args) {
        System.out.println(f(25));
    }
    public static int f(int n){
        return n == 0 ? 0 : n / 5 + f(n / 5);
    }
}
