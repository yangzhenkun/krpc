package com.krpc.client.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

import com.krpc.client.net.RequestHandler;
import com.krpc.common.entity.Request;

public class ProxyHandler implements InvocationHandler {
	
	private String serviceName;
	private String serviceImplName;
	
	public ProxyHandler(String serviceName,String serviceImplName){
		this.serviceName=serviceName;
		this.serviceImplName=serviceImplName;
	}
	
	
	public Object bind(Class<?>[] interfaces){
		
		return Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), interfaces, this);
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		
		//构造请求request
		Request request = new Request();
		request.setMethodName(method.getName());
		request.setServiceImplName(serviceImplName);
		Class[] clazz = method.getParameterTypes();
		request.setParamsTypes(Arrays.asList(clazz));
		request.setParamsValues(Arrays.asList(args));
		
		
		return RequestHandler.request(serviceName, request);
	}

}
