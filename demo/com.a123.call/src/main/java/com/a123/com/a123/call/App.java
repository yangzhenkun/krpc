package com.a123.com.a123.call;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;

import com.a123.service.user.contract.UserService;
import com.a123.service.user.entity.User;
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
			// 初始KRPC服务
			KRPC.init("C:/Users/yangzhenkun01/Desktop/krpc/client/client.xml");

			// 通过代理获取接口类，第二个参数为client.xml文件中服务的名字,第三个参数为该接口具体实现的名字，需要跟该服务的配置文件的name值一样
			final UserService service = ProxyFactory.create(UserService.class, "user", "userService");

			final List<User> users = new ArrayList<User>();
			for (int i = 0; i < 30; i++) {
				User user = new User();
				user.setId(i);
				user.setName(i + "");
				user.setPhone(111);
				user.setBirthDay(new Date());

				users.add(user);
			}

			Executor pool = Executors.newFixedThreadPool(4);
			final CountDownLatch count = new CountDownLatch(20);
			start = System.currentTimeMillis();
			for (int i = 0; i < 20; i++) {

				pool.execute(new Runnable() {

					public void run() {
						try {
							List<User> s = service.users(users);
							System.out.println(s.size());
							System.out.println(count.getCount());
						} catch (Exception e) {
							e.printStackTrace();
						}
						count.countDown();
					}
				});

			}

			while (count.getCount() != 0) {
			}
			
			System.out.println(System.currentTimeMillis() - start);
			System.out.println("执行完毕");
		} catch (Exception e) {
			System.out.println(System.currentTimeMillis() - start);
			e.printStackTrace();
		}

	}

	public class Task implements Runnable {

		UserService userService;

		public Task(UserService us) {
			this.userService = us;
		}

		public void run() {
			System.out.println();
		}

	}

}
