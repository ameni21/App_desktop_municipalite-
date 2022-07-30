package Application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;



public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/FXML_Files/login.fxml"));
			 Scene scene = new Scene(root);
			 scene.getStylesheets().add(getClass().getResource("/CSS_Files/application.css").toExternalForm());
			 primaryStage.setScene(scene);
			 primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}