package com.krpc.com.krpc.demo.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.krpc.com.krpc.demo.User;
import com.krpc.com.krpc.demo.UserService;
import com.krpc.com.krpc.demo.UserWrapper;

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

	public Map<String, User> userMap(Map<String, User> umap) {
		umap.forEach((k,v)->{
			System.out.println(k);
			System.out.println(v);
		});
		
		return umap;
	}

	public UserWrapper uw(UserWrapper uw) {
		
		System.out.println(uw.getUser().getName());
	
		return uw;
	}
	

 }
