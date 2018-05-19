package com.krpc.com.krpc.demo;

import java.util.ArrayList;
import java.util.List;

public interface UserService {
	
	public User getTest(User user);
	
	public User genericUser(Integer id,String name,Long phone);
	
	public List<User> genericUsers(ArrayList<User> users);
}
