package com.krpc.client.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.commons.io.IOUtils;

public class TCPClient {

	
	public static byte[] send(byte[] sendData,String host,int port) throws UnknownHostException, IOException {
		Socket socket = new Socket(host,port);
		OutputStream os = socket.getOutputStream();
		InputStream is = socket.getInputStream();
		byte resultArray[] = null;
		try {
			os.write(sendData);
			os.flush();
			socket.shutdownOutput();
			resultArray = IOUtils.toByteArray(is);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			os.close();
			is.close();
			socket.close();
		}
		return resultArray;
	}

}
