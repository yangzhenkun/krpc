package com.krpc.server.core;

import java.io.IOException;

public class ClassLoaderTest {

	public void test() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {


	}

	
	public void testT() {
		
//		ArrayList<String> d = new ArrayList<>();
//		
//		if(d instanceof ArrayList){
//			System.out.println("ok");
//		}
		
		
		Object d = new Object();
		
		System.out.println(d.getClass().getName());
		
	}
}
