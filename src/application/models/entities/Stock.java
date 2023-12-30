package application.models.entities;

import java.util.Optional;

import application.models.repositories.StockRepo;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class Stock {
	
	private String productName;
	private double unitCost;
	private int totalQuantity, productId, id;

	private int number;
	private HBox operations;
	
	private static TableView<Stock> dataGridTable;
	
	public Stock()
	{
		id = number = productId = totalQuantity = 0;
		unitCost = 0;
		productName = "";
	}
	
	public Stock(int id, int number, int productId, String productName, double unitCost, int totalQuantity)
	{
		this.id = id;
		this.number = number;
		this.productId = productId;
		this.productName = productName;
		this.unitCost = unitCost;
		this.totalQuantity = totalQuantity;
		
		HBox delHBox = new HBox();
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
		operations.getChildren().add(editButton);
		operations.getChildren().add(delHBox);
		
		delHBox.getChildren().add(delButton);
		delHBox.setMaxWidth(Double.MAX_VALUE);
		delHBox.setAlignment(Pos.CENTER);
		delHBox.setCursor(Cursor.HAND);
		delHBox.setOnMouseClicked(event -> {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		    alert.setTitle("Confirmation Dialog");
		    alert.setHeaderText("Delete Stock");
		    alert.setContentText("Are you sure you want to delete this stock?");

		    ButtonType confirmButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
		    ButtonType cancelButton = new ButtonType("No", ButtonBar.ButtonData.NO);
		    alert.getButtonTypes().setAll(confirmButton, cancelButton);

		    Optional<ButtonType> result = alert.showAndWait();
		    if (result.isPresent() && result.get() == confirmButton) {
		        StockRepo stockRepo = new StockRepo();
		        stockRepo.deleteStock(this.id);
		        dataGridTable.setItems(FXCollections.observableArrayList(stockRepo.deleteStock(this.id)));
		    }
		});
		
		operations.setMaxWidth(Double.MAX_VALUE);
		operations.setAlignment(Pos.CENTER);
	}
	
	public static void setDataGridTable(TableView<Stock> table) {
        dataGridTable = table;
    }
	
	public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public double getUnitCost() {
        return unitCost;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }
    
    public double getTotalPrice() {
    	return unitCost * totalQuantity;
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
