package executor;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @ClassName: PoolTest
 * @Author: WuXiangShuai
 * @Time: 11:36 2019/7/24.
 * @Description:
 */
public class PoolTest {
    public static void main(String[] args) {
//        getPoolByExecutors();
        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                1,
                5,
                1l, TimeUnit.SECONDS,
                new LinkedBlockingDeque<Runnable>(3),
                Executors.defaultThreadFactory(),
//                new ThreadPoolExecutor.AbortPolicy());//直接抛异常，结束运行
//                new ThreadPoolExecutor.CallerRunsPolicy());//回退到调用者线程去执行
//                new ThreadPoolExecutor.DiscardOldestPolicy());//抛弃等待最久的任务
                new ThreadPoolExecutor.DiscardPolicy());//丢弃无法处理的任务
        try {
            for (int i = 0; i < 15; i++) {
                pool.execute(() -> System.out.println(Thread.currentThread().getName() + "\t" + new Random().nextInt(100)));
//                try{ TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e){ e.printStackTrace(); }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.shutdown();
        }
    }

    private static void getPoolByExecutors() {
        //        ExecutorService pool = Executors.newSingleThreadExecutor();
//        ExecutorService pool = Executors.newFixedThreadPool(5);
        ExecutorService pool = Executors.newCachedThreadPool();
        try {
            for (int i = 0; i < 16; i++) {
                pool.execute(() -> System.out.println(Thread.currentThread().getName() + "\t" + new Random().nextInt(100)));
//                try{ TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e){ e.printStackTrace(); }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.shutdown();
        }
    }
}
