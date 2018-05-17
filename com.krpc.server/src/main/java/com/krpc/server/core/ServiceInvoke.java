package com.krpc.server.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

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

	static Logger log = LoggerFactory.getLogger(ServiceInvoke.class);
	
	public static Object invoke(Request request) {

		Object result = null;
		Object service = Global.getInstance().getServiceImpl(request.getServiceImplName());
		Class clazz = Global.getInstance().getServiceClass(request.getServiceImplName());

			Method method;
			try {
//				Class[] requestParamsType = (Class[]) SerializeUtil.deserialize(request.getParamsTypes(), Global.getInstance().getClassLoader());
//				Object[] requestParamsValue = (Object[]) SerializeUtil.deserialize(request.getParamsValues(), Global.getInstance().getClassLoader());
				
				Class[] requestParamTypes = new Class[request.getParamsValues().size()];
//				request.getParamsTypes().toArray(requestParamTypes);
				Object[] requestParmsValues = new Object[request.getParamsValues().size()];
				request.getParamsValues().toArray(requestParmsValues);
				for(int i=0;i<requestParamTypes.length;i++){
					requestParmsValues[i]=requestParamTypes[i].getClass();
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
