package algorithm;

/**
 * 是否是二的幂次方
 * Q：判断一个整数 n 是否为 2 的幂次方
 */
public class PowerOf2 {
    public static void main(String[] args) {
        System.out.println(isPow(1));
        System.out.println(isPow(2));
        System.out.println(isPow(4));
        System.out.println(isPow(10));
        System.out.println(isPow(16));
    }
    public static boolean isPow(int n) {
        return n <= 1 ? false : (n & (n-1)) == 0;
    }
}
