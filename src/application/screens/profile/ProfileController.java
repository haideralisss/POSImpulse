package application.screens.profile;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.components.inputform.InputFormController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

public class ProfileController implements Initializable {

    @FXML
    AnchorPane profileAnchorPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
