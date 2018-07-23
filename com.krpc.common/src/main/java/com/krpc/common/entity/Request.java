package com.krpc.common.entity;

import java.io.Serializable;
import java.util.List;

/**
 * RPC调用请求
 * 
 * @author yangzhenkun
 *
 */
public class Request implements Serializable {
	private static final long serialVersionUID = -6060365745498911171L;
	// 服务名实现类名字
	private String serviceImplName;
	// 方法名
	private String methodName;
	// 调用方法参数的 Class路径
	private List<String> paramsTypesName;

	// 调用方法参数的 实例,顺序与上面的Class保持一致
	private List<Object> paramsValues;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getServiceImplName() {
		return serviceImplName;
	}

	public String getMethodName() {
		return methodName;
	}

	public List<Object> getParamsValues() {
		return paramsValues;
	}

	public void setServiceImplName(String serviceImplName) {
		this.serviceImplName = serviceImplName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public void setParamsValues(List<Object> paramsValues) {
		this.paramsValues = paramsValues;
	}

	public List<String> getParamsTypesName() {
		return paramsTypesName;
	}

	public void setParamsTypesName(List<String> paramsTypesName) {
		this.paramsTypesName = paramsTypesName;
	}

	@Override
	public String toString() {
		return "Request [serviceImplName=" + serviceImplName + ", methodName=" + methodName + ", paramsTypesName="
				+ paramsTypesName + ", paramsValues=" + paramsValues + "]";
	}
}
