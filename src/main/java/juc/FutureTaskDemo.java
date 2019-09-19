package juc;

import java.util.concurrent.*;

/**
 * @ClassName: FutureTaskDemo
 * @Author: WuXiangShuai
 * @Time: 9:34 2019/8/13.
 * @Description:
 */
public class FutureTaskDemo {

    public static void main(String[] args) throws Exception {
        ExecutorService pool = Executors.newCachedThreadPool();
        FutureTask task = new FutureTask<Integer>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println("子线程...");
                try{ TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e){ e.printStackTrace(); }
                return 1;
            }
        });
        pool.submit(task);
        System.out.println(Thread.currentThread().getName());
        System.out.println(task.get());
        pool.shutdown();
    }

    public void taskDemo1() throws Exception {
        FutureTask<Integer> task = new FutureTask<Integer>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                try{ TimeUnit.SECONDS.sleep(2); } catch (InterruptedException e){ e.printStackTrace(); }
                System.out.println(Thread.currentThread().getName());
                return 1;
            }
        });
        new Thread(task, "haha").start();
        System.out.println("Main thread...");
        System.out.println(task.get());
    }

}
