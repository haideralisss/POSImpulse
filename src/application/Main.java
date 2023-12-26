package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
<<<<<<< HEAD
=======
import javafx.stage.Screen;
>>>>>>> sidebar
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	
	@SuppressWarnings("exports")
	@Override
	public void start(Stage primaryStage) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/screens/sidebar/SideBar.fxml"));
		Parent root = loader.load();
//		Scene scene = new Scene(root, Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight() - 20);
		Scene scene = new Scene(root);
		
		primaryStage.setTitle("POS Impulse");
		primaryStage.centerOnScreen();
		primaryStage.setResizable(false);
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
