package application.screens.profile;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class ChangePasswordController {
	
	@FXML
    private AnchorPane profileAnchorPane;

    public void initializeProfileAnchorPane(AnchorPane profileAnchorPane) {
        this.profileAnchorPane = profileAnchorPane;
    }
	
	public void GoToProfileDetails()
	{
		try {
            profileAnchorPane.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/screens/profile/ProfileDetails.fxml"));
            AnchorPane initialContent = (AnchorPane) loader.load();
            
            ProfileDetailsController detailsController = loader.getController();
            detailsController.initializeProfileAnchorPane(profileAnchorPane);
            
            profileAnchorPane.getChildren().add(initialContent);
            AnchorPane.setLeftAnchor(initialContent, 0.0);
            initialContent.toFront();
        } catch (IOException e) {
            System.out.println(e.getCause());
        }
	}
}