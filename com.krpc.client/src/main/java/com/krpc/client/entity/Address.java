package com.krpc.client.entity;

/**
 * 连接参数
 * @author yangzhenkun
 *
 */
public class Address {
	
	private String name;
	private String host;
	private Integer port;
	
	
	public String getName() {
		return name;
	}
	public String getHost() {
		return host;
	}
	public Integer getPort() {
		return port;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	
	
	
}
