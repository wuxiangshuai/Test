package com.example.demo;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName: MyTest
 * @Author: WuXiangShuai
 * @Time: 19:17 2019/11/11.
 * @Description:
 */
public class MyTest {
    private static volatile int count = 0;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        AtomicInteger integer = new AtomicInteger();
        for (int i = 0; i < 5000; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    integer.incrementAndGet();
                }
            });
        }
        System.out.println("count:"+integer);
        executorService.shutdown();
    }

    private synchronized static void add(){
        count++;
    }
}
