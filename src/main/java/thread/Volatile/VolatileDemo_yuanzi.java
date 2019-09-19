package thread.Volatile;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName: VolatileDemo_yuanzi
 * @Author: WuXiangShuai
 * @Time: 14:21 2019/6/24.
 * @Description: volatile 关键字 - 不保证原子性案例
 */
public class VolatileDemo_yuanzi {
    public static void main(String[] args) {
        MyData_yuanzi data = new MyData_yuanzi();

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 2000; j++) {
                    data.addPlusPlus();
                }
            }, String.valueOf(i)).start();
        }

        try{ TimeUnit.SECONDS.sleep(2); } catch (InterruptedException e){ e.printStackTrace(); }
        System.out.println(Thread.currentThread().getName() + "\t current value = " + data.number);
        System.out.println(Thread.currentThread().getName() + "\t current value = " + data.atomicInteger);
    }
}

class MyData_yuanzi{

    volatile int number = 0;

    AtomicInteger atomicInteger = new AtomicInteger();

    public void addPlusPlus(){
        number++;
        atomicInteger.getAndIncrement();
    }

}
