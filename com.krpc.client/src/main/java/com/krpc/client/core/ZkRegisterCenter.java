package com.krpc.client.core;

import com.krpc.client.entity.Address;
import com.krpc.client.entity.ServiceParams;
import com.krpc.common.entity.ZookeeperInfo;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yangzhenkun
 * @create 2018-11-26 14:12
 */
public class ZkRegisterCenter {

    private static Logger log = LoggerFactory.getLogger(ZkRegisterCenter.class);
    private static ZkClient zc = null;

    public static void init(ZookeeperInfo zookeeperInfo) {
        if (zc != null) {
            return;
        }

        try {
            zc = new ZkClient(zookeeperInfo.getAddr(), zookeeperInfo.getSessionTimeOut(), zookeeperInfo.getConnectionTimeOut());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("zk init error!", e);
        }
    }

    public static List<Address> getServerAddr(String serverName) {


        List<Address> addresses = new ArrayList<>();
        try {
            StringBuffer path = new StringBuffer("/krpc/");
            path.append(serverName);

            List<String> serverlist = zc.getChildren(path.toString());

            if (serverlist != null && serverlist.size() > 0) {
                for (String ipport : serverlist) {
                    String[] content = ipport.split(":");
                    Address address = new Address();
                    address.setHost(content[0]);
                    address.setPort(Integer.valueOf(content[1]));
                    addresses.add(address);
                }

            }

            subscribe(serverName);
        } catch (Exception e) {
            log.error("get server config from zk error!", e);
        } finally {

            return addresses;
        }
    }

    /**
     * 订阅
     *
     * @param serverName
     */
    public static void subscribe(String serverName) {

        ServiceParams serviceParams = ServiceParams.getService(serverName);
        StringBuffer path = new StringBuffer("/krpc/");
        path.append(serverName);
        zc.subscribeChildChanges(path.toString(), new IZkChildListener() {
            @Override
            public void handleChildChange(String s, List<String> list) throws Exception {

                System.out.println("server change===" + list);
                if (list != null && list.size() > 0) {
                    List<Address> newAddr = new ArrayList<>(list.size());
                    for (String ipport : list) {
                        String[] content = ipport.split(":");
                        Address address = new Address();
                        address.setHost(content[0]);
                        address.setPort(Integer.valueOf(content[1]));

                        newAddr.add(address);
                    }

                    serviceParams.setAddresses(newAddr);
                    log.info("{} server change,content={}", serverName, list.toString());
                } else {
                    serviceParams.getAddresses().clear();
                    log.info("{} server change,no able server!", serverName);
                }

            }
        });

    }


}
