package com.a123.com.a123.call;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.log4j.xml.DOMConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.a123.service.user.contract.UserService;
import com.a123.service.user.entity.User;
import com.alibaba.fastjson.JSON;
import com.krpc.client.KRPC;
import com.krpc.client.proxy.ProxyFactory;

/**
 * 服务调用者
 * 
 * @author yangzhenkun
 *
 */
public class App {

	public static void main(String[] args) {
		Long start = 0L;
		try {
			DOMConfigurator.configure("C:\\Users\\yangzhenkun01\\Desktop\\krpc\\client\\log4j.xml");
			final Logger log = LoggerFactory.getLogger(App.class);

			// 初始KRPC服务
			KRPC.init("C:/Users/yangzhenkun01/Desktop/krpc/client/client.xml");

			// 通过代理获取接口类，第二个参数为client.xml文件中服务的名字,第三个参数为该接口具体实现的名字，需要跟该服务的配置文件的name值一样
			UserService service = ProxyFactory.create(UserService.class, "user", "userService");

/*			final List<User> users = new ArrayList<User>();
			for (int i = 0; i < 20; i++) {
				User user = new User();
				user.setId(i);
				user.setName(i + "");
				user.setPhone(111);
				user.setBirthDay(new Date());

				users.add(user);
			}
*/
			Executor pool = Executors.newFixedThreadPool(1);
			final CountDownLatch count = new CountDownLatch(400000);
			start = System.currentTimeMillis();
			for (int i = 0; i < 400000; i++) {

				pool.execute( new Task(service,i,log) );

			}

			count.await();

			System.out.println(System.currentTimeMillis() - start);
			System.out.println("执行完毕");
		} catch (Exception e) {
			System.out.println(System.currentTimeMillis() - start);
			e.printStackTrace();
		}

	}

	public static class Task implements Runnable {

		UserService userService;
		int id;
		Logger log;

		public Task(UserService us, int id,Logger log) {
			this.userService = us;
			this.id=id;
			this.log=log;
		}

		public void run() {
			User user = new User();
			user.setId(id);
			user.setName("name:" + id);
			user.setBirthDay(new Date());
			user.setPhone(188L);
			List<User> users = new ArrayList<User>();
			users.add(user);
			long start = System.currentTimeMillis();
			List<User> s = userService.users(users);
			try {
				if(s.get(0).getId()!=id){
					log.error("不正确的数据 ,sessionID:{},内容:{}",id,JSON.toJSONString(s));
				}
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.error("调用错误",e);
			}

		}

	}

}
