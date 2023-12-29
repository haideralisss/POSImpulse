package application.models.entities;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class Stock {
	
	private String productName;
	private double unitCost;
	private int totalQuantity, productId;

	private int number;
	private HBox operations;
	
	public Stock(int number, int productId, String productName, double unitCost, int totalQuantity)
	{
		this.number = number;
		this.productId = productId;
		this.productName = productName;
		this.unitCost = unitCost;
		this.totalQuantity = totalQuantity;
		
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
		operations.getChildren().add(delButton);
		
		operations.setMaxWidth(Double.MAX_VALUE);
		operations.setAlignment(Pos.CENTER);
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
	
	public int getNumber()
	{
		return number;
	}
	
	public HBox getOperations()
	{
		return operations;
	}
}
