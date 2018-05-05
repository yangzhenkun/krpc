package com.krpc.client.net;

import com.krpc.client.entity.Address;
import com.krpc.common.entity.Request;
import com.krpc.common.serializer.SerializeUtil;

public class RequestHandler {
	
	public static Object request(String serviceName,Request request){
		
		Address addr = LoadBalance.loadbalance(serviceName);
		byte[] requestBytes = SerializeUtil.serialize(request);
		addr.getHost();
		addr.getPort();
		
		//tcp请求
		
		
		
		return new Object();
		
	}
	
	
}
