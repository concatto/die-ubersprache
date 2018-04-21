package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class Main extends Application {
	
	public static Stage stage;
	
	public static Stage getStage() {
		return stage;
	}
	//@Override
	public void start(Stage primaryStage) {
		try {
			stage = primaryStage;
			Pane pane = FXMLLoader.load(getClass().getResource("../layout/layout.fxml"));
			pane.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setMinHeight(600);
			stage.setMinWidth(800);
			Scene s = new Scene(pane);
			stage.setScene(s);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}