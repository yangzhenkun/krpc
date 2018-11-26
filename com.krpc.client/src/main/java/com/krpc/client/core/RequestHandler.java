package com.krpc.client.core;

import com.krpc.client.entity.Address;
import com.krpc.client.entity.ServiceParams;
import com.krpc.client.net.TCPClient;
import com.krpc.common.entity.Request;
import com.krpc.common.serializer.HessianUtil;
import com.krpc.common.util.CompressUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 选择服务，进行tcp请求
 *
 * @author yangzhenkun
 */
public class RequestHandler {

    private static Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private static Map<Address, TCPClient> tcpClientCache = new ConcurrentHashMap();

    private static Object lockHelper = new Object();

    public static Object request(String serviceName, Request request, Class returnType) throws Exception {

        Address addr = LoadBalance.loadbalanceRandom(serviceName);

        byte[] requestBytes = CompressUtil.compress(HessianUtil.serialize(request));

        TCPClient tcpClient = getTCPClient(addr, ServiceParams.getService(serviceName).getTimeout());

        log.debug("客户端发送数据:{}", requestBytes.length);
        Integer sessionID = tcpClient.sendMsg(requestBytes);
        if (Objects.isNull(sessionID)) {
            throw new Exception("send data error!");
        }

        byte[] responseBytessrc = tcpClient.getData(sessionID);
        return HessianUtil.deserialize(CompressUtil.uncompress(responseBytessrc), null);
    }

    private static TCPClient getTCPClient(Address address, Integer timeout) throws IOException {
        TCPClient tcpClient = tcpClientCache.get(address);
        if (Objects.isNull(tcpClient)) {

            synchronized (lockHelper) {
                tcpClient = tcpClientCache.get(address);
                if (Objects.isNull(tcpClient)) {
                    tcpClient = new TCPClient(address.getHost(), address.getPort(), timeout);
                    tcpClientCache.put(address, tcpClient);
                }

            }

        }

        return tcpClient;
    }

}
