package juc;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @ClassName: CallableAndFuture
 * @Author: WuXiangShuai
 * @Time: 11:54 2019/6/25.
 * @Description:
 */
class MyThread implements Callable<Integer>{

    int score = 0;

    @Override
    public Integer call() throws Exception {
        Thread.sleep(2000);
        System.out.println(Thread.currentThread().getName() + "\t come in call !");
        score += 200;
        return score;
    }
}

class MyThread1 implements Callable<Integer>{

    @Override
    public Integer call() throws Exception {
        Thread.sleep(3000);
        System.out.println(Thread.currentThread().getName() + "\t come in call !");
        return 300;
    }
}

public class CallableAndFuture {
    public static void main(String[] args) {
        FutureTask<Integer> task = new FutureTask(new MyThread());
        FutureTask<Integer> taskB = new FutureTask(new MyThread1());
//        new Thread(task, "AA").start();
        task.run();
        Thread threadB = new Thread(taskB, "BB");
        threadB.start();
//        task.run();
//        taskB.run();

        System.out.println(Thread.currentThread().getName() + " --- main !");

        try {
            System.out.println("hahahaha");
            Integer result = task.get();
            System.out.println("result 1 = " + result);
            System.out.println(task.isDone());
            Integer result2 = task.get();
            System.out.println("result 2 = " + result2);

//            new Thread(taskB, "BB").start();
            System.out.println(taskB.isDone());//判断任务是否完成
            System.out.println(taskB.isCancelled());//判断任务是否取消
//            taskB.cancel(true);//终端任务
            System.out.println(taskB.isCancelled());
            System.out.println(taskB.isDone());
            Integer resultB = taskB.get();
            System.out.println("result B = " + resultB);

            System.out.println("result + = " + (result + resultB));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
