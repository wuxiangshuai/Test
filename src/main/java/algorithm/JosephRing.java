package algorithm;

/**
 * 约瑟夫环
 * Q:编号为 1-N 的 N 个士兵围坐在一起形成一个圆圈，从编号为 1 的士兵开始依次报数（1，2，3…这样依次报），
 * 数到 m 的士兵会出列，之后的士兵再从 1 开始报数。直到最后剩下一士兵，求这个士兵的编号。
 */
public class JosephRing {
    public static void main(String[] args) {
        System.out.println(f(5, 3));
    }
    public static int f(int n, int m){
        return n == 1 ? n : (f(n - 1, m) + m - 1) % n + 1;
    }
}
