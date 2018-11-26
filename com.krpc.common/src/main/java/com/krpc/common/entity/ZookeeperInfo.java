package com.krpc.common.entity;

import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yangzhenkun
 * @create 2018-11-26 11:37
 */
public class ZookeeperInfo {

    private static Logger log = LoggerFactory.getLogger(ZookeeperInfo.class);

    public static ZookeeperInfo createByElement(Element root) {
        /**
         * 初始化注册中心数据
         */
        ZookeeperInfo zookeeperInfo = null;
        try {
            Element zkNode = root.element("zk");
            if (zkNode != null) {
                String sessionTimeStr = zkNode.attributeValue("sessionTimeOut");
                String connectionTimeOutStr = zkNode.attributeValue("connectionTimeOut");
                Element addrNode = zkNode.element("addr");
                String addr = addrNode.getData().toString();

                zookeeperInfo = new ZookeeperInfo(addr, Integer.valueOf(sessionTimeStr), Integer.valueOf(connectionTimeOutStr));
            }
        } catch (Exception e) {
            log.error("get zk info from server.xml error!",e);
        } finally {
            return zookeeperInfo;
        }
    }

    public ZookeeperInfo(String addr, int sessionTimeOut, int connectionTimeOut) {
        this.addr = addr;
        this.sessionTimeOut = sessionTimeOut;
        this.connectionTimeOut = connectionTimeOut;
    }

    private String addr;

    private int sessionTimeOut;

    private int connectionTimeOut;

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public int getSessionTimeOut() {
        return sessionTimeOut;
    }

    public void setSessionTimeOut(int sessionTimeOut) {
        this.sessionTimeOut = sessionTimeOut;
    }

    public int getConnectionTimeOut() {
        return connectionTimeOut;
    }

    public void setConnectionTimeOut(int connectionTimeOut) {
        this.connectionTimeOut = connectionTimeOut;
    }
}
