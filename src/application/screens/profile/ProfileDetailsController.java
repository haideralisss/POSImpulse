package application.screens.profile;

import java.io.IOException;

import application.models.entities.Accounts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class ProfileDetailsController {
	
	@FXML
    private AnchorPane profileAnchorPane;
	
	@FXML
	private Label userNameLabel, fullNameLabel, phoneLabel;

	private Accounts currentAccount;
	
	@SuppressWarnings("exports")
    public void initializeProfileAnchorPane(AnchorPane profileAnchorPane, Accounts account) {
        this.profileAnchorPane = profileAnchorPane;
        this.currentAccount = account;
        
        fullNameLabel.setText(currentAccount.getFullName());
        phoneLabel.setText(currentAccount.getPhone());
    }
	
	public void GoToChangePassword()
	{
		try {
			profileAnchorPane.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/screens/profile/ChangePassword.fxml"));
            AnchorPane initialContent = (AnchorPane) loader.load();
            
            ChangePasswordController changePasswordController = loader.getController();
            changePasswordController.initializeProfileAnchorPane(profileAnchorPane, currentAccount);
            
            profileAnchorPane.getChildren().add(initialContent);
            AnchorPane.setLeftAnchor(initialContent, 0.0);
            initialContent.toFront();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}