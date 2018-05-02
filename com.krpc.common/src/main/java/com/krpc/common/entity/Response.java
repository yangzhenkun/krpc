package com.krpc.common.entity;

import java.io.Serializable;

/**
 * RPC调用返回的结果
 * 
 * @author yangzhenkun
 *
 */
public class Response implements Serializable{

	private static final long serialVersionUID = 4235979493889293157L;
	
	private byte[] data;
	
	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
	
	
	
}
