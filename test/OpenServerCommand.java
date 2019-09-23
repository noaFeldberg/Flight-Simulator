package test;

import ShuntingYard.ShuntingYardAlg;

public class OpenServerCommand implements Command {
	
	int port, rate;
	
	public OpenServerCommand() {
		this.port=0;
		this.rate=0;
	}
	
	@SuppressWarnings("unused")
	@Override
	public double doCommand(String[] parameters, int commandIndex) {
		//set parameters
		this.port = (int)ShuntingYardAlg.calc(parameters[commandIndex+1]);
		this.rate=(int) (1000/ShuntingYardAlg.calc(parameters[commandIndex+2]));
		
		//validation check:
		if(port<=0 || rate<=0) {
			System.out.println("Invalid port or readingSpeed");
		}	
		
        new Thread(() -> {
            DataReaderServer server = new DataReaderServer(port, rate);
            server.runServer();
        }).start();
        while(!MyInterpreter.flag);
        return parameters.length;
	}
}
