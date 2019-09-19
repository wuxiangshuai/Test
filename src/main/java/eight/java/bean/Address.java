package eight.java.bean;

import java.util.Optional;

/**
 * @ClassName: Address
 * @Author: WuXiangShuai
 * @Time: 18:13 2019/4/26.
 * @Description:
 */
public class Address {

    private String name;
    private String countryName;

    public Address() {
    }

    public Address(String name) {
        this.name = name;
    }

    public Optional<String> getCountryName() {
        return Optional.ofNullable(countryName);
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
