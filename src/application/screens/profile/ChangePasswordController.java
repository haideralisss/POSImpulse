package application.screens.profile;

import java.io.IOException;

import application.models.entities.Accounts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class ChangePasswordController {
	
	@FXML
    private AnchorPane profileAnchorPane;

	Accounts currentAccount;
	
	@SuppressWarnings("exports")
    public void initializeProfileAnchorPane(AnchorPane profileAnchorPane, Accounts account) {
        this.profileAnchorPane = profileAnchorPane;
        currentAccount = account;
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