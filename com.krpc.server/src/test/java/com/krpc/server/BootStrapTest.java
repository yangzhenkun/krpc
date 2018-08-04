package com.krpc.server;


import com.krpc.common.serializer.SerializeUtil;
import com.krpc.server.entity.User1;

public class BootStrapTest {

	public static void main(String[] args){
		
		
		byte[] bytes = SerializeUtil.serialize(new User1(1,"yasin","18888888888"));
		
		
		
	}
	
}
