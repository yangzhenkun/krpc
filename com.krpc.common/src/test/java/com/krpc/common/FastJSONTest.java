package com.krpc.common;

import org.junit.Test;

import com.alibaba.fastjson.JSON;

public class FastJSONTest {

	@Test
	public void test() {
		try {
			String data = "\"yasin2222\"";

			Object o = JSON.parseObject(data, String.class,null);

			System.out.println(o);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
