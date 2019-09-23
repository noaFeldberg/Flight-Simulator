package test;

import ShuntingYard.ShuntingYardAlg;

//add return value

public class SetVarCommand implements Command {

	@SuppressWarnings("null")
	@Override
	public double doCommand(String[] parameters, int commandIndex) {
		StringBuilder sb = new StringBuilder();
		for(String s:parameters) {
			sb.append(s+" ");
		}
        //cases: "x="+rand || "x=y" || "x=3" || "x=y+3"
        if (parameters.length == 1) {
        	handelEquasionAndInsertIntoSymbolTable(parameters);
        } 
        //case: "x=" "y" "+" "3" || "x" "=" "y" "+" "3"
        else {
        	boolean isBind = false;
        	for(int i=0; i<parameters.length;i++) {
        		if(parameters[i].equals("bind")){
        			isBind= true;
        			break;
        		}
        	}
        	//case's: "x=" "y" "+" "3" || "x" "=" "y" "+" "3"
        	if(isBind==false) {	
        		handelEquasionAndInsertIntoSymbolTable(parameters);
        	}
        	// X = bind simy
        	else {
        		if(!MyInterpreter.varToPathMap.get(parameters[commandIndex]).equals(parameters[commandIndex+3])
        				|| !MyInterpreter.pathToVarMap.get(parameters[commandIndex+3]).equals(parameters[commandIndex])) {
        		MyInterpreter.varToPathMap.put(parameters[commandIndex], parameters[commandIndex+3]);
        		MyInterpreter.pathToVarMap.put(parameters[commandIndex+3], parameters[commandIndex]);
        		}
                else {
                	MyInterpreter.symbolTable.put(parameters[commandIndex], MyInterpreter.pathToDoubleValueTable.get(parameters[commandIndex+3]));
                	//System.out.println("Sending to simulator 1: "+"set " + MyInterpreter.varToPathMap.get(parameters[commandIndex]) + " " + MyInterpreter.pathToDoubleValueTable.get(parameters[commandIndex+3]));
                	 ConnectCommand.out.println("set " + MyInterpreter.varToPathMap.get(parameters[commandIndex]) + " " + MyInterpreter.pathToDoubleValueTable.get(parameters[commandIndex+3]));
                }
        	}
        }
	return parameters.length;
	}
	
	public static void handelEquasionAndInsertIntoSymbolTable(String[] str) {
		String var =str[0];
		StringBuilder sb= new StringBuilder();
		//from "x=y+3" to --> "y+3"
		for (int i = 2; i< str.length ; i++) {
			sb.append(str[i]);
		}
		String equation= sb.toString();
		double res = ShuntingYardAlg.calc(equation);
		//System.out.println("res is: "+res);
		if(MyInterpreter.symbolTable.containsKey(var)) {
			MyInterpreter.symbolTable.replace(var, res);
		}
		else {
			MyInterpreter.symbolTable.put(var, res);
		}
		
		if(MyInterpreter.varToPathMap.containsKey(var)) {
			String path = MyInterpreter.varToPathMap.get(var);
			MyInterpreter.pathToDoubleValueTable.replace(path,res);
			//System.out.println("Sending to simulator 2: "+"set " + path + " " + res);
			ConnectCommand.out.println("set " + path + " " + res);
			MyInterpreter.varToPathMap.forEach((v,p)->{if(MyInterpreter.varToPathMap.get(v).equals(path)) {
				MyInterpreter.symbolTable.replace(v, res);
				
			}});
		}
	}
}