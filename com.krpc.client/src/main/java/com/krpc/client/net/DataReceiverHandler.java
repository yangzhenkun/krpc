package com.krpc.client.net;

/**
 * 原生NIO接受控制类
 * 已交由netty
 * 
 * @author yangzhenkun
 *
 */
@Deprecated
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
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			
		}
		
	}
}
