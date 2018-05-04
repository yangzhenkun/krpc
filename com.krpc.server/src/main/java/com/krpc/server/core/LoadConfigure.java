package com.krpc.server.core;

import java.io.File;
import java.io.FilenameFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.krpc.server.entity.Global;

/**
 * 加载配置文件，初始化相关配置
 * 
 * @author yangzhenkun
 *
 */
public class LoadConfigure {

	/**
	 * 加载该服务下的配置文件,并初始化相关内容
	 * @param serviceRootPath
	 * @throws Exception
	 */
	public static void load(String serviceRootPath) throws Exception {

		String serviceLib = serviceRootPath + File.separator + "lib";
		String serviceConf = serviceRootPath + File.separator + "conf";

		// 读取该服务的配置文件
		SAXReader reader = new SAXReader();
		Document document = reader.read(new File(serviceConf + File.separator + "service.xml"));
		Element node = document.getRootElement();

		Element proNode = node.element("property");

		Element connectionNode = proNode.element("connection");
		Element nettyNode = proNode.element("netty");

		Global.getInstance().setIp(connectionNode.attributeValue("ip"));
		Global.getInstance().setPort(Integer.parseInt(connectionNode.attributeValue("port")));
		Global.getInstance().setTimeout(Integer.parseInt(connectionNode.attributeValue("timeout")));

		Map<String, String> serviceMap = new HashMap<String, String>();
		Element servicesNode = node.element("services");

		List<Element> serviceList = servicesNode.elements("service");
		for (Element e : serviceList) {
			serviceMap.put(e.attributeValue("name"), e.attributeValue("impl"));
		}

		initService(serviceMap, serviceLib);

	}

	/**
	 * 加载该服务所有的jar，并实例化jar中所有服务的实现
	 * 
	 * @param services
	 * @throws MalformedURLException
	 */
	private static void initService(Map<String, String> services, String serviceLibPath) throws Exception {

		File serviceLibDir = new File(serviceLibPath);

		File[] jarFiles = serviceLibDir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".jar");
			}
		});

		URL[] jarURLS = new URL[jarFiles.length];
		for (int i = 0; i < jarFiles.length; i++) {
			jarURLS[i] = jarFiles[i].toURI().toURL();
		}

		URLClassLoader classLoader = new URLClassLoader(jarURLS, ClassLoader.getSystemClassLoader());
		
		Map<String,Object> instances = new HashMap<String,Object>();
		Map<String,Class> types = new HashMap<String,Class>();
		Iterator<Entry<String, String>> it = services.entrySet().iterator();
		while(it.hasNext()){
			Entry<String, String> e = it.next();
			Class clazz = classLoader.loadClass(e.getValue());
			instances.put(e.getKey(), clazz.newInstance());
			types.put(e.getKey(), clazz);
		}
		
		Global.getInstance().setClassLoader(classLoader);
		Global.getInstance().setServiceImpl(instances);
		Global.getInstance().setServiceClass(types);
	}

}
