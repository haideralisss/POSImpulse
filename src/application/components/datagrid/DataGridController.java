package application.components.datagrid;
import java.io.IOException;
import java.util.List;

import application.components.inputform.InputFormController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class DataGridController 
{
	@FXML
	public Label title;
	
	@FXML
	public TableView dataGridTable;
	
	@FXML 
	public ComboBox<String> category;
	
	@FXML
	public ImageView titleIcon;
	
	List<Attribute> attributes;
	public AnchorPane anchorPane;
	
	public void SetupDataGrid(String title, List<Attribute> attributes, AnchorPane anchorPane)
	{
		this.anchorPane = anchorPane;
		int i = 0;
		
		this.title.setText(title);
		titleIcon.setImage(new Image(getClass().getResource("/assets/" + title.toLowerCase() + "Icon.png").toExternalForm()));
		titleIcon.setFitWidth(30);
		titleIcon.setFitHeight(30);
		
		TableColumn<String, String> firstCol = new TableColumn<>("#");
        firstCol.getStyleClass().add("columnHeader");
        firstCol.getStyleClass().add("startCorner");
        dataGridTable.getColumns().add(firstCol);
		
		if(attributes != null)
		{
			this.attributes = attributes;
			for (Attribute attribute : attributes) 
			{
	            if(!attribute.isHide())
	            {
	            	TableColumn<String, String> column = new TableColumn<>(attribute.getAttribute());
		            column.getStyleClass().add("columnHeader");
		            dataGridTable.getColumns().add(column);
		            
		            if(attribute.isSearch())
		            {
		            	category.getItems().add(attribute.getAttribute());
		            }
		            
		            i++;
	            }
	        }
		}
		
		TableColumn<String, String> endCol = new TableColumn<>("Operations");
        endCol.getStyleClass().add("columnHeader");
        endCol.getStyleClass().add("endCorner");
        dataGridTable.getColumns().add(endCol);
		
		category.setValue(category.getItems().get(0));
	}
	
	public void OpenInputForm()
	{
		try {
			anchorPane.getChildren().clear();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/components/inputform/InputForm.fxml"));
			AnchorPane nextAnchorPane;
			nextAnchorPane = (AnchorPane) loader.load();
			anchorPane.getChildren().add(nextAnchorPane);
			AnchorPane.setLeftAnchor(nextAnchorPane, 0.0);
		    nextAnchorPane.toFront();
			
			InputFormController ifController;
			ifController = loader.getController();
			ifController.SetupInputForm(title.getText(), attributes, anchorPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}