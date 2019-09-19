package eight.java.bean;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Optional;

/**
 * @ClassName: User
 * @Author: WuXiangShuai
 * @Time: 18:12 2019/4/26.
 * @Description:
 */
@ToString
//@NoArgsConstructor
@AllArgsConstructor
public class User {

	private String name;
	private String email;
	private String psw;
	private int age;
	private Address address;
	private Country country;

	/**
	 * @Function: User.java
	 * @Description:
	 */
	public User() {
	}

	public User(String email, String psw) {
		this.email = email;
		this.psw = psw;
	}

	public User(String name, String email, String psw) {
		this.email = email;
		this.psw = psw;
	}

	public Optional<String> getName() {
		return Optional.ofNullable(name);
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPsw() {
		return psw;
	}

	public void setPsw(String psw) {
		this.psw = psw;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Optional<Address> getAddress() {
		return Optional.ofNullable(address);
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}
}
