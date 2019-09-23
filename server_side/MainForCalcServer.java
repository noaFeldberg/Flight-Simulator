package server_side;

public class MainForCalcServer {
	
	public static void main(String[] args) {
		MySerialServer ms= new MySerialServer(5400);
		ms.start(5400, new ClientHandler() {
			
			@Override
			public void handleClient() {
				// TODO Auto-generated method stub
				
			}
		});

	}

}
