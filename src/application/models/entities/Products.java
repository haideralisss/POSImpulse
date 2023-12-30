package application.models.entities;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import application.components.datagrid.Attribute;
import application.components.inputform.InputFormController;
import application.models.repositories.ProductsRepo;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class Products 
{	
	private String name, companyName;
	private int packSize, companyId, id;
	private double purchasePrice, retailPrice;
	
	private int number;
	private HBox operations;
	
	private static TableView<Products> dataGridTable;
	private static String title;
	private static List<Attribute> attributes;
	private static AnchorPane anchorPane;
	
	public Products()
	{
		id = number = packSize = companyId = 0;
		name = companyName = "";
		purchasePrice = retailPrice = 0;
	}
	
	public Products(int id, int number, String name, int packSize, double purchasePrice, double retailPrice, int companyId, String companyName)
	{
		this.id = id;
		this.number = number;
		this.name = name;
		this.packSize = packSize;
		this.purchasePrice = purchasePrice;
		this.retailPrice = retailPrice;
		this.companyId = companyId;
		this.companyName = companyName;
		
		HBox delHBox = new HBox();
		HBox editHBox = new HBox();
		ImageView delButton = new ImageView();
		Image delIcon = new Image("file:///C:/Users/AbdulWali/eclipse-workspace/POSImpulse/src/assets/deleteIcon.png");
		delButton.setImage(delIcon);
		delButton.setFitWidth(15);
		delButton.setFitHeight(15);
		ImageView editButton = new ImageView();
		Image editIcon = new Image("file:///C:/Users/AbdulWali/eclipse-workspace/POSImpulse/src/assets/editIcon.png");
		editButton.setImage(editIcon);
		editButton.setFitWidth(15);
		editButton.setFitHeight(15);
		operations = new HBox();
		operations.getChildren().add(editHBox);
		operations.getChildren().add(delHBox);
		
		editHBox.setMaxWidth(Double.MAX_VALUE);
		editHBox.setAlignment(Pos.CENTER);
		editHBox.getChildren().add(editButton);
		editHBox.setCursor(Cursor.HAND);
		editHBox.setOnMouseClicked(event -> {
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
				ifController.SetupInputForm(title, attributes, anchorPane, this);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		
		delHBox.getChildren().add(delButton);
		delHBox.setMaxWidth(Double.MAX_VALUE);
		delHBox.setAlignment(Pos.CENTER);
		delHBox.setCursor(Cursor.HAND);
		delHBox.setOnMouseClicked(event -> {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		    alert.setTitle("Confirmation Dialog");
		    alert.setHeaderText("Delete Product");
		    alert.setContentText("Are you sure you want to delete this product?");

		    ButtonType confirmButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
		    ButtonType cancelButton = new ButtonType("No", ButtonBar.ButtonData.NO);
		    alert.getButtonTypes().setAll(confirmButton, cancelButton);

		    Optional<ButtonType> result = alert.showAndWait();
		    if (result.isPresent() && result.get() == confirmButton) {
		        ProductsRepo productsRepo = new ProductsRepo();
		        dataGridTable.setItems(FXCollections.observableArrayList(productsRepo.deleteProduct(this.id)));
		    }
		});
		
		operations.setMaxWidth(Double.MAX_VALUE);
		operations.setAlignment(Pos.CENTER);
	}
	
	public static void setDataGridTable(TableView<Products> table, String Title, List<Attribute> Attributes, AnchorPane AnchorPANE) {
        dataGridTable = table;
        title = Title;
        attributes = Attributes;
        anchorPane = AnchorPANE;
    }
	
	public String getName() 
	{
        return name;
    }

    public int getPackSize() 
    {
        return packSize;
    }

    public double getPurchasePrice() 
    {
        return purchasePrice;
    }

    public double getRetailPrice() 
    {
        return retailPrice;
    }

    public int getCompanyId() 
    {
        return companyId;
    }

    public String getCompanyName() 
    {
        return companyName;
    }
    
    public int getId()
	{
		return id;
	}
	
	public int getNumber()
	{
		return number;
	}
	
	public HBox getOperations()
	{
		return operations;
	}
}