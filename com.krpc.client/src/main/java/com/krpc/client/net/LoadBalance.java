package com.krpc.client.net;

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
	public static Address loadbalance(String serviceName){
		ServiceParams serviceParams = KRPC.getService(serviceName);
		int total = serviceParams.getAddresses().size();
		int index = (int) (System.currentTimeMillis()%total);
		
		return serviceParams.getAddresses().get(index);
	}
	
	
}
