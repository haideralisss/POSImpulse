package application.screens.profile;

import java.io.IOException;

import com.jfoenix.controls.JFXPasswordField;

import application.models.entities.Accounts;
import application.models.repositories.AccountsRepo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ChangePasswordController {
	
	@FXML
    private AnchorPane profileAnchorPane;
	
	@FXML
	private JFXPasswordField oldPasswordTextBox, newPasswordTextBox, confirmNewPasswordTextBox;

	private Accounts currentAccount;
	private int attempts = 0;
	
	@SuppressWarnings("exports")
    public void initializeProfileAnchorPane(AnchorPane profileAnchorPane, Accounts account) {
        this.profileAnchorPane = profileAnchorPane;
        currentAccount = account;
    }
	
	@SuppressWarnings("exports")
	public void changePassword(ActionEvent e) throws IOException
	{
		if(newPasswordTextBox.getText().equals(confirmNewPasswordTextBox.getText()))
		{
			if(newPasswordTextBox.getText().equals(oldPasswordTextBox.getText()))
			{
				showErrorMessage("Password Match!", "Please make sure that the entered old password and the new password are not same.");
			}
			else 
			{
				AccountsRepo accountsRepo = new AccountsRepo();
				if(accountsRepo.changePassword(currentAccount.getId(), oldPasswordTextBox.getText(), newPasswordTextBox.getText()))
				{
					GoToProfileDetails();
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Success");
					alert.setHeaderText("Password Change Successful!");
					alert.setContentText("You have successfully changed your password!");
					alert.show();
				}
				else
				{
					attempts++;
					
					if(attempts == 3)
					{
						FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/screens/login/Login.fxml"));
					    Parent root = loader.load();
					    Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
					    Scene scene = new Scene(root);
					    stage.setScene(scene);
					    stage.centerOnScreen();
					    stage.show();
					    showErrorMessage("You have been logged out!", "Due to security reasons, you have been logged out after three attempts.");
					}
					else
						showErrorMessage("Old Password Mismatch!", "Please make sure that you have entered the correct password!");
				}
			}
		}
		else
		{
			showErrorMessage("New Password Mismatch!", "Please make sure that the new password matches!");
		}
	}
	
	public void showErrorMessage(String header, String content)
	{
		oldPasswordTextBox.setText("");
		newPasswordTextBox.setText("");
		confirmNewPasswordTextBox.setText("");
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Validation Error");
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.show();
	}
	
	public void GoToProfileDetails()
	{
		try {
            profileAnchorPane.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/screens/profile/ProfileDetails.fxml"));
            AnchorPane initialContent = (AnchorPane) loader.load();
            
            ProfileDetailsController detailsController = loader.getController();
            detailsController.initializeProfileAnchorPane(profileAnchorPane, currentAccount);
            
            profileAnchorPane.getChildren().add(initialContent);
            AnchorPane.setLeftAnchor(initialContent, 0.0);
            initialContent.toFront();
        } catch (IOException e) {
            System.out.println(e.getCause());
        }
	}
}