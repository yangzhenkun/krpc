package com.krpc.client;

import org.junit.Test;

public class KRPCTest {

	@Test
	public void testInit(){
		
		try {
			KRPC.init("D://krpc//service//demo//conf//client.xml");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
}
