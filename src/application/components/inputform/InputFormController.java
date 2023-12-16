package application.components.inputform;
import java.io.IOException;
import java.util.List;

import application.components.datagrid.Attribute;
import application.components.datagrid.DataGridController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class InputFormController {
	
	@SuppressWarnings("exports")
	@FXML
	public Label title;
	
	@SuppressWarnings("exports")
	@FXML
	public FlowPane inputFlowPane;
	
	@SuppressWarnings("exports")
	@FXML
	public ImageView titleIcon;
	
	@SuppressWarnings("exports")
	@FXML
	public VBox anchorVBox, flowVBox;
	
	@SuppressWarnings("exports")
	@FXML
	public AnchorPane inputAnchorPane, anchorPane;
	
	@SuppressWarnings("exports")
	@FXML
	public Label heading;
	
	List<Attribute> attributes;
	
	public void SetupInputForm(String title, List<Attribute> attributes, @SuppressWarnings("exports") AnchorPane anchorPane)
	{
		this.title.setText(title);
		this.anchorPane = anchorPane;
		this.attributes = attributes;
		titleIcon.setImage(new Image(getClass().getResource("/assets/" + title.toLowerCase() + "Icon.png").toExternalForm()));
		titleIcon.setFitWidth(30);
		titleIcon.setFitHeight(30);
	}
	
	public void CancelEvent()
	{
		try {
			anchorPane.getChildren().clear();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/components/datagrid/DataGrid.fxml"));
			AnchorPane nextAnchorPane;
			nextAnchorPane = (AnchorPane) loader.load();
			anchorPane.getChildren().add(nextAnchorPane);
			AnchorPane.setLeftAnchor(nextAnchorPane, 0.0);
		    nextAnchorPane.toFront();
		    DataGridController dgController = loader.getController();
			dgController.SetupDataGrid((title.getText() == "Admin Panel" ? "Accounts" : title.getText()), attributes, anchorPane);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public void SubmitEvent()
	{
		
	}
}