package eight.java.bean;

import eight.java.bean.Gender;
import eight.java.bean.Grade;

/**
 * @ClassName: Student
 * @Author: WuXiangShuai
 * @Time: 12:37 2019/4/20.
 * @Description:
 */

public class Student {

    private String name;
    private Integer age;
    private Integer haha;
    private Gender gender;
    private Grade grade;

    public Student(String name, Integer age,Integer haha, Gender gender, Grade grade) {
        this.name = name;
        this.age = age;
        this.haha = haha;
        this.gender = gender;
        this.grade = grade;
    }

    public Integer getHaha() {
        return haha;
    }

    public void setHaha(Integer haha) {
        this.haha = haha;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }
}
