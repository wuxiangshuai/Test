package dataStructures.demo8;


/**
 * @ClassName: HashTabDemo
 * @Author: WuXiangShuai
 * @Time: 11:27 2019/9/19.
 * @Description: 哈希表结构
 */
public class HashTabDemo {
    public static void main(String[] args) {
        Emp emp = new Emp(1, "wxs");
        HashTab tab = new HashTab(10);
        tab.add(emp);
        tab.list();
    }
}
// 雇员
class Emp {
    int id;
    String name;
    Emp next;

    public Emp(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
// 哈希表
class HashTab {
    EmpLinkedList[] array;
    int size;

    public HashTab(int size) {
        this.size = size;
        array = new EmpLinkedList[size];
    }

    public void add(Emp emp) {
        int index = hasFun(emp.id);
        EmpLinkedList list = array[index];
        if (null == list) {
            list = new EmpLinkedList();
            array[index] = list;
        }
        list.add(emp);
    }

    public Emp findById (int id) {
        int index = hasFun(id);
        return array[index].findById(id);
    }

    // 遍历链表
    public void list() {
        for (int i = 0; i < size; i++) {
            EmpLinkedList list = array[i];
            if (null == list) {
                System.out.println((i + 1) + " 空");
            } else {
                list.list();
            }
        }
    }

    // 散列函数
    public int hasFun(int id) {
        return id % size;
    }
}

// 链表
class EmpLinkedList {
    private Emp head;
    public void add(Emp emp) {
        if (head == null) {
            head = emp;
            return;
        }
        Emp temp = head.next;
        while (temp != null) {
            temp = temp.next;
        }
        temp.next = emp;
    }

    public Emp findById (int id) {
        Emp temp = head;
        while (temp != null && temp.id != id) {
            temp = temp.next;
        }
        if (temp == null) return null;
        else return temp;
    }

    public void list() {
        if (null == head) {
            System.out.println("空");
            return;
        }
        Emp temp = head;
        while (temp != null) {
            System.out.println(temp.name);
            temp = temp.next;
        }
    }
}
