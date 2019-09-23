package test;



import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import server_side.MyClientHandler;
import server_side.MySerialServer;
import view.MapDisplayer1;

public class Model {
	
	MapDisplayer1 mapDis;
	
	public Model(MapDisplayer1 mapDis) {
		this.mapDis=mapDis;
	}
	
	public void throttleBar(double move) {
		System.out.println("testing");
		String path = "/controls/engines/current-engine/throttle";
		String varName = "throttle";
		if(MyInterpreter.pathToDoubleValueTable.containsKey(path)) {
			MyInterpreter.pathToDoubleValueTable.replace(path, move);
		}
		if(MyInterpreter.symbolTable.containsKey(varName)) {
			MyInterpreter.symbolTable.replace(varName, move);
		}
		ConnectCommand.out.println("set " + path + " " + move);
		
	}
	
	public void rudderBar(double move) {
		String path = "/controls/flight/rudder";
		String varName = "rudder";
		if(MyInterpreter.pathToDoubleValueTable.containsKey(path)) {
			MyInterpreter.pathToDoubleValueTable.replace(path, move);
		}
		if(MyInterpreter.symbolTable.containsKey(varName)) {
			MyInterpreter.symbolTable.replace(varName, move);
		}
		ConnectCommand.out.println("set " + path + " " + move);
	}
	
	public void moveJoystick(double aileronVal , double elevatorVal) {
		String aileronPath = "/controls/flight/aileron";
		String elevatorPath = "/controls/flight/elevator";
		String aileronVar = "aileron";
		String elevatorVar = "elevator";
		MyInterpreter.pathToDoubleValueTable.replace(aileronPath, aileronVal);
		MyInterpreter.symbolTable.replace(aileronVar, aileronVal);
		MyInterpreter.pathToDoubleValueTable.replace(elevatorPath, elevatorVal);
		MyInterpreter.symbolTable.replace(elevatorVar, elevatorVal);
		ConnectCommand.out.println("set " + aileronPath + " " + aileronVal);
		ConnectCommand.out.println("set " + elevatorPath + " " + elevatorVal);
	}
	
	public void connect(String ip, String port) {
		 OpenServerCommand osc = new OpenServerCommand();
		 String[] parameters1 = {"openDataServer", "5400" ,"10"}; 
		 osc.doCommand(parameters1, 0);
		 ConnectCommand cc = new ConnectCommand();
		 String[] parameters2 = {"connect", ip, port};
		 cc.doCommand(parameters2, 0);
	}
	
	
	public void calculatePath() {
		Thread calcThread = new Thread();
		   MySerialServer mss = new MySerialServer(5402);
		   mss.start(5402, new MyClientHandler(mapDis));
			try {
				Socket theServer = new Socket("127.0.0.1", 5402);
			   } catch (IOException e) { e.printStackTrace(); }
	}
	
	public void runScript() {
        try {
        BufferedReader scriptReader=new BufferedReader(new FileReader("./resources/script.txt"));     
        String line;
        List<String> lines=new ArrayList<>();
        while((line=scriptReader.readLine())!=null){
            lines.add(line);
        }
        scriptReader.close();
        MyInterpreter.interpret(lines.toArray(new String[1]));
    } catch (IOException e) { e.printStackTrace();}
	}
	

}
