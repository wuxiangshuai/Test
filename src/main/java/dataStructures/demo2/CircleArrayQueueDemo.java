package dataStructures.demo2;

/**
 * @ClassName: CircleArrayQueueDemo
 * @Author: WuXiangShuai
 * @Time: 17:15 2019/9/10.
 * @Description:
 */
public class CircleArrayQueueDemo {

    public static void main(String[] args) {
        CircleArrayQueue queue = new CircleArrayQueue(5);
        queue.addQueue(1);
        queue.addQueue(2);
        queue.addQueue(3);
        queue.addQueue(4);
        queue.addQueue(5);
        queue.showQueue();
        System.out.println("\n" + queue.getQueue());
        System.out.println(queue.getQueue());
        System.out.println(queue.getQueue());
        queue.addQueue(6);
        queue.addQueue(7);
        queue.addQueue(8);
        queue.showQueue();
    }

}

// 使用数组模拟队列
class CircleArrayQueue {
    private int maxSize; // 最大容量
    private int front;   // 队列头
    private int rear;    // 队列尾部
    private int[] arr; // 模拟队列

    // 创建构造器
    public CircleArrayQueue(Integer maxSize) {
        this.maxSize = maxSize;
        arr = new int[this.maxSize];
        front = 0; // 指向队列头部前一个位置
        rear = 0;  // 指向队列尾部
    }

    // 判断队列是否已满
    public boolean isFull() {
        return (rear + 1) % maxSize == front;
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
        arr[rear] = n;
        rear = (rear + 1) % maxSize; // rear后移，需考虑环形结构，进行取模操作改变值
    }

    // 获取队列数据
    public int getQueue() {
        if (isEmpty()) { // 队列是否为空
            throw new RuntimeException("队列为空，不能取出数据。");
        }
        // 保存arr[front]的值
        int val = arr[front];
        // 将front后移
        front = (front + 1) % maxSize;
        return val; // 返回保存的值
    }

    // 展示队列所有数据
    public void showQueue() {
        if (isEmpty()) {
            System.out.println("数组为空，无法打印");
            return;
        }
        for (int i = front; i < front + size(); i++) {
            System.out.printf("arr[%d] = %d \n", i % maxSize, arr[i % maxSize]);
        }
    }

    // 当前有效数据个数
    public int size() {
        return (rear + maxSize - front) % maxSize;
    }

    // 显示队列头数据
    public void headQueue() {
        if (isEmpty()) {
            System.out.println("队列为空，无法打印首元素");
            return;
        }
        System.out.println(arr[front]);
    }
}