package com.krpc.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import com.krpc.client.net.TCPClient;

public class TCP {

	@Test
	public void test() {

		String req = "hello";

		try {
			byte[] res = TCPClient.send(req.getBytes(), "127.0.0.1", 17999);

			String response = new String(res);

			System.out.println("客户端接收到:" + response);

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testSend() {

		try {
			// 1.创建 socket 指定服务器地址和端
			Socket client = new Socket("127.0.0.1", 17999);
			// 2.客户端向服务器发送登录信息
			OutputStream os = client.getOutputStream();// 字节输出流
			PrintWriter pw = new PrintWriter(os);
			pw.write("hello");
			pw.flush();
			client.shutdownOutput();// 关闭输出流

			// 3. 获取输入流
			InputStream is = client.getInputStream();
//			InputStreamReader isr = new InputStreamReader(is);
//			BufferedReader br = new BufferedReader(isr);
//			String info = null;
//			while ((info = br.readLine()) != null) {
//				System.out.println("服务器发来消息说：" + info);
//			}
			byte[] res = IOUtils.toByteArray(is);
			System.out.println(new String(res));
			

			// 3.关闭其他资源
			pw.close();
			os.close();
			client.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
