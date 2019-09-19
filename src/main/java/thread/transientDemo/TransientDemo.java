package thread.transientDemo;


import java.io.*;

/**
 * @ClassName: TransientDemo
 * @Author: WuXiangShuai
 * @Time: 9:24 2019/6/25.
 * @Description: Java中transient关键字的作用，简单地说，就是让某些被修饰的成员属性变量不被序列化
 * 例如：类中的字段值可以根据其它字段推导出来，如一个长方形类有三个属性：长度、宽度、面积（示例而已，一般不会这样设计），
 *      那么在序列化的时候，面积这个属性就没必要被序列化了；
 */
class Rectangle implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1710022455003682613L;
    private Integer width;
    private Integer height;
    private transient Integer area;

    public Rectangle (Integer width, Integer height){
        this.width = width;
        this.height = height;
        this.area = width * height;
    }

    public void setArea(){
        this.area = this.width * this.height;
    }

    @Override
    public String toString(){
        StringBuffer sb = new StringBuffer(40);
        sb.append("width : ");
        sb.append(this.width);
        sb.append("\nheight : ");
        sb.append(this.height);
        sb.append("\narea : ");
        sb.append(this.area);
        return sb.toString();
    }
}

public class TransientDemo {
    public static void main(String args[]) throws Exception {
        Rectangle rectangle = new Rectangle(3,4);
        System.out.println("1.原始对象\n"+rectangle);
        ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream("rectangle"));
        // 往流写入对象
        o.writeObject(rectangle);
        o.close();

        // 从流读取对象
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("rectangle"));
        Rectangle rectangle1 = (Rectangle)in.readObject();
        System.out.println("2.反序列化后的对象\n"+rectangle1);
        rectangle1.setArea();
        System.out.println("3.恢复成原始对象\n"+rectangle1);
        in.close();
    }
}
