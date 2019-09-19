package eight.java.bean;

/**
 * @Author: WuXiangShuai
 * @Time: 20:12 2019/4/16.
 * @Description:
 */
public class Nav {

    private String name;
    private Integer age;

    public Nav() {
    }

    public Nav(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public Nav setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public Nav setAge(Integer age) {
        this.age = age;
        return this;
    }

    @Override
    public String toString() {
        return "Nav{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
