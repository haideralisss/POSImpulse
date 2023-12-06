package application.screens.login;

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
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

public class LoginController implements Initializable {
	
	@SuppressWarnings("exports")
	@FXML
	public TextField username, password;
	
	@SuppressWarnings("exports")
	@FXML
	public ImageView imageView;
	
	private Stage stage;
	private Scene scene;
	@SuppressWarnings("unused")
	private Parent root;
	
	Image myImage = new Image(getClass().getResourceAsStream("/application/assets/closeicon.png"));
	
	public void closeProgram()
	{
		Platform.exit();
	}
	
	public void loginUser()
	{
		if(username.getText().trim().equals("haider") && password.getText().trim().equals("haider"))
			System.out.println("Haider"); // will move to dashboard in future
	}
	
	@SuppressWarnings("exports")
	public void moveToRegisterUserScreen(MouseEvent e) throws IOException
	{
		Parent root = FXMLLoader.load(getClass().getResource("/application/screens/signup/Signup.fxml"));
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		imageView.setImage(myImage);
		
	}
	
	

}
