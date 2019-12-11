package juc;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.LongAdder;

/**
 * @ClassName: LongAdder
 * @Author: WuXiangShuai
 * @Time: 17:52 2019/12/11.
 * @Description:
 */
public class LongAdderDemo {

    public static void main(String[] args) {
        LongAdder adder = new LongAdder();
        adder.add(10L);
        CountDownLatch latch = new CountDownLatch(50);
        for (int i = 0; i < 50; i++) {
            new Thread(() -> {
                adder.add(1L);
                // -1
                latch.countDown();
            }, String.valueOf(i)).start();
        }
        try {
            // 等待latch为0后继续执行
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(adder);
    }

}
