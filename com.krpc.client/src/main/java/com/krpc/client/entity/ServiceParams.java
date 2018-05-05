package com.krpc.client.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 服务参数
 * @author yangzhenkun
 *
 */
public class ServiceParams {

	private String deadTimeout;
	private List<Address> addresses;
	private String serviceName;
	
	public String getDeadTimeout() {
		return deadTimeout;
	}
	public List<Address> getAddresses() {
		return addresses;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setDeadTimeout(String deadTimeout) {
		this.deadTimeout = deadTimeout;
	}
	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
	public void addAddress(Address address){
		if(addresses==null){
			addresses = new ArrayList<Address>();
		}
		addresses.add(address);
	}
	
	public void removeAddress(int index){
		if(addresses!=null){
			addresses.remove(index);
		}
		
	}
	
}
