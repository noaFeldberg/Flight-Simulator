package test;

public class SleepCommand implements Command{

	@Override
	public double doCommand(String[] parameters, int commandIndex) {
		try {
			int sleepy = Integer.parseInt(parameters[commandIndex+1]);
			Thread.sleep(sleepy);
		} catch (NumberFormatException | InterruptedException e) { e.printStackTrace(); }
		return parameters.length;
	}
}
