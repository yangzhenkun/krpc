package com.krpc.com.krpc.server.serializer;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import org.junit.Test;

import com.krpc.com.krpc.server.util.SerializeUtil;

public class SerializerTest {

	
	@Test
	public void test() throws Exception{
		
		
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
		Object bob = SerializeUtil.deserialize( bytes, classLoader);
		
		System.out.println(bob);
		System.out.println(bytes.length);
		System.out.println(ob);
		
		
	}
	
	
}
