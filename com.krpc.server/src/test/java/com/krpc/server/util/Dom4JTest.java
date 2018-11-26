package com.krpc.server.util;

import java.io.File;
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
        Document document = reader.read(new File("/opt/krpc-dev/krpc/demo/config_file_template/server/service.xml"));
        //获取根节点元素对象  
        Element node = document.getRootElement();  
       
        Element zkNode = node.element("zk1");


        System.out.println(zkNode.attribute("sessionTimeOut"));


        Element addrNode = zkNode.element("addr");


        System.out.println(addrNode.getData().toString());
	}

}
