package com.krpc.server.register;

import com.krpc.server.entity.Global;
import com.krpc.common.entity.ZookeeperInfo;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 注册中心处理
 *
 * @author yangzhenkun
 * @create 2018-11-24 17:53
 */
public class ZkRegisterCenter {

    private static Logger log = LoggerFactory.getLogger(ZkRegisterCenter.class);

    private static ZkClient zc;

    public static void register() {
        try {

            ZookeeperInfo zookeeperInfo = Global.getInstance().getZookeeperInfo();
            zc = new ZkClient(zookeeperInfo.getAddr(), zookeeperInfo.getSessionTimeOut(), zookeeperInfo.getConnectionTimeOut());

            StringBuffer stringBuffer = new StringBuffer("/krpc");

            if (!zc.exists(stringBuffer.toString())) {
                zc.create(stringBuffer.toString(), "", CreateMode.PERSISTENT);

                log.info("创建根节点krpc");
            }
            stringBuffer.append("/").append(Global.getInstance().getServiceName());

            if (!zc.exists(stringBuffer.toString())) {
                zc.create(stringBuffer.toString(), "", CreateMode.PERSISTENT);
                log.info("创建{}服务节点", Global.getInstance().getServiceName());
            }

            stringBuffer.append("/").append(Global.getInstance().getIp()).append(":").append(Global.getInstance().getPort());

            zc.create(stringBuffer.toString(), Global.getInstance().getServiceName(), CreateMode.EPHEMERAL);
        } catch (Exception e) {
            log.error("register error!", e);
        }
    }


}
