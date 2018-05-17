package com.krpc.server.core;

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
	
	
	public static byte[] handler(byte[] requestBytes){
		System.out.println("服务端收到的数据:"+requestBytes.length);
		Request request = (Request) SerializeUtil.deserialize(requestBytes,Global.getInstance().getClassLoader());

		Object object = ServiceInvoke.invoke(request);
		System.out.println("服务端返回的类:"+object);
		byte[] response = SerializeUtil.serialize(object);
		System.out.println("服务端返回的数据:"+response.length);
		return response;
		
	}
	
}
