package dataStructures.demo2;

/**
 * @ClassName: ArrayQueue
 * @Author: WuXiangShuai
 * @Time: 16:39 2019/9/10.
 * @Description:
 */
public class ArrayQueueDemo {

    public static void main(String[] args) {
        ArrayQueue queue = new ArrayQueue(3);
        queue.addQueue(1);
        queue.addQueue(2);
        queue.addQueue(3);
        queue.addQueue(4);
    }

}

// 使用数组模拟队列
class ArrayQueue {
    private int maxSize; // 最大容量
    private int front;   // 队列头
    private int rear;    // 队列尾部
    private int[] arr; // 模拟队列

    // 创建构造器
    public ArrayQueue(Integer maxSize) {
        this.maxSize = maxSize;
        arr = new int[this.maxSize];
        front = -1; // 指向队列头部前一个位置
        rear = -1;  // 指向队列尾部
    }

    // 判断队列是否已满
    public boolean isFull() {
        return rear - front >= maxSize;
    }

    // 判断队列是否为空
    public boolean isEmpty() {
        return rear == front;
    }

    // 添加数据到队列
    public void addQueue(int n) {
        if (isFull()) { // 队列是否满
            System.out.println("队列已满，不能再加入数据。");
            return;
        }
        ++rear; // 后移
        arr[rear] = n;
    }

    // 获取队列数据
    public int getQueue(int n) {
        if (isEmpty()) { // 队列是否为空
            throw new RuntimeException("队列为空，不能取出数据。");
        }
        ++front; // 首元素下标
        return arr[front];
    }

    // 展示队列所有数据
    public void showQueue() {
        if (isEmpty()) {
            System.out.println("数组为空，无法打印");
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.println("arr[" + i + "] = " + arr[i]);
        }
    }

    // 显示队列头数据
    public void headQueue() {
        if (isEmpty()) {
            System.out.println("队列为空，无法打印首元素");
            return;
        }
        System.out.println(arr[front + 1]);
    }
}