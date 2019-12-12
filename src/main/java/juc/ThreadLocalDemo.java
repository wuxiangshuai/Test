package juc;

import java.util.concurrent.atomic.LongAdder;

/**
 * @ClassName: ThreadLocalDemo
 * @Author: WuXiangShuai
 * @Time: 11:31 2019/12/12.
 * @Description:
 */
public class ThreadLocalDemo {

    private static ThreadLocal thread;
    private static LongAdder adder;
    private static int num;

    static {
        thread = new ThreadLocal();
        adder = new LongAdder();
        num = 0;
    }

    public static void main(String[] args) {
        for (int i = 1; i < 51; i++) {
            new Thread(() -> {
//                int random = (int) (Math.random() * 50);
//                thread.set(random);
                String name = Thread.currentThread().getName();
                doit(name);
                System.out.println(name + " ::: " + num + " ::: " + thread.get());
            }, String.valueOf(i)).start();
        }
    }

    private static synchronized void doit(String name) {
//        thread.set(adder.longValue());
//        adder.add(1);
        num++;
        thread.set(num);
        System.out.println(name + " ::: " + num);
    }

}
