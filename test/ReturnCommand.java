package test;

import ShuntingYard.ShuntingYardAlg;

public class ReturnCommand implements Command {

	@Override
	public double doCommand(String[] parameters, int commandIndex) {
		int expressionIndex = commandIndex+1;
		StringBuilder sb = new StringBuilder();

		//in case we have: return 5  +  4*3
		while (expressionIndex < parameters.length) {
			//if its a value and not a number
			if(MyInterpreter.symbolTable.containsKey(parameters[expressionIndex])) {
				sb.append(MyInterpreter.symbolTable.get(parameters[expressionIndex]));
			}
			else {
				sb.append(parameters[expressionIndex]);
			}
			expressionIndex++;
		}
		double res = ShuntingYardAlg.calc(sb.toString());
		
		 // clear all databases so we can start from fresh in the next mini-test
	      if (!MyInterpreter.pathToDoubleValueTable.isEmpty())
	    	  MyInterpreter.pathToDoubleValueTable.clear();
	      if (!MyInterpreter.varToPathMap.isEmpty())
	        	MyInterpreter.varToPathMap.clear();
	      if (!MyInterpreter.symbolTable.isEmpty())
	    	  	MyInterpreter.symbolTable.clear();
	      if (!MyInterpreter.pathToVarMap.isEmpty())
	        	MyInterpreter.pathToVarMap.clear();
	      if(!MyInterpreter.commands.isEmpty()) {
	    	  MyInterpreter.commands.clear();
	      }
		return res;
	}
}
