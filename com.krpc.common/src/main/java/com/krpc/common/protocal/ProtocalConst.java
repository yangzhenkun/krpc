package com.krpc.common.protocal;

public class ProtocalConst {
	 
	/**
     * 初始标记
     */
    public static final byte[] P_START_TAG = new byte[]{1,2,3,4,5};
    
    /**
     * 结束标记
     */
    public static final byte[] P_END_TAG = new byte[]{5,4,3,2,1};
    
    public static final Integer SESSION_ID_LENGTH = 5;
}
