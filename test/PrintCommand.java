package test;

public class PrintCommand implements Command {

	@Override
	public double doCommand(String[] parameters, int commandIndex) {
	    //MyInterpreter.bool = false;
		//System.out.println(parameters[commandIndex+1] + "     "+ MyInterpreter.symbolTable.get(parameters[commandIndex+1]));
		//ConnectCommand.out.println("set " + MyInterpreter.varToPathMap.get(parameters[commandIndex+1]) + " " + MyInterpreter.symbolTable.get(parameters[commandIndex+1]));

		return parameters.length;
	}

}
