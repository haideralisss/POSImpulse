package application.models.entities;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class PurchasedProducts {
	
	private int productId, purchaseId, quantity, bonus;
	private double unitCost, netTotal;
	private String discount, salesTax, batchNum;
	
	private int number;
	private HBox operations;
	
	public PurchasedProducts(int number, int productId, int purchaseId, int quantity, int bonus, float unitCost, String discount, String salesTax, float netTotal, String batchNum)
	{
		this.number = number;
		this.productId = productId;
		this.purchaseId = purchaseId;
		this.quantity = quantity;
		this.bonus = bonus;
		this.unitCost = unitCost;
		this.discount = discount;
		this.salesTax = salesTax;
		this.netTotal = netTotal;
		this.batchNum = batchNum;
		
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

    public int getPurchaseId() {
        return purchaseId;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getBonus() {
        return bonus;
    }

    public double getUnitCost() {
        return unitCost;
    }

    public String getDiscount() {
        return discount;
    }

    public String getSalesTax() {
        return salesTax;
    }

    public double getNetTotal() {
        return netTotal;
    }

    public String getBatchNum() {
        return batchNum;
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