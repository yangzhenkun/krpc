package com.krpc.com.krpc.demo;

import java.io.Serializable;

import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;

public class User implements Serializable{
	
	private static final long serialVersionUID = 1889297357709451926L;
	@Protobuf
	private Integer id;
	@Protobuf
	private String name;
	@Protobuf
	private Long phone;
	
	public Integer getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public Long getPhone() {
		return phone;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPhone(Long phone) {
		this.phone = phone;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", phone=" + phone + "]";
	}

		
}
