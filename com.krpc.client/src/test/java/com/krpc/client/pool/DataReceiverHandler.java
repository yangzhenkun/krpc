package com.krpc.client.pool;

import java.io.IOException;
import java.net.Socket;

public class DataReceiverHandler {
	
	private KRPCSocket socket;
	
	public DataReceiverHandler(KRPCSocket socket){
		this.socket=socket;
		
		ReceiverTask task = new ReceiverTask(socket);
		Thread thread = new Thread(task);
		thread.setDaemon(true);
		thread.setName("socket数据接受线程");
		thread.start();
	}
	
	
	class ReceiverTask implements Runnable{

		private KRPCSocket socket;
		
		public ReceiverTask(KRPCSocket socket) {
			this.socket=socket;
		}
		
		@Override
		public void run() {
			
			while(true){
				
				try {
					socket.receiver();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
			
		}
		
	}
}
