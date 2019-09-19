package dataStructures.demo2;

/**
 * @ClassName: MyArrayQueueDemo
 * @Author: WuXiangShuai
 * @Time: 11:25 2019/9/11.
 * @Description:
 */
public class MyArrayQueueDemo {

    public static void main(String[] args) {
        MyArrayQeueu qeueu = new MyArrayQeueu(5);
        qeueu.add(1);
        qeueu.add(2);
        qeueu.add(3);
        qeueu.add(4);
        qeueu.add(5);
        qeueu.add(6);
        qeueu.size();
        qeueu.show();
        System.out.println(qeueu.get());
        System.out.println(qeueu.get());
        qeueu.add(5);
        qeueu.add(6);
        System.out.println(qeueu.get());
        qeueu.add(7);
        qeueu.show();
    }

}

class MyArrayQeueu {

    private int front;
    private int rear;
    private int maxSize;
    private int array[];

    public MyArrayQeueu(int maxSize) {
        this.maxSize = maxSize;
        this.front = 0;
        this.rear = 0;
        this.array = new int[maxSize];
    }

    public boolean isEmpty() {
        return rear == front;
    }

    public boolean isFull() {
        return (rear + 1) % maxSize == front;
    }

    public void add(int num) {
        if (isFull()) {
            System.out.println("队列已满，无法添加！");
            return;
        }
        array[rear] = num;
        rear = (rear + 1) % maxSize; // 环形使用数组空间
    }

    public int get() {
        if (isEmpty()) {
            System.out.println("队列为空！");
            return -1;
        }
        int val = array[front];
        front = (front + 1) % maxSize; // 环形使用数组空间
        return val;
    }

    public int size() {
        return (rear + maxSize - front) % maxSize;
    }

    public void show() {
        for (int i = front; i < front + size(); i++) {
            System.out.printf("array[%d] = %d \n", i % maxSize, array[i % maxSize]);
        }
        System.out.println("front : " + front);
        System.out.println("rear  : " + rear);
    }
}
