package com.krpc.client;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;

public class TestClass {
	
	@Test
	public void forBaseType() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException{
		
		Function f = new Function();
		Class clazz = f.getClass();
		
		Class[] pts = {int.class};
		
		try {
//			Method m = clazz.getMethod("testInt", pts);
//			Object obj = m.invoke(clazz.newInstance(), 1);
//			System.out.println(obj);
			
			for(Method m:clazz.getDeclaredMethods()){
				
				System.out.println("方法名字 :"+m.getName());
				for(Class c:m.getParameterTypes()){
					System.out.println(c.getName());
					Class.forName(c.getName());
				}
				
			}
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		
		
		
		
	}
	
}
