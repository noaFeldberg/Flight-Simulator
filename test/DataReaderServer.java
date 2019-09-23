package test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import view.MapDisplayer1;


public class DataReaderServer {
	
	int port, rate;
	public ArrayList<String> varNames;
	public volatile static boolean stop;
	MapDisplayer1 md1;
	
	
	public DataReaderServer(int port , int rate) {
		stop=false;
		this.port=port;
		this.rate=rate;
		this.varNames = new ArrayList<>();
		try {
			Scanner scanner= new Scanner(new BufferedReader(new FileReader("./resources/simulator_vars.txt")));
			while (scanner.hasNext()) {
				varNames.add(scanner.next());
			}
		} catch (FileNotFoundException e) {}
		for(String s : varNames) {
			MyInterpreter.pathToDoubleValueTable.put(s, 0.0);
			
		}
	}

	void runServer() {
		try {
			ServerSocket dataReaderServer = new ServerSocket(port);
			System.out.println("Server is alive and waiting for a client..");
			Socket sendingDataClient = dataReaderServer.accept();
			Thread.sleep(120000);
			System.out.println("Client connected..");
			MyInterpreter.flag = true;
			BufferedReader in = new BufferedReader(new InputStreamReader(sendingDataClient.getInputStream()));
			int i;
			String line=null;
			line= in.readLine();
			while (line!=null /*&& MyInterpreter.bool.equals(true)*/) {
				i = 0;
				String[] inputFromClient = line.split(",");
				for(String s : inputFromClient) {
					Double d = Double.parseDouble(s);
					if(MyInterpreter.pathToDoubleValueTable.containsKey(varNames.get(i))){
						MyInterpreter.pathToDoubleValueTable.replace(varNames.get(i), d);
						if(MyInterpreter.pathToVarMap.containsKey(varNames.get(i))) {
							MyInterpreter.symbolTable.replace(MyInterpreter.pathToVarMap.get(varNames.get(i)), d);
						}	
					MyInterpreter.pathToDoubleValueTable.put(varNames.get(i), d);
					i++;
					}
				}
				//getPlanePosition();
				line= in.readLine();
				Thread.sleep(3);
				
			}
			in.close();
			sendingDataClient.close();
			dataReaderServer.close();
			System.out.println("Server is off");
		}
		catch (IOException | InterruptedException e) {}
	}
}
