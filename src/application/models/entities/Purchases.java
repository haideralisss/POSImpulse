package application.models.entities;

import java.time.LocalDate;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class Purchases {
	
	private String invoiceNum, salesTax, discount, shift, supplierName;
	private String purchaseDate;
	private int supplierId;
	private double grossTotal, otherCharges, netTotal, amountPaid;
	private boolean isReturn, isLoose;
	
	private int number;
	private HBox operations;
	
	public Purchases()
	{
		this.number = 0;
		this.supplierId = 0;
		this.supplierName = "";
		this.purchaseDate = "";
		this.invoiceNum = "";
		this.grossTotal = 0;
		this.salesTax = "";
		this.discount = "";
		this.otherCharges = 0;
		this.netTotal = 0;
		this.isReturn = false;
		this.isLoose = false;
		this.shift = "";
		this.amountPaid = 0;
	}
	
	public Purchases(int number, int supplierId, String supplierName, String purchaseDate, String invoiceNum, double grossTotal, String salesTax, String discount, double otherCharges, double netTotal, boolean isReturn, boolean isLoose, String shift, double amountPaid)
	{
		this.number = number;
		this.supplierId = supplierId;
		this.supplierName = supplierName;
		this.purchaseDate = purchaseDate;
		this.invoiceNum = invoiceNum;
		this.grossTotal = grossTotal;
		this.salesTax = salesTax;
		this.discount = discount;
		this.otherCharges = otherCharges;
		this.netTotal = netTotal;
		this.isReturn = isReturn;
		this.isLoose = isLoose;
		this.shift = shift;
		this.amountPaid = amountPaid;
		
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
	
	public int getSupplierId()
	{
		return supplierId;
	}
	
	public String getSupplierName()
	{
		return supplierName;
	}
	
	public String getPurchaseDate()
	{
		return purchaseDate;
	}
	
	public String getInvoiceNum()
	{
		return invoiceNum;
	}
	
	public double getGrossTotal()
	{
		return grossTotal;
	}
	
	public String getSalesTax()
	{
		return salesTax;
	}
	
	public String getDiscount()
	{
		return discount;
	}
	
	public double getOtherCharges()
	{
		return otherCharges;
	}
	
	public double getNetTotal()
	{
		return netTotal;
	}
	
	public boolean getIsReturn()
	{
		return isReturn;
	}
	
	public boolean getIsLoose()
	{
		return isLoose;
	}
	
	public String getShift()
	{
		return shift;
	}
	
	public double getAmountPaid()
	{
		return amountPaid;
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