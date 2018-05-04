package com.krpc.client;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.krpc.com.krpc.demo.User;
import com.krpc.com.krpc.demo.UserWrapper;
import com.krpc.common.entity.Request;
import com.krpc.common.serializer.SerializeUtil;

public class Call {

	@Test
	public void testRequest() throws Exception {

		User user = new User();
		user.setId(333);
		user.setName("也是");
		user.setPhone(7348374823L);
		
		UserWrapper uw = new UserWrapper();
		uw.setUser(user);
		uw.setId(111L);
		
		

		Request request = new Request();
		request.setServiceImplName("demoService");
		request.setMethodName("getTest");
		List<Object> requestParams = new ArrayList<Object>();
		requestParams.add(user);
		List<Class> requestTypes = new ArrayList<Class>();
		requestTypes.add(User.class);
		
//		request.setParamsTypes(SerializeUtil.serialize(requestTypes));
//		request.setParamsValues(SerializeUtil.serialize(requestParams));;
		request.setParamsTypes(requestTypes);
		request.setParamsValues(requestParams);
		
		
		byte[] bytes = SerializeUtil.serialize(request);

		SerializeUtil.WriteStringToFile(bytes,"D://request.txt");
//		byte[] r = SerializeUtil.read("D://request.txt");
//		Request request11 = (Request) SerializeUtil.deserialize(r);
//		System.out.println(request11);
//		Object[] rpv = (Object[]) SerializeUtil.deserialize(request11.getParamsValues());
//		for(Object o:rpv){
//			System.out.println(o);
//		}
//		Class[] rpt = (Class[]) SerializeUtil.deserialize(request11.getParamsTypes());
//		
//		for(Class c:rpt){
//			System.out.println(c);
//		}
//		
	}
	
	
	@Test
	public void testResponse() throws Exception{
		
		byte[] res = SerializeUtil.read("D://response.txt");
		Object o = SerializeUtil.deserialize(res);
		System.out.println(o);
		
	}

	@Test
	public void test(){
		
		System.out.println(Integer.class.getName());
		
		
	}
	

}
