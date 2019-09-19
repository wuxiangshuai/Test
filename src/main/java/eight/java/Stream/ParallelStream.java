package eight.java.Stream;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @ClassName: ParallelStream
 * @Author: WuXiangShuai
 * @Time: 12:36 2019/4/20.
 * @Description:
 */
@SuppressWarnings("unused")
public class ParallelStream {

    public static void main(String[] args) {
        parallel();
    }

    private static void parallel() {
        //并行流操作
        long l = System.currentTimeMillis();
        //10104 -> 2631
//        IntStream.rangeClosed(1, 10000).peek(ParallelStream::debug).count();
//        IntStream.rangeClosed(1, 10000).parallel().peek(ParallelStream::debug).count();
        //37 -> 54
//        IntStream.rangeClosed(1, 1000000).count();
//        IntStream.rangeClosed(1, 1000000).parallel().count();
        /**
         * 结论：对于需要时间进行的操作，并行化可大大减少运行时间，
         *      但是当单个操作所需时长较短时，并行化效率低于串行操作
         */
//        System.out.println(System.currentTimeMillis() - l);

        //多次调用串行和并行后，以最后一次为准
//        IntStream.range(1, 100)
//                .parallel()
//                .peek(Case04StreamDemo04 :: debug)
//                .sequential()
//                .peek(Case04StreamDemo04 :: debug02)
//                .count();
//        IntStream.range(1, 100)
//                .sequential()
//                .peek(Case04StreamDemo04 :: debug)
//                .parallel()
//                .peek(Case04StreamDemo04 :: debug02)
//                .count();

        /**
         * 并行操作时打印线程信息
         * 结论：并行流使用的线程池是JDK自带的ForkJoinPool.commonPool，默认线程数是CUP个数。
         *      可通过修改该类属性，实现自定义线程数量
         */
//        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "20");
//        IntStream.rangeClosed(1, 1000).parallel().peek(ParallelStream::debug03).count();

        ForkJoinPool forkJoinPool = new ForkJoinPool(10);
        forkJoinPool.submit(() -> IntStream.rangeClosed(1, 1000).parallel().peek(ParallelStream::debug03).count());
        forkJoinPool.shutdown();
        //主线程等待线程池执行结束
        synchronized (forkJoinPool) {
            try {
                forkJoinPool.wait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void debug(int i) {
        System.out.println("Debug ::: " + i);
        try {
            TimeUnit.MICROSECONDS.sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void debug03(Integer i) {
        System.out.println(Thread.currentThread().getName() + "-Debug-" + i);
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
