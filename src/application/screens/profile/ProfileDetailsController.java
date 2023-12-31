package application.screens.profile;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class ProfileDetailsController {
	
	@FXML
    private AnchorPane profileAnchorPane;

    public void initializeProfileAnchorPane(@SuppressWarnings("exports") AnchorPane profileAnchorPane) {
        this.profileAnchorPane = profileAnchorPane;
    }
	
	public void GoToChangePassword()
	{
		try {
			profileAnchorPane.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/screens/profile/ChangePassword.fxml"));
            AnchorPane initialContent = (AnchorPane) loader.load();
            
            ChangePasswordController changePasswordController = loader.getController();
            changePasswordController.initializeProfileAnchorPane(profileAnchorPane);
            
            profileAnchorPane.getChildren().add(initialContent);
            AnchorPane.setLeftAnchor(initialContent, 0.0);
            initialContent.toFront();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}