package test;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import ShuntingYard.ShuntingYardAlg;

public class ConnectCommand implements Command {
	public static PrintWriter out;
	
	public ConnectCommand() {
	}
	
	@SuppressWarnings({"resource" })
	@Override
	public double doCommand(String[] parameters, int commandIndex) {
		//set parameters:
		String ip= parameters[commandIndex+1];
		int port = (int)ShuntingYardAlg.calc(parameters[commandIndex+2]);
		
		//validation check:		
		if(port<=0 || ip==null) {
			System.out.println("Invalid ip or port");
		}	
		
		try {
			Socket theServer = new Socket(ip, port);
            System.out.println("Client connected to Server..");
            out = new PrintWriter(theServer.getOutputStream(),true);
		} catch (IOException e) {}
		
		return parameters.length;
	}
}