package com.krpc.server.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.krpc.common.entity.Request;
import com.krpc.server.entity.Global;

/**
 * 调用真实服务
 * 
 * @author yangzhenkun
 *
 */
public class ServiceInvoke {

	static Logger log = LoggerFactory.getLogger(ServiceInvoke.class);
	
	public static Object invoke(Request request) throws ClassNotFoundException {

		Object result = null;
		Object service = Global.getInstance().getServiceImpl(request.getServiceImplName());
		Class clazz = Global.getInstance().getServiceClass(request.getServiceImplName());

			Method method;
			try {
				Object[] requestParmsValues = new Object[request.getParamsValues().size()];
				Class[] requestParamTypes = new Class[request.getParamsTypesName().size()];
				
				
				for(int i=0;i<request.getParamsTypesName().size();i++){
					requestParamTypes[i]=Class.forName(request.getParamsTypesName().get(i), false, Global.getInstance().getClassLoader());
					
					if(request.getParamsValues().get(i)==null){
						requestParmsValues[i]=null;
						continue;
					}
					
					if(requestParamTypes[i]==String.class){
						requestParmsValues[i]=request.getParamsValues().get(i).toString();
					}else{
						requestParmsValues[i]=JSON.parseObject(request.getParamsValues().get(i).toString(),requestParamTypes[i]);
					}
					
				}
				
				
				method = clazz.getMethod(request.getMethodName(),requestParamTypes);
				method.setAccessible(true);
				
				for(Parameter p : method.getParameters()){
					System.out.println(p.getType());
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
