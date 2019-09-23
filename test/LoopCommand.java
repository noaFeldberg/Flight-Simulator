package test;

import ShuntingYard.ShuntingYardAlg;

public class LoopCommand implements Command {

	@SuppressWarnings("unused")
	@Override
	public double doCommand(String[] parameters, int commandIndex) {
		//find the loop condition:
		Double leftExpression = null;
		Double rightExpression = null;
		String predicate=null;
		int res=0;	
		StringBuilder sb = new StringBuilder();
		int innerLoop = 0;
		String[] innerLoopEquasion = new String[5];
		int j=0;
		String[] whileiLine=null;
		boolean flag= true;
		
//		System.out.println("length is: "+parameters.length);
//		
//		for(String s: parameters) {
//			System.out.println(s);
//		}
//		
		for(int i=0; i<parameters.length;i++) {
			if(parameters[i].contains("while")) {
				innerLoop = i;
			}
		}
		
//		System.out.println("length is: " + parameters.length);
//		System.out.println("innerloop index: " + innerLoop);
					
		//calculate how many equasion i need to calculate
		for(int i=innerLoop; i<parameters.length && flag;i++) {
			if(parameters[i].contains("while")) {
				whileiLine = parameters[i].split("\\s+");
				leftExpression = ExpressionToDouble(whileiLine[1]);
				rightExpression= ExpressionToDouble(whileiLine[3]);
				predicate = whileiLine[2];
			}
			else if(parameters[i].contains("}")) {
				flag=false;
			}			
			else if(i>innerLoop) {	
				 innerLoopEquasion[j]=parameters[i];
				//System.out.println("loopy: "+ innerLoopEquasion[j]);
				j++;
			}
		}
				
		//apply the condition:
	     switch (predicate) {
         case "<": {
             while (leftExpression - rightExpression < 0) {
            	res=MyInterpreter.interpret(innerLoopEquasion);
         		leftExpression = ExpressionToDouble(whileiLine[1]);
        		rightExpression= ExpressionToDouble(whileiLine[3]);
             }
         } break;

         case ">": {
             while (leftExpression - rightExpression > 0) {
            	 res=MyInterpreter.interpret(innerLoopEquasion);
         		leftExpression = ExpressionToDouble(whileiLine[1]);
        		rightExpression= ExpressionToDouble(whileiLine[3]);	
             }
         } break;

         case "<=": {
             while (leftExpression - rightExpression <= 0) {
            	 res=MyInterpreter.interpret(innerLoopEquasion);
         		leftExpression = ExpressionToDouble(whileiLine[1]);
        		rightExpression= ExpressionToDouble(whileiLine[3]);	
             }
         } break;

         case ">=": {
             while (leftExpression - rightExpression >= 0) {
            	 res=MyInterpreter.interpret(innerLoopEquasion);
         		leftExpression = ExpressionToDouble(whileiLine[1]);
        		rightExpression= ExpressionToDouble(whileiLine[3]);	
             }
         } break;

         case "==": {
             while (leftExpression - rightExpression == 0) {
            	 res=MyInterpreter.interpret(innerLoopEquasion);
         		leftExpression = ExpressionToDouble(whileiLine[1]);
        		rightExpression= ExpressionToDouble(whileiLine[3]);	
             }
         } break;

         case "!=": {
             while (leftExpression - rightExpression != 0) {
            	 res=MyInterpreter.interpret(innerLoopEquasion);
         		leftExpression = ExpressionToDouble(whileiLine[1]);
        		rightExpression= ExpressionToDouble(whileiLine[3]);	
             }
         } break;
     } 
	   //return the next expression afer the "}"
	     return parameters.length;
	}
	
	
	public double ExpressionToDouble(String var) {
		Double expressionValue;
		if(MyInterpreter.symbolTable.containsKey(var)) {
			expressionValue =MyInterpreter.symbolTable.get(var);
			return expressionValue;
		}
		else {
			expressionValue = ShuntingYardAlg.calc(var);
			return expressionValue;
		}	
	}
}