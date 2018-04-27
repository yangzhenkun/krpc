package com.krpc.com.krpc.server;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import org.apache.log4j.helpers.Loader;
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

		try {
			URL[] urls = new URL[1];
			urls[0] = new URL("file:///D:/conf/a.jar");

			URLClassLoader classLoader = new URLClassLoader(urls, ClassLoader.getSystemClassLoader());
			
			Class clazz = classLoader.loadClass("com.krpc.com.krpc.demo.Service");

			Method[] methods = clazz.getMethods();
			Method method = methods[0];
			Class returnClazz = method.getReturnType();
			Object ob = method.invoke(clazz.newInstance(), null);
			
//			byte[] obbytes = SerializeUtil.serialize(ob);
			
//			Class rob = SerializeUtil.unserialize(returnClazz, obbytes);
			
			System.out.println(ob);
			
			//1.问题,如何获得返回值得类型，
			//--2.如果获得服务类的具体路径(利用配置文件)--
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
