package server_side;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class MySerialServer implements Server {
	
	int port;
	ClientHandler ch;
	volatile boolean stop;
	Boolean flag;
	
	public MySerialServer(int port) { //constructor
		this.port=port;
		stop=false;
		//flag=true;
	}
	
	public Boolean getFlag() {
		return flag;
	}
	
	//begin connection with the clients + can handle multiply clients
	public void start(int port, ClientHandler ch) {
		this.port = port;
		this.ch = ch;
		stop = false;
		new Thread(()->{
			try {
				runServer();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();
		return;
	}
	   
	@Override
	public void runServer() throws Exception {
		ServerSocket serverSoc=new ServerSocket(port);
		serverSoc.setSoTimeout(1000);
		System.out.println("Server is alive, Waiting for a client..");
		
		while(!stop) {   
		try {
			Socket aClient = serverSoc.accept();
			System.out.println("client is connected");
			ch.handleClient();
			//aClient.close();
			stopServer();
			
			System.out.println("Exit server");
			
		}catch (SocketTimeoutException e) {/* ... */}
	}	
		 serverSoc.close();
		 return;
}

	@Override
	public void stopServer() {
		stop=true;
		//flag=false;
		
	}
}