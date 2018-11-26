package com.krpc.server.core;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkMarshallingError;
import org.I0Itec.zkclient.serialize.ZkSerializer;
import org.apache.zookeeper.CreateMode;
import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author yangzhenkun
 * @create 2018-11-24 11:30
 */
public class ZKTest {

    private String addr = "";

    private int outTime = 20000;

    private ZkClient zc = null;

    @Before
    public void init() {
        try {
            zc = new ZkClient(addr, outTime, outTime);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Test
    public void run() {

        zc.subscribeChildChanges("/krpc/user", new IZkChildListener() {
            @Override
            public void handleChildChange(String s, List<String> list) throws Exception {

                System.out.println("s====" + s);
                if (list != null && list.size() > 0) {
                    list.forEach(l -> {
                        System.out.println("list====" + l);
                    });
                } else {

                    System.out.println("list====null");
                }

            }
        });

        System.out.println("监听打开");

        try {
            TimeUnit.HOURS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void get() {

        String path = "/krpc/user";
        List<String> serverList = zc.getChildren(path);


        System.out.println(serverList);

    }


    @Test
    public void add() {

//        zc.writeData("/FirstZnode","1");
        if (!zc.exists("/krpc")) {
            zc.create("/krpc", "", CreateMode.PERSISTENT);

            System.out.println("创建基点krpc");
        }
        if (!zc.exists("/krpc/user")) {
            zc.create("/krpc/user", "", CreateMode.PERSISTENT);
            System.out.println("创建user服务节点");
        }


        System.out.println(zc.create("/krpc/user/127.0.0.2:8080", "user", CreateMode.EPHEMERAL));


        try {
            TimeUnit.MINUTES.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }


    public class StringZkSerialize implements ZkSerializer {
        @Override
        public byte[] serialize(Object o) throws ZkMarshallingError {
            try {
                return o.toString().getBytes("utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        public Object deserialize(byte[] bytes) throws ZkMarshallingError {
            try {
                return new String(bytes, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
