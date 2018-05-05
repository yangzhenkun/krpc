package com.krpc.client.proxy;

public class ProxyFactory {
	
	/**
	 * 
	 * @param type
	 * @param strUrl krpc://serviceName/serviceImplName
	 * @return
	 */
	public static <T> T  create(Class<?> type, String serviceAddress) {//<T> T返回任意类型的数据？  返回代理的实例  泛型
    	
		serviceAddress = serviceAddress.replace("krpc://", "");
		String[] params = serviceAddress.split("/");
		
		
		ProxyHandler handler = new ProxyHandler(params[0],params[1]);
		
		
		return (T) handler.bind(new Class<?>[]{type});
	}

}
