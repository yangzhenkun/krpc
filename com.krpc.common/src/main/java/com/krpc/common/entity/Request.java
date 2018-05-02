package com.krpc.common.entity;

import java.io.Serializable;
import java.util.Map;

/**
 * RPC调用请求
 * 
 * @author yangzhenkun
 *
 */
public class Request implements Serializable{

	private static final long serialVersionUID = -3424112505762173831L;
	
	//服务名实现类名字
	private String serviceName;
	//方法名
	private String methodName;
	//方法参数
	private Map<String,Object> params;
	
	
	public Request(String serviceName, String methodName, Map<String, Object> params) {
		super();
		this.serviceName = serviceName;
		this.methodName = methodName;
		this.params = params;
	}

	public String getServiceName() {
		return serviceName;
	}

	public String getMethodName() {
		return methodName;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
	
	
}
