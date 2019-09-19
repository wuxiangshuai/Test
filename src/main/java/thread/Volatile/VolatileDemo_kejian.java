package thread.Volatile;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName: VolatileDemo
 * @Author: WuXiangShuai
 * @Time: 13:52 2019/6/24.
 * @Description: volatile 关键字 - 可见性案例
 */
public class VolatileDemo_kejian {
    public static void main(String[] args) {
        MyData data = new MyData();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t come in !");
            try{ TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e){ e.printStackTrace(); }
            data.addTo60();
            System.out.println(Thread.currentThread().getName() + "\t current value = " + data.number);
        }, "A").start();

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                while (data.number == 0){

                }
                System.out.println(Thread.currentThread().getName() + "\t current value = " + data.number);
            }, String.valueOf(i)).start();
        }
        while (data.number == 0){

        }
        System.out.println(Thread.currentThread().getName() + "\t current value = " + data.number);
    }
}

class MyData{
    volatile int number = 0;

    AtomicInteger atomicInteger = new AtomicInteger();

    public void addTo60(){
        number = 60;
    }
}