package com.krpc.server;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.xml.DOMConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.krpc.common.serializer.SerializeUtil;

/**
 * 启动器
 * 
 * @author yangzhenkun01
 *
 */
public class BootStrap {

	public static void main(String[] args) {

		if(args.length>0){
			//初始化项目路径
			String serviceName = args[0];
			Global.setServiceName(serviceName);
			String userDir = System.getProperty("user.dir");
			String rootPath = userDir + File.separator+".."+File.separator;
			String rootLib = userDir + File.separator+"lib";
			
			
			String serviceRoot = rootPath+"service"+File.separator+serviceName+File.separator;
			String serviceLib = serviceRoot+"lib";
			String serviceConf = serviceRoot+"conf";
			
			// 初始化log4j
			DOMConfigurator.configure(serviceConf+File.separator+"log4j.xml");
			Logger log = LoggerFactory.getLogger(BootStrap.class);
			
			//获取所有的jar包
			try {
				File serviceLibDir = new File(serviceLib);
				
				File[] jarFiles = serviceLibDir.listFiles(new FilenameFilter() {
					@Override
					public boolean accept(File dir, String name) {
						return name.endsWith(".jar");
					}
				});
				
				URL[] jarURLS = new URL[jarFiles.length];
				for(int i=0;i<jarFiles.length;i++){
					jarURLS[i] = jarFiles[i].toURI().toURL();
				}
				
				URLClassLoader classLoader = new URLClassLoader(jarURLS, ClassLoader.getSystemClassLoader());
				Class clazz = classLoader.loadClass("com.krpc.com.krpc.demo.impl.Service");
				
				
				
				
			} catch (MalformedURLException e) {
				log.error("获取服务lib错误",e);
			} catch (ClassNotFoundException e) {
				log.error("调用真实服务错误",e);
			} catch (Exception e) {
				log.error("初始化服务错误",e);
			} 
			
			
			
			
			
			
		}else{
			System.out.println("请输入启动的服务名字");
		}
		
		
	}

}
