package application.models.entities;

import java.time.LocalDate;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class Bills {
	
	String customerName, discount, salesTax, shift;
	int invoiceNum;
	double profit, grossTotal, netTotal, amountPaid;
	boolean isCredit, isReturn;
	String billDate;
	
	private int number;
	private HBox operations;
	
	public Bills()
	{
		this.number = 0;
		this.customerName = "";
		this.invoiceNum = 0;
		this.billDate = "";
		this.grossTotal = 0;
		this.discount = "";
		this.salesTax = "";
		this.netTotal = 0;
		this.amountPaid = 0;
		this.shift = "";
		this.isCredit = false;
		this.isReturn = false;
		this.profit = 0;
		
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
	
	public Bills(int number, String customerName, int invoiceNum, String billDate, double grossTotal, String discount, String salesTax, double netTotal, double amountPaid, String shift, boolean isCredit, boolean isReturn, double profit) 
	{
		this.number = number;
		this.customerName = customerName;
		this.invoiceNum = invoiceNum;
		this.billDate = billDate;
		this.grossTotal = grossTotal;
		this.discount = discount;
		this.salesTax = salesTax;
		this.netTotal = netTotal;
		this.amountPaid = amountPaid;
		this.shift = shift;
		this.isCredit = isCredit;
		this.isReturn = isReturn;
		this.profit = profit;
		
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
	
	public void setStringData(String customerName, String billDate, String discount, String salesTax, String shift) 
	{
		this.customerName = customerName;
		this.billDate = billDate;
		this.discount = discount;
		this.salesTax = salesTax;
		this.shift = shift;
	}
	
	public void setIntegerData(int invoiceNum)
	{
		this.invoiceNum = invoiceNum;
	}
	
	public void setDoubleData(double profit, double grossTotal, double netTotal, double amountPaid)
	{
		this.profit = profit;
		this.grossTotal = grossTotal;
		this.netTotal = netTotal;
		this.amountPaid = amountPaid;
	}
	
	public void setBooleanData(boolean isCredit, boolean isReturn)
	{
		this.isCredit = isCredit;
		this.isReturn = isReturn;
	}
	
	public String getCustomerName() {
        return customerName;
    }

    public int getInvoiceNum() {
        return invoiceNum;
    }

    public String getBillsDate() {
        return billDate;
    }
    
    public String getBillDate() {
        return billDate;
    }

    public double getGrossTotal() {
        return grossTotal;
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

    public double getAmountPaid() {
        return amountPaid;
    }

    public String getShift() {
        return shift;
    }

    public boolean getIsCredit() {
        return isCredit;
    }

    public boolean getIsReturn() {
        return isReturn;
    }

    public double getProfit() {
        return profit;
    }
	
	public int getNumber()
	{
		return number;
	}
	
	public HBox getOperations()
	{
		return operations;
	}
	
	public void setBillDateAndAmountPaid(String billDate, double amountPaid)
	{
		this.billDate = billDate;
		this.amountPaid = amountPaid;
	}
}
