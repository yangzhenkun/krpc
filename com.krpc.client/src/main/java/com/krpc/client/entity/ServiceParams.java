package com.krpc.client.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 服务参数
 *
 * @author yangzhenkun
 */
public class ServiceParams {

    private static Map<String, ServiceParams> serviceCache = new HashMap<String, ServiceParams>();

    private int timeout;
    private List<Address> addresses;
    private String serviceName;

    public ServiceParams(String serverName){
        this.serviceName=serverName;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
        serviceCache.put(serviceName,this);
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
        serviceCache.put(serviceName,this);
    }


    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
        serviceCache.put(serviceName,this);
    }

    public static ServiceParams getService(String serviceName){
        return serviceCache.get(serviceName);
    }
}
