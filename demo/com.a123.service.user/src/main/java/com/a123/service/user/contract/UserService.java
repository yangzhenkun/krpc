package com.a123.service.user.contract;

import java.util.Date;
import java.util.List;

import com.a123.service.user.entity.User;

/**
 * 服务接口
 * 
 * @author yangzhenkun
 *
 */
public interface UserService {
	
	public User genericUser(int id,String name,Long phone,Date birthDay);
	
	public List<User> users(List<User> us);
}
