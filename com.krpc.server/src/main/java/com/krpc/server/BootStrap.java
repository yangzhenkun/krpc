package com.krpc.server;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.xml.DOMConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.krpc.common.serializer.SerializeUtil;
import com.krpc.server.core.LoadConfigure;
import com.krpc.server.core.RequestHandler;
import com.krpc.server.entity.Global;

/**
 * 启动器
 * 
 * @author yangzhenkun01
 *
 */
public class BootStrap {

	public static void main(String[] args) {

		if(args.length>0){
			//初始化项目路径
			String serviceName = args[0];
			Global.getInstance().setServiceName(serviceName);
			String userDir = System.getProperty("user.dir");
			String rootPath = userDir + File.separator+".."+File.separator;
			String rootLibPath = userDir + File.separator+"lib";
			
			String serviceRootPath = rootPath+"service"+File.separator+serviceName+File.separator;
			
			
			// 初始化log4j
			DOMConfigurator.configure(serviceRootPath+File.separator+"conf"+File.separator+"log4j.xml");
			Logger log = LoggerFactory.getLogger(BootStrap.class);
			
			try {
				//加载配置文件，并初始化相关内容
				LoadConfigure.load(serviceRootPath);
				
				byte[] request = SerializeUtil.read("D://request.txt");
				
				byte[] response = RequestHandler.handler(request);
				
				SerializeUtil.WriteStringToFile(response, "D://response.txt");
				
			
			} catch (Exception e) {
				log.error("",e);
			}
			
			
			
			
		}else{
			System.out.println("请输入启动的服务名字");
		}
		
		
	}

}
