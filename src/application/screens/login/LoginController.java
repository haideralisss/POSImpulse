package application.screens.login;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class LoginController implements Initializable {
	
	@SuppressWarnings("exports")
	@FXML
	public TextField username, password;
	
	@SuppressWarnings("exports")
	@FXML
	public ImageView imageView;
	
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		imageView.setImage(myImage);
		
	}
	
	

}
