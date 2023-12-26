package application.screens.signup;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class SignupController implements Initializable {
	
	@SuppressWarnings("exports")
	@FXML
	public TextField fullName, username, phoneNumber, password;
	
	@SuppressWarnings("exports")
	@FXML
	public ImageView imageView;
	
	private Stage stage;
	private Scene scene;
	
	Image myImage = new Image(getClass().getResourceAsStream("/application/assets/closeicon.png"));
	
	public void registerUser()
	{
		System.out.println("Registered User!"); // will move to the login screen/dashboard in future
	}
	
	public void closeProgram()
	{
		Platform.exit();
	}
	
	@SuppressWarnings("exports")
	public void moveToLoginScreen(MouseEvent e) throws IOException
	{
		Parent root = FXMLLoader.load(getClass().getResource("/application/screens/login/Login.fxml"));
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		imageView.setImage(myImage);	
	}

}
