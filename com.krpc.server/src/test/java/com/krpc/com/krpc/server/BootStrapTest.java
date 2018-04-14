package com.krpc.com.krpc.server;


import org.apache.log4j.xml.DOMConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.krpc.com.krpc.server.entity.User;
import com.krpc.com.krpc.server.util.SerializeUtil;

public class BootStrapTest {

	public static void main(String[] args){
		
		DOMConfigurator.configure("conf/log4j.xml");//xml文件指定方法
		
		byte[] bytes = SerializeUtil.serialize(new User(1,"yasin","18888888888"));
		
		User user = SerializeUtil.unserialize(User.class,bytes);
		
		System.out.println(user);
		
	}
	
}
