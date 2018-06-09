package com.a123.service.user.impl;

import java.util.Date;
import java.util.List;

import com.a123.service.user.contract.UserService;
import com.a123.service.user.entity.User;

/**
 * 服务实现类
 * @author yangzhenkun
 *
 */
public class UserServiceImpl implements UserService {

	@Override
	public User genericUser(int id, String name, Long phone, Date birthDay) {
		
		User user = new User();
		user.setId(id);
		user.setName(name);
		user.setPhone(phone);
		user.setBirthDay(birthDay);
		
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return user;
	}

	public List<User> users(List<User> us) {
		
		System.out.println("服务日志:"+us.size());
		
		return us;
	}

}
