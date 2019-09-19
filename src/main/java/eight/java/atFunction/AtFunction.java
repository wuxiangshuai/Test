package eight.java.atFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * @Author: WuXiangShuai
 * @Time: 20:00 2019/4/16.
 * @Description:
 */
public class AtFunction {

    /**
     * Predicate <T> 接口是一个函数式接口，它接受一个输入参数 T，返回一个布尔值结果。
     * 该接口包含多种默认方法来将Predicate组合成其他复杂的逻辑（比如：与，或，非）。
     * 该接口用于测试对象是 true 或 false。
     * @param list
     * @param predicate
     * @param <T>
     * @return
     */
    public <T> List<T> test(List<T> list, Predicate<T> predicate){
        List<T> datas = new ArrayList<>();
        list.stream().forEach(i -> {
            if (predicate.test(i))
                datas.add(i);
        });
        if (datas.size() > 0)
            return datas;
        return null;
    }

}
