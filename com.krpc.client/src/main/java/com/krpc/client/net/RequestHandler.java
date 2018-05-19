package com.krpc.client.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.krpc.client.entity.Address;
import com.krpc.common.entity.Request;

/**
 * 选择服务，进行tcp请求
 * @author yangzhenkun
 *
 */
public class RequestHandler {
	
	private static Logger log = LoggerFactory.getLogger(RequestHandler.class);
	
	public static Object request(String serviceName,Request request,Class returnType) throws Exception{
		
		Address addr = LoadBalance.loadbalance(serviceName);
		byte[] requestBytes = JSON.toJSONBytes(request);
		
		
		byte[] responseBytes = TCPClient.send(requestBytes, addr.getHost(), addr.getPort());
		
		if(returnType==String.class){
			return new String(responseBytes);
		}
		
		return JSON.parseObject(responseBytes, returnType,null);
	}
	
	
}
