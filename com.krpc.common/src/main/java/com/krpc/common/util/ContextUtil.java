package com.krpc.common.util;

import java.util.Arrays;

import com.krpc.common.protocal.ProtocalConst;
import com.krpc.common.serializer.HessianUtil;

/**
 * 消息传输工具类
 * 
 * @author yangzhenkun
 *
 */
public class ContextUtil {

	/**
	 * 从数据中取出sessionID
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static Integer getSessionID(byte[] data) throws Exception {

		byte[] sessionData = Arrays.copyOfRange(data, 0, ProtocalConst.SESSION_ID_LENGTH);

		return (Integer) HessianUtil.deserialize(sessionData, null);
	}

	/**
	 * 将sessionID和数据合并
	 * 
	 * @param sessionId
	 * @param data
	 */
	public static byte[] mergeSessionID(Integer sessionId, byte[] data) throws Exception {

		byte[] newArr = new byte[data.length + ProtocalConst.SESSION_ID_LENGTH];
		System.arraycopy(HessianUtil.serialize(sessionId), 0, newArr, 0, ProtocalConst.SESSION_ID_LENGTH);
		System.arraycopy(data, 0, newArr, ProtocalConst.SESSION_ID_LENGTH, data.length);

		return newArr;
	}

	/**
	 * 获得传输真实的消息体
	 * 
	 * @param data
	 * @return
	 */
	public static byte[] getBody(byte[] data) {

		byte[] newArr = new byte[data.length - ProtocalConst.SESSION_ID_LENGTH];
		System.arraycopy(data, ProtocalConst.SESSION_ID_LENGTH, newArr, 0, data.length - ProtocalConst.SESSION_ID_LENGTH);

		return newArr;
	}

}
