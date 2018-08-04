package com.krpc.client.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import com.krpc.client.core.RequestHandler;
import com.krpc.common.entity.Request;

public class ProxyHandler implements InvocationHandler {

	private final String LIST_PATTERN = "java.util.*List";
	private final String MAP_PATTERN = "java.util.*Map";

	private String serviceName;
	private String serviceImplName;

	public ProxyHandler(String serviceName, String serviceImplName) {
		this.serviceName = serviceName;
		this.serviceImplName = serviceImplName;
	}

	public Object bind(Class<?>[] interfaces) {

		return Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), interfaces, this);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

		// 构造请求request
		Request request = new Request();
		request.setMethodName(method.getName());
		request.setServiceImplName(serviceImplName);
		request.setParamsValues(Arrays.asList(args));
		Class[] sourceTypes = method.getParameterTypes();
		List<String> paramsTypeName = new ArrayList<String>();
		
		for (int i = 0; i < args.length; i++) {
			paramsTypeName.add(sourceTypes[i].getName());
		}
		request.setParamsTypesName(paramsTypeName);

		Class returnClass = method.getReturnType();

		return RequestHandler.request(serviceName, request, returnClass);
	}
	
	
	/**
	 * 获取该对象值得类路径,支持扫描集合类型的泛型类型
	 * @param type
	 * @param value
	 * @return
	 */
	@Deprecated
	private StringBuilder getClassName(Class type,Object value){
		
		StringBuilder sb = new StringBuilder();
		String typeName = type.getName();
		sb.append(typeName).append(";");
		
		if(value!=null){
			if(Pattern.matches(LIST_PATTERN, typeName)){//list
				if(((List)value).size()>0){
					sb.append((getClassName(((List)value).get(0).getClass(),((List)value).get(0))));
				}
			}else if(Pattern.matches(MAP_PATTERN, typeName)){//map
				if(((Map)value).size()>0){
					Object k = ((Entry)((Map)value).entrySet().iterator().next()).getKey();
					Object v = ((Entry)((Map)value).entrySet().iterator().next()).getValue();
					sb.append(getClassName(k.getClass(),k));
					sb.append(getClassName(v.getClass(),v));
				}
				
				
			}
			
		}
		
		return sb;
	}
	

}
