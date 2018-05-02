package com.krpc.com.krpc.server;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

import org.apache.log4j.xml.DOMConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.krpc.com.krpc.server.util.SerializeUtil;

/**
 * 启动器
 * 
 * @author yangzhenkun01
 *
 */
public class BootStrap {

	public static void main(String[] args) {
		// 初始化log4j
		DOMConfigurator.configure("conf/log4j.xml");
		Logger log = LoggerFactory.getLogger(BootStrap.class);
		
		
	}

}
