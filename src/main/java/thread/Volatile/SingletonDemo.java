package thread.Volatile;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName: SingletonDemo
 * @Author: WuXiangShuai
 * @Time: 15:44 2019/6/24.
 * @Description:
 * DCL（Double Click Lock），双端检查机制不一定线程安全，原因是有指令重排序的存在，加入 volatile 可以禁止指令重排
 * 原因在于：某个线程执行第一次检查，读取到的 instance 不为 null 时，instance 的引用对象可能还没有完成初始化，仅仅为其分配了内存空间。
 * 初始化流程：
 * instance = new Singleton();
 * memory = allocatge(); 1、分配对象内存空间
 * instance(memory);     2、初始化对象
 * instance = memory;    3、设置 instance 指向刚刚分配的内存地址，此时 istance != null
 *
 * 步骤 2、3 不存在数据依赖的关系，读取到的 instance 不为 null 时，instance 的引用对象可能还没有完成初始化，仅仅为其分配了内存空间。
 * instance = new Singleton();
 * memory = allocatge(); 1、分配对象内存空间
 * instance = memory;    3、设置 instance 指向刚刚分配的内存地址，此时 instance != null
 * instance(memory);     2、初始化对象
 *
 * 指令重排序仅保证串行语句的执行的一致性（单线程），不会关系多线程的语义一致性。
 * 当一条线程访问 instance 不为 null 时，由于 instance 实例未必完成了初始化，也就造成了线程安全问题。
 */
public class SingletonDemo {

    private static volatile SingletonDemo singleton = null;

    public SingletonDemo() {
        System.out.println(Thread.currentThread().getName() + "\t 构造方法");
    }

    //DCL (Double Click Lock) 双端检查机制
    public static SingletonDemo getInstance (){
        if (singleton == null){
            synchronized (SingletonDemo.class){
                if (singleton == null){
                    singleton = new SingletonDemo();
                }
            }
        }
        return  singleton;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                SingletonDemo.getInstance();
            }, String.valueOf(i)).start();
        }
    }
}
