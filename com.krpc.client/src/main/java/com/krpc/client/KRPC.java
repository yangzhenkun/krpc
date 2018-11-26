package com.krpc.client;

import com.krpc.client.core.ZkRegisterCenter;
import com.krpc.client.entity.Address;
import com.krpc.client.entity.ServiceParams;
import com.krpc.common.entity.ZookeeperInfo;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * _  _______  _____   _____
 * | |/ |  __ \|  __ \ / ____|
 * | ' /| |__) | |__) | |
 * |  < |  _  /|  ___/| |
 * | . \| | \ \| |    | |____
 * |_|\_|_|  \_|_|     \_____|
 *
 * @author yangzhenkun
 */


public class KRPC {

    /**
     * 初始化客户端配置文件
     *
     * @param clientPath
     * @throws Exception
     */
    public static void init(String clientPath) throws Exception {

        // 读取该服务的配置文件
        SAXReader reader = new SAXReader();
        Document document = reader.read(new File(clientPath));
        Element root = document.getRootElement();

        List<Element> serviceNodes = root.elements("Service");

        ZookeeperInfo zookeeperInfo = ZookeeperInfo.createByElement(root);
        if (zookeeperInfo != null) {
            ZkRegisterCenter.init(zookeeperInfo);
        }

        /**
         * 解析所有服务配置信息
         */
        for (Element serviceNode : serviceNodes) {
            String serverName = serviceNode.attributeValue("name");
            ServiceParams serviceParams = new ServiceParams(serverName);
            serviceParams.setServiceName(serverName);
            serviceParams.setTimeout(Integer.valueOf(serviceNode.attributeValue("timeout")));

            /**
             * 如果配置了注册中心，直接获取
             */
            if (zookeeperInfo != null) {
                serviceParams.setAddresses(ZkRegisterCenter.getServerAddr(serverName));
            }

            /**
             * 解析直连ip，如果使用注册中心会覆盖zk中的配置信息
             */
            Element loadBalanceNode = serviceNode.element("Loadbalance");
            if (loadBalanceNode != null) {

                Element serverNode = loadBalanceNode.element("Server");
                List<Element> addrNodes = serverNode.elements("addr");
                List<Address> addresses = serviceParams.getAddresses();
                if (addresses != null && addresses.size() > 0) {
                    addresses.clear();
                } else {
                    addresses = new ArrayList<>();
                }
                for (Element addrNode : addrNodes) {
                    Address addr = new Address();
                    addr.setName(addrNode.attributeValue("name"));
                    addr.setHost(addrNode.attributeValue("host"));
                    addr.setPort(Integer.parseInt(addrNode.attributeValue("port")));
                    addresses.add(addr);
                }

                serviceParams.setAddresses(addresses);
            }

        }

    }

}