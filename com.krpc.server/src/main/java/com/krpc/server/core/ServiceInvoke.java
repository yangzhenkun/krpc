package com.krpc.server.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.krpc.common.entity.Request;
import com.krpc.server.entity.Global;

/**
 * 调用真实服务
 * 
 * @author yangzhenkun
 *
 */
public class ServiceInvoke {
	
	private final String LIST_PATTERN = "java.util.*List";
	private final String MAP_PATTERN = "java.util.*Map";
	
	static Logger log = LoggerFactory.getLogger(ServiceInvoke.class);
	
	public static Object invoke(Request request) throws ClassNotFoundException {

		Object result = null;
		Object service = Global.getInstance().getServiceImpl(request.getServiceImplName());
		Class clazz = Global.getInstance().getServiceClass(request.getServiceImplName());

			Method method;
			try {
				Object[] requestParmsValues = new Object[request.getParamsValues().size()];
				Class[] requestParamTypes = new Class[request.getParamsValues().size()];
				
				
				for(int i=0;i<request.getParamsTypesName().size();i++){
					
					String className = request.getParamsTypesName().get(i);
					//基本类型不能通过class.forname获取
					if("byte".equals(className)){
						requestParamTypes[i]=byte.class;
					}else if("short".equals(className)){
						requestParamTypes[i]=short.class;
					}else if("int".equals(className)){
						requestParamTypes[i]=int.class;
					}else if("long".equals(className)){
						requestParamTypes[i]=long.class;
					}else if("float".equals(className)){
						requestParamTypes[i]=float.class;
					}else if("double".equals(className)){
						requestParamTypes[i]=double.class;
					}else if("boolean".equals(className)){
						requestParamTypes[i]=boolean.class;
					}else if("char".equals(className)){
						requestParamTypes[i]=char.class;
					}else{
						requestParamTypes[i]=Class.forName(className, false, Global.getInstance().getClassLoader());
					}
					
					requestParmsValues[i]=request.getParamsValues().get(i);
					
				}
				method = Global.getInstance().getMethod(request.getServiceImplName(), request.getMethodName(), request.getParamsTypesName());
				if(method==null){
					method = clazz.getMethod(request.getMethodName(),requestParamTypes);
					method.setAccessible(true);
					Global.getInstance().putMethod(request.getServiceImplName(), request.getMethodName(), request.getParamsTypesName(), method);
				}
				result = method.invoke(service, requestParmsValues);
				
			} catch (NoSuchMethodException e) {
				log.error("real invokke error",e);
			} catch (SecurityException e) {
				log.error("real invokke error",e);
			} catch (IllegalAccessException e) {
				log.error("real invokke error",e);
			} catch (IllegalArgumentException e) {
				log.error("real invokke error",e);
			} catch (InvocationTargetException e) {
				log.error("real invokke error",e);
			}
			
		

		return result;
	}
	
	
	
	

}
