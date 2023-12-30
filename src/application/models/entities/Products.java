package application.models.entities;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class Products 
{	
	private String name, companyName;
	private int packSize, companyId;
	private double purchasePrice, retailPrice;
	
	private int number;
	private HBox operations;
	
	public Products(int number, String name, int packSize, double purchasePrice, double retailPrice, int companyId, String companyName)
	{
		this.number = number;
		this.name = name;
		this.packSize = packSize;
		this.purchasePrice = purchasePrice;
		this.retailPrice = retailPrice;
		this.companyId = companyId;
		this.companyName = companyName;
		
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
	
	public int getNumber()
	{
		return number;
	}
	
	public HBox getOperations()
	{
		return operations;
	}
}