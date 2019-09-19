package thread.solve;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @ClassName: ListTest
 * @Author: WuXiangShuai
 * @Time: 16:56 2019/6/24.
 * @Description: 多线程状态下的异常，记录元素个数（size）不正确导致遍历越界
 */
public class ListTest {
    public static void main(String[] args) {
//        setes();
        list();
    }

    private static void list() {
//        ArrayList<String> list = new ArrayList<>();
        //返回 Collections 内部类 SynchronizedRandomAccessList、SynchronizedList
        //其增删改查、排序方法被 synchronize 关键字修饰
        List<Object> list = Collections.synchronizedList(new ArrayList<>());
        //返回 CopyOnWriteArrayList
        //其增删改查、排序方法被 Lock（ReentrantLock 使用默认非公平锁） 修饰
//        List<Object> list = new CopyOnWriteArrayList<>();

        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 2));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }
    }

    private static void setes(){
//        HashSet<String> set = new HashSet<>();
//        Set<String> set = Collections.synchronizedSet(new HashSet<>());
        Set<String> set = new CopyOnWriteArraySet<>();

        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                set.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(Thread.currentThread().getName() + "\t" + set);
            }, String.valueOf(i)).start();
        }
    }
}
