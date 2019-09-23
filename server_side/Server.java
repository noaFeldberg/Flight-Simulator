package server_side;

public interface Server {
	
	public void runServer() throws Exception;
	public void stopServer();
	public void start(int port, ClientHandler ch);
	
}
