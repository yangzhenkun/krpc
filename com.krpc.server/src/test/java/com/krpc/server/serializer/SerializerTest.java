package com.krpc.server.serializer;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

import com.krpc.common.serializer.SerializeUtil;

public class SerializerTest {

	public void test() throws Exception {

		URL[] urls = new URL[2];
		urls[0] = new URL("file:///D:/jar/a.jar");
		urls[1] = new URL("file:///D:/jar/a.impl.jar");

		URLClassLoader classLoader = new URLClassLoader(urls, ClassLoader.getSystemClassLoader());
		Class clazz = classLoader.loadClass("com.krpc.com.krpc.demo.impl.Service");

		Method[] methods = clazz.getMethods();
		Method method = methods[0];

		Object ob = method.invoke(clazz.newInstance(), null);
		Class returnClazz = method.getReturnType();

		byte[] bytes = SerializeUtil.serialize(ob);
		Object bob = SerializeUtil.deserialize(bytes, classLoader);

		System.out.println(bob);
		System.out.println(bytes.length);
		System.out.println(ob);

	}

	public void testLoader() {
		// try {
		// URL[] urls = new URL[2];
		// urls[0] = new URL("file:///D://krpc//service//demo//lib//a.jar");
		// urls[1] = new
		// URL("file:///D://krpc//service//demo//lib//a.impl.jar");
		// URLClassLoader classLoader = new URLClassLoader(urls,
		// ClassLoader.getSystemClassLoader());
		// byte[] requestb = SerializeUtil.read("D://request.txt");
		// Request request = (Request)
		// SerializeUtil.deserialize(requestb,classLoader);
		// System.out.println(request);
		// List<Object> os = (List<Object>)
		// SerializeUtil.deserialize(request.getParamsValues(), classLoader);
		// List<Class> clazz = (List<Class>)
		// SerializeUtil.deserialize(request.getParamsTypes(), classLoader);
		//
		// Object[] os = (Object[]
		// )SerializeUtil.deserialize(request.getParamsValues(), classLoader);
		// Class[] clazz = ( Class[])
		// SerializeUtil.deserialize(request.getParamsTypes(), classLoader);
		//
		// for(Object o:request.getParamsValues()){
		// System.out.println(o);
		// }
		//
		// for(Class c:request.getParamsTypes()){
		// System.out.println(c);
		// }
		//
		// } catch (Exception e) {
		// e.printStackTrace();
		// }

	}

}
