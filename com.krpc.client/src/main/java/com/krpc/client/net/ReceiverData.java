package com.krpc.client.net;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author: yangzhenkun01
 * @Date: 2018/6/19
 */
public class ReceiverData {

    /**
     * 服务端返回来的数据
     */
    private byte[] data;

    /**
     * 用于阻塞查询数据的
     */
    private CountDownLatch countDownLatch;

    public ReceiverData() {
        countDownLatch = new CountDownLatch(1);
    }

    public byte[] getData(long waitTime) throws InterruptedException {
    	
        countDownLatch.await(waitTime,TimeUnit.MILLISECONDS);
    	
    	return data;
    }

    public void setData(byte[] data) {
        this.data = data;
        countDownLatch.countDown();
    }
    

}
