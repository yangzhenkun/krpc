package com.krpc.client.core;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.krpc.client.KRPC;
import com.krpc.client.entity.Address;
import com.krpc.client.net.KRPCSocket;
import com.krpc.common.entity.Request;
import com.krpc.common.serializer.HessianUtil;
import com.krpc.common.util.CompressUtil;

/**
 * 选择服务，进行tcp请求
 * 
 * @author yangzhenkun
 *
 */
public class RequestHandler {

	private static Logger log = LoggerFactory.getLogger(RequestHandler.class);

	private static Map<Address, KRPCSocket> socketCache = new ConcurrentHashMap();

	private static Object lockHelper = new Object();

	public static Object request(String serviceName, Request request, Class returnType) throws Exception {

		Address addr = LoadBalance.loadbalance(serviceName);
		byte[] requestBytes = CompressUtil.compress(HessianUtil.serialize(request));

		KRPCSocket socket = getSocket(addr);

		log.debug("客户端发送数据:{}" , requestBytes.length);
		int sessionID = socket.send(requestBytes);

		byte[] responseBytessrc = socket.getData(sessionID, KRPC.getService(serviceName).getTimeout());
		return HessianUtil.deserialize( CompressUtil.uncompress(responseBytessrc), null);

	}

	private static KRPCSocket getSocket(Address address) throws IOException {
		KRPCSocket socket = socketCache.get(address);
		if (Objects.isNull(socket)) {

			synchronized (lockHelper) {
				socket = socketCache.get(address);
				if (Objects.isNull(socket)) {
					socket = new KRPCSocket(address.getHost(), address.getPort());
					socketCache.put(address, socket);
				}

			}

		}

		return socket;
	}

}
