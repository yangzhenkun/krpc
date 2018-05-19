package com.krpc.server.core;

import com.alibaba.fastjson.JSON;
import com.krpc.common.entity.Request;
import com.krpc.common.serializer.SerializeUtil;
import com.krpc.server.entity.Global;

/**
 * 请求处理
 * 
 * @author yangzhenkun
 *
 */
public class RequestHandler {
	
	
	public static byte[] handler(byte[] requestBytes) throws ClassNotFoundException{
		Request request = JSON.parseObject(requestBytes,Request.class,null);
		Object object = ServiceInvoke.invoke(request);
		byte[] response = JSON.toJSONBytes(object);
		return response;
		
	}
	
}
