package juc;

import java.util.concurrent.*;

/**
 * @ClassName: ScheduleExecutorServiceDemo
 * @Author: WuXiangShuai
 * @Time: 18:16 2019/12/11.
 * @Description:
 */
public class ScheduleExecutorServiceDemo {

    public static void main(String[] args) {
        ScheduledThreadPoolExecutor pool = new ScheduledThreadPoolExecutor(10);
        //
//        delayTaskRunnable(pool);
//        delayTaskCallAble(pool);
//        timeTaskAtFixedRate(pool);
        delayTaskWithFixedDelay(pool);
//        pool.shutdown();
    }
    /**
     * 定时任务
     * 创建并执行给一个循环的延迟任务
     * Runnable 操作， initialDelay 首次执行时的延迟， delay 后续延时， TimeUnit delay和period的时间单位
     * 第一次执行前有 initialDelay 的延迟，后续的任务在前者结束 delay 后执行，非周期性。
     * 注意：
     * 同 AtFixedRate
     */
    private static void delayTaskWithFixedDelay(ScheduledThreadPoolExecutor pool) {
        pool.scheduleWithFixedDelay(()->{
            try {
                System.out.println("haha");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 1L, 1L, TimeUnit.SECONDS);
    }
    /**
     * 定时任务
     * 创建并执行给一个周期性任务
     * Runnable 操作， initialDelay 首次执行时的延迟， period 周期时长， TimeUnit delay和period的时间单位
     * 注意：
     * 1、下一次执行任务的时间只与period相关，若将执行下一次任务时，上一个任务仍未完成，推迟执行后续的任务，但周期仍不变。
     * 2、若某次执行任务出现异常【且未捕捉到】，后续的执行都会取消。
     */
    private static void timeTaskAtFixedRate(ScheduledThreadPoolExecutor pool) {
        pool.scheduleAtFixedRate(()->{
            try {
                System.out.println("haha");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 1L, 2L, TimeUnit.SECONDS);
    }

    // 延迟任务，使用 Callable 接口
    private static void delayTaskCallAble(ScheduledThreadPoolExecutor pool) {
        ScheduledFuture<String> task = pool.schedule(() -> "haha", 2L, TimeUnit.SECONDS);
        try {
            System.out.println(task.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
    // 延迟任务，使用 Runnable 接口
    private static void delayTaskRunnable(ScheduledThreadPoolExecutor pool) {
        pool.schedule(() -> System.out.println("haha"), 2L, TimeUnit.SECONDS);
    }

}
