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
		
		Request request = (Request) SerializeUtil.deserialize(requestBytes,Global.getInstance().getClassLoader());

		Object object = ServiceInvoke.invoke(request);
		
		return SerializeUtil.serialize(object);
		
	}
	
}
