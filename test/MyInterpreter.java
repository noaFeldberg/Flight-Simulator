package test;

import test.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

@SuppressWarnings("unused")
public class MyInterpreter {
	
    // commands to send to simulator server socket
    public static Queue<String> commands = new LinkedBlockingQueue<>();
    // saving each command when created for the first time
    public static HashMap<String, Command> commandsMap = new HashMap<>();

    // connection maps:
    // ----------------------------------------------------------------------

    // path -> var(key)
    public static HashMap<String, String> pathToVarMap = new HashMap<>();
    // var(key) -> path
    public static HashMap<String, String> varToPathMap = new HashMap<>();
    // path -> double(value)
    public static HashMap<String, Double> pathToDoubleValueTable = new HashMap<>();
    
	public static Map<String, Double> symbolTable= new HashMap<String, Double>();
	
	public Map<String, Double> getSymbolTable() {
		return symbolTable;
	}

    //static Boolean bool = true; 
    public static String[] allLines;
    
	//public static int lineNum;
	public volatile static boolean flag=false;
	
	public static int interpret(String[] lines){
		int res =0;
		allLines = lines;
		for(int lineNum=0; lineNum<lines.length; lineNum++) {
			//System.out.println("line is: "+"("+lineNum+") "+lines[lineNum]);
			res = parser(lexer(lines[lineNum]));
			}
		//System.out.println("i want to get out");
		return res;
	}
	
	public static String[] lexer(String inputline) {
		String[] wordsToInterpret = null;
		wordsToInterpret = inputline.split(" ");
		//wordsToInterpret= inputline.split("\\s+");
		return wordsToInterpret;
	}
	
	public static int parser(String[] wordsToInterpret) {
		Command command = null;
		//System.out.println("1");
		for(int currIndex=0; currIndex<wordsToInterpret.length; currIndex++) {
			if(commandsMap.containsKey(wordsToInterpret[currIndex])) {
				currIndex+=commandsMap.get(wordsToInterpret[currIndex]).doCommand(wordsToInterpret, currIndex);
			}
			else {
				 switch (wordsToInterpret[currIndex]) {
                 case "var": { 
                     command = new DefineVarCommand();
                     commandsMap.put("var", command);
                     currIndex += command.doCommand(wordsToInterpret, currIndex);
                     break;
                 }
                 case "connect": {
                     command = new ConnectCommand();
                     commandsMap.put("connect", command);
                     currIndex += command.doCommand(wordsToInterpret, currIndex);
                     break;
                 }
                 case "openDataServer": {
                     command = new OpenServerCommand();
                     commandsMap.put("openDataServer", command);
                     currIndex += command.doCommand(wordsToInterpret, currIndex);
                     break;
                 }
                 case "while": {
                     command = new LoopCommand();
                     commandsMap.put("while", command);
                     currIndex += command.doCommand(allLines, currIndex);
                     //lineNum =wordsToInterpret.length;
                     currIndex=wordsToInterpret.length;
                     break;
                 }

                 case "print": {
                     command = new PrintCommand();
                     commandsMap.put("PrintCommand", command);
                     currIndex += command.doCommand(wordsToInterpret, currIndex);
                     break;
                 }
                 case "return": {
                     command = new ReturnCommand();
                     commandsMap.put("returnCommand", command);
                     return (int)command.doCommand(wordsToInterpret, currIndex);
                 }
                 case "}":{
                	 currIndex = 1;
                	 break;
                 }
                 case "disconnect": {
                     command = new DisconnectCommand();
                     commandsMap.put("disconnect", command);
                     currIndex += command.doCommand(wordsToInterpret, currIndex);
                     break;
                 }
                 case "sleep" : {
                	 command = new SleepCommand();
                	 commandsMap.put("sleep", command);
                	 currIndex += command.doCommand(wordsToInterpret, currIndex);
                	 break;
           
                 }
                 //case: X=45
                 default: {
                     command = new SetVarCommand();
                     commandsMap.put(wordsToInterpret[currIndex], command);
//                     for(String s : wordsToInterpret) {
//                    	 System.out.println(s);
//                     }
                     currIndex += command.doCommand(wordsToInterpret, currIndex);
                     break;
                 }
              }
			}
		}
		return 0;
	}
	


}