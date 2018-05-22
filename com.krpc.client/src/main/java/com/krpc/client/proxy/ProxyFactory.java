package com.krpc.client.proxy;

public class ProxyFactory {
	
	/**
	 * 
	 * @param type
	 * @param strUrl krpc://serviceName/serviceImplName
	 * @return
	 */
	public static <T> T  create(Class<?> type, String serviceName,String serviceImpleName) {//<T> T返回任意类型的数据？  返回代理的实例  泛型
    	
		
		ProxyHandler handler = new ProxyHandler(serviceName,serviceImpleName);
		
		
		return (T) handler.bind(new Class<?>[]{type});
	}

}
