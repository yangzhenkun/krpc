package com.krpc.server.core;

import java.io.IOException;

import com.krpc.common.entity.Request;
import com.krpc.common.serializer.HessianUtil;
import com.krpc.server.entity.Global;

/**
 * 请求处理
 * 
 * @author yangzhenkun
 *
 */
public class RequestHandler {
	
	
	public static byte[] handler(byte[] requestBytes) throws ClassNotFoundException, IOException{
		Request request = (Request) HessianUtil.deserialize(requestBytes,Global.getInstance().getClassLoader());
		
		Object object = ServiceInvoke.invoke(request);
		byte[] response = HessianUtil.serialize(object);
		return response;
		
	}
	
}
