package tool.validation;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import javax.validation.constraints.DecimalMin;

/**
 * @ClassName: Book
 * @Author: WuXiangShuai
 * @Time: 11:56 2019/6/6.
 * @Description:
 */
public class Book {

    private Integer id;

    @NotBlank(message = "Book对象的name属性不能为空")
    @Length(min = 1, max = 10, message = "Book对象的name属性长度必须在 {min} - {max} 之间")
    private String name;

//    @Min(value = 0l, message = "Book对象的price属性不能低于 {value} ")
    @DecimalMin(value = "0.1", message = "价格不能低于 {value}")
    private double price;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name=" + name +
                ", price=" + price +
                '}';
    }
}
