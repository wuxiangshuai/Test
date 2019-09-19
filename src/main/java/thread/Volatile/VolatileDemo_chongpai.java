package thread.Volatile;

/**
 * @ClassName: VolatileDemo_chongpai
 * @Author: WuXiangShuai
 * @Time: 15:12 2019/6/24.
 * @Description: 指令重排案例
 */
public class VolatileDemo_chongpai {
    int a = 0;
    boolean flag = false;

    public void method01(){
        a = 1;
        flag = true;
    }

    public void method02(){
        if (flag){
            a = a + 5;
            System.out.println("retValue = " + a);
        }
    }
}
