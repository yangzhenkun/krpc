package com.krpc.server.util;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;

public class Dom4JTest {
	@Test
	public void test() throws Exception{
		
		// 创建saxReader对象  
        SAXReader reader = new SAXReader();  
        // 通过read方法读取一个文件 转换成Document对象  
        Document document = reader.read(new File("D:/krpc/service/demo/conf/service.xml"));  
        //获取根节点元素对象  
        Element node = document.getRootElement();  
       
        Element proNode = node.element("property");
        Element connectionNode = proNode.element("connection");
        Element nettyNode = proNode.element("netty");
        System.out.println(connectionNode.attributeValue("ip"));
        System.out.println(connectionNode.attributeValue("port"));
        System.out.println(connectionNode.attributeValue("timeout"));
        
        System.out.println(nettyNode.attributeValue("workerCount"));
        
        Element servicesNode = node.element("services");
        
        List<Element> serviceList = servicesNode.elements("service");
        for(Element e:serviceList){
        	System.out.println(e.attributeValue("name"));
        	System.out.println(e.attributeValue("impl"));
        }
        
	}

}
