package com.krpc.client.pool;

import java.util.concurrent.CountDownLatch;

/**
 * @author: yangzhenkun01
 * @Date: 2018/6/19
 */
public class ReceiverData {

    private int sessionID;

    /**
     * 服务端返回来的数据
     */
    private byte[] data;

    /**
     * 用于阻塞查询数据的
     */
    private CountDownLatch countDownLatch;

    public ReceiverData(int sessionID) {
        this.sessionID = sessionID;
        countDownLatch = new CountDownLatch(1);
    }

    public int getSessionID() {
        return sessionID;
    }

    public void setSessionID(int sessionID) {
        this.sessionID = sessionID;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public CountDownLatch getCountDownLatch() {
        return countDownLatch;
    }

    public void setCountDownLatch(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }
}
