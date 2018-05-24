package com.krpc.client.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.krpc.client.entity.Address;
import com.krpc.common.entity.Request;
import com.krpc.common.serializer.HessianUtil;

/**
 * 选择服务，进行tcp请求
 * @author yangzhenkun
 *
 */
public class RequestHandler {
	
	private static Logger log = LoggerFactory.getLogger(RequestHandler.class);
	
	public static Object request(String serviceName,Request request,Class returnType) throws Exception{
		
		Address addr = LoadBalance.loadbalance(serviceName);
		byte[] requestBytes = HessianUtil.serialize(request);
		byte[] responseBytes = TCPClient.send(requestBytes, addr.getHost(), addr.getPort());
		
		return HessianUtil.deserialize(responseBytes,Thread.currentThread().getContextClassLoader());
	}
	
	
}
