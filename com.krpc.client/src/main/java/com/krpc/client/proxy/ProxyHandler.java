package com.krpc.client.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
		request.setParamsValues(Arrays.asList(args));
		
		//调用方法类型 名字
		List<String> paramsTypeName = new ArrayList<String>();
		Class[] sourceTypes = method.getParameterTypes();
		
		
		for(int i=0;i<args.length;i++){
			String typeName = null;
			if(args[i]!=null){
				typeName = args[i].getClass().getName();
			}else{
				typeName = sourceTypes[i].getName();
			}
			
			paramsTypeName.add( typeName );
		}
		
		
		request.setParamsTypesName(paramsTypeName);
		
		Class returnClass = method.getReturnType();
		
		
		return RequestHandler.request(serviceName, request,returnClass);
	}

}
