package test;

public class DisconnectCommand implements Command {

	@Override
	public double doCommand(String[] parameters, int commandIndex) {
		MyInterpreter.commands.add("bye");
        return parameters.length;
	}
}
