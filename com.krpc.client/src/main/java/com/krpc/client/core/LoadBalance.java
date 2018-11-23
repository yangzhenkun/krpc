package com.krpc.client.core;

import java.util.concurrent.atomic.AtomicInteger;

import com.krpc.client.KRPC;
import com.krpc.client.entity.Address;
import com.krpc.client.entity.ServiceParams;

/**
 * 负载均衡相关
 * @author yangzhenkun
 *
 */
public class LoadBalance {
	
	/**
	 * 获取一个服务地址
	 * 策略：随机
	 * @param serviceName
	 * @return
	 */
	public static Address loadbalanceRandom(String serviceName){
		ServiceParams serviceParams = KRPC.getService(serviceName);1
		int total = serviceParams.getAddresses().size();
		int index = (int) (System.currentTimeMillis()%total);
		
		return serviceParams.getAddresses().get(index);
	}
	
	private static AtomicInteger count = new AtomicInteger(0);
	
	
	public static Address loadbalanceUniformity(String serviceName) {
		ServiceParams serviceParams = KRPC.getService(serviceName);
		int total = serviceParams.getAddresses().size();
		return serviceParams.getAddresses().get(count.getAndIncrement()%total);

		
	}
	
	
	
}
