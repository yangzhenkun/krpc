package com.krpc.client.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.krpc.client.KRPC;
import com.krpc.client.entity.Address;
import com.krpc.common.entity.Request;
import com.krpc.common.protocal.ProtocalConst;
import com.krpc.common.serializer.HessianUtil;
import com.krpc.common.util.CompressUtil;

/**
 * 选择服务，进行tcp请求
 * @author yangzhenkun
 *
 */
public class RequestHandler {
	
	private static Logger log = LoggerFactory.getLogger(RequestHandler.class);
	
	public static Object request(String serviceName,Request request,Class returnType) throws Exception{
		
		Address addr = LoadBalance.loadbalance(serviceName);
		byte[] requestBytes = CompressUtil.compress(HessianUtil.serialize(request));
//		byte[] rbs = new byte[requestBytes.length+ProtocalConst.P_END_TAG.length];
//		System.arraycopy(requestBytes, 0, rbs, 0, requestBytes.length);
//		System.arraycopy(ProtocalConst.P_END_TAG, 0, rbs, requestBytes.length, ProtocalConst.P_END_TAG.length);
		
//		log.debug("参数:"+requestBytes.length+":::::"+requestBytes[8]);
		System.out.println("客户端发送数据:"+requestBytes.length);
		byte[] responseBytessrc = CompressUtil.uncompress( TCPClient.send(requestBytes, addr.getHost(), addr.getPort(),KRPC.getService(serviceName).getTimeout()));
		System.out.println("客户端接受数据:"+responseBytessrc.length);
		return HessianUtil.deserialize(responseBytessrc,null);
	}
	
	
}
