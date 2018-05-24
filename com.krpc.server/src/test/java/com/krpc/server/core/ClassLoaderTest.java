package com.krpc.server.core;

import java.io.IOException;

import org.junit.Test;

public class ClassLoaderTest {

	@Test
	public void test() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {


	}

	
	@Test
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
