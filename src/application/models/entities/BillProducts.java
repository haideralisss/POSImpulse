package application.models.entities;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class BillProducts {
	
	private int productId, billId, quantity;
	private double price, netTotal;
	private String discount;
	
	private int number;
	private HBox operations;
	
	public BillProducts(int number, int billId, int productId, int quantity, double price, String discount, double netTotal)
	{
		this.number = number;
		this.billId = billId;
		this.productId = productId;
		this.quantity = quantity;
		this.price = price;
		this.discount = discount;
		this.netTotal = netTotal;
		
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

	public int getBillId() {
        return billId;
    }

    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public String getDiscount() {
        return discount;
    }

    public double getNetTotal() {
        return netTotal;
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