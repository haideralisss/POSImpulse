package application.screens.profile;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.models.entities.Accounts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

public class ProfileController {

    @FXML
    AnchorPane profileAnchorPane;
    
    private Accounts currentAccount;
	
	public void setAccount(Accounts account)
	{
		currentAccount = account;
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
