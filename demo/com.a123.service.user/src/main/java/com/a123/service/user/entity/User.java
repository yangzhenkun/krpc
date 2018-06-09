package com.a123.service.user.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户实体类
 * 需要RPC调用的实体类必须实现Serializable接口,才能进行序列化进行TCP通信
 * @author yangzhenkun
 *
 */
public class User implements Serializable{

	private Integer id;
	private String name;
	private Long phone;
	private Date birthDay;
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public long getPhone() {
		return phone;
	}
	public Date getBirthDay() {
		return birthDay;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPhone(long phone) {
		this.phone = phone;
	}
	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", phone=" + phone + ", birthDay=" + birthDay + "]";
	}
}
