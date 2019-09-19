package executor;

import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: Test
 * @Author: WuXiangShuai
 * @Time: 9:25 2019/7/24.
 * @Description:
 */
public class BlockingTest {
    public static void main(String[] args) throws Exception {
//        throwExpection();
//        special();
//        awaysWait();
//        waitForTime();
    }

    /**
     * 设置超时时长，当队列满时，阻塞生产者线程，若阻塞时长达到设置的超时时长时，生产者线程退出
     */
    private static void waitForTime() throws InterruptedException {
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(3);
        System.out.println(queue.offer("a"));
        System.out.println(queue.offer("b"));
        System.out.println(queue.offer("c"));
        System.out.println(queue.offer("d", 5, TimeUnit.SECONDS));
        try{ TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e){ e.printStackTrace(); }
        queue.poll();
        queue.poll();
        queue.poll();
        System.out.println(queue.poll(5, TimeUnit.SECONDS));
    }

    /**
     * 当阻塞队列满时，生产者线程继续往队列里 put 元素，则线程会一直阻塞生产者线程直到可以 put 数据或响应中断退出
     * 当阻塞队列空时，消费者线程试图从队列里 take 元素，队列会一直阻塞消费者线程直到队列可用
     */
    private static void awaysWait() throws InterruptedException {
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(3);
        queue.put("a");
        queue.put("b");
        queue.put("c");
        queue.put("d");
        Arrays.stream(queue.toArray()).forEach(System.out::println);
        System.out.println(queue.take());
        System.out.println(queue.take());
        System.out.println(queue.take());
    }

    /**
     * 插入方法，成功返回 true，失败返回 false
     * 移除方法，成功返回出队列的元素，若无元素则返回 null
     */
    private static void special() {
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(3);
        System.out.println(queue.offer("a"));
        System.out.println(queue.offer("b"));
        System.out.println(queue.offer("c"));
        System.out.println(queue.offer("d"));
        Arrays.stream(queue.toArray()).forEach(System.out::println);
        System.out.println(queue.peek());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
    }

    /**
     * 当阻塞队列满时，往队列里 put 插入元素会抛出 IllegalStateException：Queue full
     * 当阻塞队列空时，从队列里 remove 移除元素会抛出 NoSuchElementException
     */
    private static void throwExpection() {
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(3);
        queue.add("a");
        queue.add("b");
        queue.add("c");
//        queue.add("d");
        System.out.println(queue.element());
        queue.remove();
        queue.remove();
        queue.remove();
        queue.remove();
    }
}
