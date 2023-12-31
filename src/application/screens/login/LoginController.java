package application.screens.login;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import application.models.entities.Accounts;
import application.models.repositories.*;
import application.screens.sidebar.SideBarController;

public class LoginController implements Initializable {
	
	@SuppressWarnings("exports")
	@FXML
	public TextField username, password;
	
	@SuppressWarnings("exports")
	@FXML
	public ImageView imageView;
	
	@SuppressWarnings("unused")
	private Stage stage;
	
	@SuppressWarnings("unused")
	private Scene scene;
	
	@SuppressWarnings("unused")
	private Parent root;
	
	AccountsRepo accountsRepo;
	
	Image myImage = new Image(getClass().getResourceAsStream("/application/assets/closeicon.png"));
	
	public void closeProgram()
	{
		Platform.exit();
	}
	
	@SuppressWarnings("exports")
	public void loginUser(ActionEvent e) throws IOException
	{
		Accounts account = accountsRepo.verifyUser(username.getText(), password.getText());
		if(account != null)
		{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/screens/sidebar/SideBar.fxml"));
		    Parent root = loader.load();

		    SideBarController sbController = loader.getController();
		    sbController.setAccount(account);

		    Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		    Scene scene = new Scene(root);

		    stage.setScene(scene);
		    stage.centerOnScreen();
		    stage.show();
		}
		else
		{
			username.setText("");
			password.setText("");
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Login Error");
			alert.setHeaderText("Wrong Credentials!");
			alert.setContentText("Please provide correct credentials!");
			alert.show();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		accountsRepo = new AccountsRepo();
		imageView.setImage(myImage);
	}
}