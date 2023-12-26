package application.components.inputform;
import java.io.IOException;
import java.util.List;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import application.components.datagrid.Attribute;
import application.components.datagrid.DataGridController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class InputFormController {
	
	@FXML
	public Label title;
	
	@SuppressWarnings("exports")
	@FXML
	public FlowPane inputFlowPane;
	
	@FXML
	public ImageView titleIcon;
	
	@FXML
	public VBox anchorVBox, flowVBox;
	
	@FXML
	public AnchorPane inputAnchorPane, anchorPane;
	
	@FXML
	public Label heading;
	
	List<Attribute> attributes;
	
	public void SetupInputForm(String title, List<Attribute> attributes, AnchorPane anchorPane)
	{
		this.title.setText(title);
		this.anchorPane = anchorPane;
		this.attributes = attributes;
		titleIcon.setImage(new Image(getClass().getResource("/assets/" + title.toLowerCase() + "Icon.png").toExternalForm()));
		titleIcon.setFitWidth(30);
		titleIcon.setFitHeight(30);
		//anchorVBox.setVgrow(inputAnchorPane, javafx.scene.layout.Priority.ALWAYS);
		//inputAnchorPane.setTopAnchor(heading, 10.0);
		
		for(Attribute attribute : attributes) {
			if(attribute.isInput())
			{
				if(attribute.getType() == "text")
				{
					VBox vBox = new VBox();
					Label label = new Label(attribute.getAttribute());
					label.getStyleClass().add("inputLabel");
					vBox.getChildren().add(label);
					JFXTextField textField = new JFXTextField();
					textField.getStyleClass().add("inputField");
					textField.setPromptText(attribute.getAttribute());
					vBox.getChildren().add(textField);
					inputFlowPane.getChildren().add(vBox);	
				}
				else if(attribute.getType() == "checkbox")
				{
					FlowPane fPane = new FlowPane();
					Label label = new Label(attribute.getAttribute());
					label.getStyleClass().add("checkLabel");
					fPane.getChildren().add(label);
					fPane.getChildren().add(new JFXCheckBox());
					fPane.getStyleClass().add("fPaneSpacing");
					inputFlowPane.getChildren().add(fPane);
				}
				else if(attribute.getType() == "password")
				{
					VBox vBox = new VBox();
					Label label = new Label(attribute.getAttribute());
					label.getStyleClass().add("inputLabel");
					vBox.getChildren().add(label);
					JFXPasswordField passwordField = new JFXPasswordField();
					passwordField.getStyleClass().add("inputField");
					passwordField.setPromptText(attribute.getAttribute());
					vBox.getChildren().add(passwordField);
					inputFlowPane.getChildren().add(vBox);	
				}
				else if(attribute.getType() == "date")
				{
					VBox vBox = new VBox();
					Label label = new Label(attribute.getAttribute());
					label.getStyleClass().add("inputLabel");
					vBox.getChildren().add(label);
					DatePicker datePicker = new DatePicker();
					datePicker.getStyleClass().add("inputField");
					datePicker.setPromptText(attribute.getAttribute());
					vBox.getChildren().add(datePicker);
					inputFlowPane.getChildren().add(vBox);	
				}
			}
		}
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