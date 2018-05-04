package com.krpc.server.entity;

import java.net.URLClassLoader;
import java.util.Map;

public class Global {
	
	private Global(){};
	
	private static class SingleHolder{
		private static final Global INSTANCE = new Global();
	}
	
	/**
	 * 单例
	 * @return
	 */
	public static Global getInstance(){
		return SingleHolder.INSTANCE;
	}
	
	
	//服务应用名字
	private   String serviceName;
	
	//网络连接的一些配置
	private  String ip;
	private  Integer port;
	private  Integer timeout;
	
	//服务缓存
	private  Map<String,Object> serviceImpl;
	
	//服务实现类缓存
	private Map<String,Class> serviceClass;
	
	private URLClassLoader classLoader;

	public String getServiceName() {
		return serviceName;
	}

	public String getIp() {
		return ip;
	}

	public Integer getPort() {
		return port;
	}

	public Integer getTimeout() {
		return timeout;
	}

	public Map<String, Object> getServiceImpl() {
		return serviceImpl;
	}
	
	public Object getServiceImpl(String key){
		return serviceImpl.get(key);
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	public void setServiceImpl(Map<String, Object> serviceImpl) {
		this.serviceImpl = serviceImpl;
	}

	public Map<String, Class> getServiceClass() {
		return serviceClass;
	}
	
	public Class getServiceClass(String key){
		return serviceClass.get(key);
	}

	public void setServiceClass(Map<String, Class> serviceClass) {
		this.serviceClass = serviceClass;
	}

	public URLClassLoader getClassLoader() {
		return classLoader;
	}

	public void setClassLoader(URLClassLoader classLoader) {
		this.classLoader = classLoader;
	} 
	
}
