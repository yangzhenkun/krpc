package com.krpc.client.net;

import com.krpc.client.entity.Address;
import com.krpc.common.entity.Request;
import com.krpc.common.serializer.SerializeUtil;

/**
 * 选择服务，进行tcp请求
 * @author yangzhenkun
 *
 */
public class RequestHandler {
	
	public static Object request(String serviceName,Request request) throws Exception{
		
		Address addr = LoadBalance.loadbalance(serviceName);
		byte[] requestBytes = SerializeUtil.serialize(request);
		
		System.out.println("客户端请求数据:"+requestBytes.length);
		
		//tcp请求
		byte[] responseBytes = TCPClient.send(requestBytes, addr.getHost(), addr.getPort());
		
		
		System.out.println("客户端返回数据:"+responseBytes.length);
		return SerializeUtil.deserialize(responseBytes);
		
	}
	
	
}
