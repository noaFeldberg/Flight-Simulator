package test;

public class mainForSim {
	public static void main(String[] args) {
	DataReaderServer dr= new DataReaderServer(5402, 5);
	dr.runServer();
	}
}
