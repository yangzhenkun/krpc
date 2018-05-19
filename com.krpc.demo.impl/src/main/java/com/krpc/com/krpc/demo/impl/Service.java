package com.krpc.com.krpc.demo.impl;

import java.util.ArrayList;
import java.util.List;

import com.krpc.com.krpc.demo.User;
import com.krpc.com.krpc.demo.UserService;

public class Service implements UserService{

	public User getTest(User user) {
		return user;
	}

	public User genericUser(Integer id, String name, Long phone) {
		
		User user = new User();
		user.setId(id);
		user.setName(name);
		user.setPhone(phone);
		
		return user;
	}

	public List<User> genericUsers(ArrayList<User> users) {
		
		for(User u:users){
			System.out.println(u.getName());
		}
		
		return users;
	}
	

 }
