package view;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			//SplitPane root = (SplitPane)FXMLLoader.load(getClass().getResource("mainWindow.fxml"));
			//Scene scene = new Scene(root,831,306);
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainWindow.fxml"));
	        Parent root = loader.load();
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("FlightGear Simulator GUI Application - By Dror & Noa");
			primaryStage.setScene(new Scene(root,831,306));
			primaryStage.show();
			
		} catch(Exception e) { e.printStackTrace(); }
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
