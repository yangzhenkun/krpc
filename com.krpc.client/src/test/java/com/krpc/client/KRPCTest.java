package com.krpc.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
	public void testGetName() {

		List<List<User>> userss = new ArrayList<List<User>>();

		System.out.println(userss.getClass().getName());

	}

	@Test
	public void testR() {

		Class c = TestClass.class;

		Class[] cs = c.getMethods()[0].getParameterTypes();

		for (Class cc : cs) {
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

	public List<String> getClass(Object object, List<String> classes) {

		String typeName = object.getClass().getName();

		if (Pattern.matches(list, typeName)) {
			classes.add(typeName);
			getClass(((List) object).get(0), classes);
		} else if (Pattern.matches(map, typeName)) {

		} else {

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

	@Test
	public void map() {

		Map<List<String>, Map<String, String>> testMap1 = new HashMap<>();
		Map<String, String> testMap = new HashMap();
		testMap.put("1", "one");

		List<String> klist = new ArrayList<>();
		klist.add("一");

		testMap1.put(klist, testMap);

		Object t = testMap1;

//		StringBuilder pnames = getClassName(t.getClass(), t);
//		System.out.println(pnames.toString());
		
//		byte[] requestBytes = JSON.toJSONBytes(testMap1);
		
//		Map request = JSON.parseObject(requestBytes,Map.class,null);	
		
//		request.forEach((k,v)->{
//			System.out.println(((List)k).get(0)); 
//			System.out.println(  ((Map)v).get("1") );
//		});
		
		
		// Object k = ((Entry) ((Map) t).entrySet().iterator().next()).getKey();
		// Object v = ((Entry) ((Map)
		// t).entrySet().iterator().next()).getValue();
		//
		// System.out.println(k.getClass().getName());
		// System.out.println(v.getClass().getName());

	}

	private StringBuilder getClassName(Class type, Object value) {

		StringBuilder sb = new StringBuilder();
		String typeName = type.getName();
		sb.append(typeName).append(";");

		if (value != null) {
			if (Pattern.matches("java.util.*List", typeName)) {// list
				if (((List) value).size() > 0) {
					sb.append((getClassName(((List) value).get(0).getClass(), ((List) value).get(0))));
				}
			} else if (Pattern.matches("java.util.*Map", typeName)) {// map
				if (((Map) value).size() > 0) {
					Object k = ((Entry) ((Map) value).entrySet().iterator().next()).getKey();
					Object v = ((Entry) ((Map) value).entrySet().iterator().next()).getValue();
					sb.append(getClassName(k.getClass(), k));
					sb.append(getClassName(v.getClass(), v));
				}

			}

		}

		return sb;
	}

}
