package com.krpc.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.junit.Test;

import com.krpc.com.krpc.demo.User;


public class KRPCTest {

	@Test
	public void testInit() {

		try {
			KRPC.init("D://krpc//service//demo//conf//client.xml");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Test
	public void testGetName(){
		
		
		List<List<User>> userss = new ArrayList<List<User>>();
		
		System.out.println(userss.getClass().getName());
		
	}
	
	@Test
	public void testR(){
		
		Class c = TestClass.class;
		
		Class[] cs = c.getMethods()[0].getParameterTypes();
		
		for(Class cc:cs){
			System.out.println(cc.getName());
		}
		
		
	}
	

	@Test
	public void test() {

		String list = "java.util.*List";
		String map = "java.util.*Map";

		Object[] args = new Object[3];

		User user1 = new User();
		user1.setId(1);
		user1.setName("yasin");
		user1.setPhone(18810572463L);

		User user2 = new User();
		user2.setId(2);
		user2.setName("test2");
		user2.setPhone(18810572464L);

		User user3 = new User();
		user3.setId(3);
		user3.setName("test3");
		user3.setPhone(18810572465L);

		List<User> users = new ArrayList<User>();
		users.add(user1);
		users.add(user2);
		users.add(user3);

		Map<String, User> umap = new HashMap<String, User>();
		umap.put("1", user1);
		umap.put("2", user2);
		umap.put("3", user3);

		args[0] = user1;
		args[1] = users;
		args[2] = umap;

		List<Class> classes = new ArrayList<Class>();

		for (Object o : args) {

			String typeName = o.getClass().getName();
			if (Pattern.matches(list, typeName)) {

			} else if (Pattern.matches(map, typeName)) {

			} else {

			}

		}

	}

	String list = "java.util.*List";
	String map = "java.util.*Map";

	public List<String> getClass(Object object,List<String> classes) {


		String typeName = object.getClass().getName();

		if (Pattern.matches(list, typeName)) {
			classes.add(typeName);
			getClass( ((List)object).get(0),classes );
		} else if (Pattern.matches(map, typeName)) {

		}else{
			
		}
		
		return classes;
	}

	@Test
	public void match() {

		String src1 = "java.util.ArrayList";
		String src2 = "java.util.HashMap";

		String list = "java.util.*List";
		String map = "java.util.*Map";

		System.out.println(Pattern.matches(list, src2));

	}

}
