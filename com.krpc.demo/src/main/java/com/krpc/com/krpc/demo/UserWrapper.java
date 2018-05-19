package com.krpc.com.krpc.demo;

import java.io.Serializable;

public class UserWrapper implements Serializable{

	private static final long serialVersionUID = -1908120951354029876L;

	private User user;
	
	private Long id;

	public User getUser() {
		return user;
	}

	public Long getId() {
		return id;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "UserWrapper [user=" + user + ", id=" + id + "]";
	}
	
	
}
