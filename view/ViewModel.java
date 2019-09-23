package view;



import test.Model;

public class ViewModel {
	
	Model myModel;
	
	
	public ViewModel(MapDisplayer1 mapDis) {
		myModel = new Model(mapDis);
	}
	
	public void runScripVM() {
		myModel.runScript();
	}

	public void moveJoystic(double aileronVal , double elevatorVal) {
		myModel.moveJoystick(aileronVal ,elevatorVal);
	}
	
	public void connect(String ip, String port) {
		myModel.connect(ip,port);
	}
	
	public void throttleBar(double move) {
		System.out.println("Test");
		myModel.throttleBar(move);
	}
	
	public void rudderBar(double move) {
		myModel.rudderBar(move);
	}
}
