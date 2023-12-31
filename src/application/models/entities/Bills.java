package application.models.entities;

import java.util.List;
import java.util.Optional;

import application.components.datagrid.Attribute;
import application.models.repositories.BillsRepo;
import javafx.collections.FXCollections;
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

public class Bills 
{
	
	private String customerName, discount, salesTax, shift;
	private int invoiceNum, id;
	private double profit, grossTotal, netTotal, amountPaid;
	private boolean isCredit, isReturn;
	private String billDate;
	
	private int number;
	private HBox operations;
	
	private static TableView<Bills> dataGridTable;
	//private static String title;
	//private static List<Attribute> attributes;
	//private static AnchorPane anchorPane;
	
	public Bills()
	{
		this.id = 0;
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
		
		HBox delHBox = new HBox();
		HBox editHBox = new HBox();
		ImageView delButton = new ImageView();
		Image delIcon = new Image("file:///C:/Users/ALI/eclipse-workspace/POSImpulse/src/assets/deleteIcon.png");
		delButton.setImage(delIcon);
		delButton.setFitWidth(15);
		delButton.setFitHeight(15);
		ImageView editButton = new ImageView();
		Image editIcon = new Image("file:///C:/Users/ALI/eclipse-workspace/POSImpulse/src/assets/editIcon.png");
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
			
		});
		
		delHBox.getChildren().add(delButton);
		delHBox.setMaxWidth(Double.MAX_VALUE);
		delHBox.setAlignment(Pos.CENTER);
		delHBox.setCursor(Cursor.HAND);
		delHBox.setOnMouseClicked(event -> {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		    alert.setTitle("Confirmation Dialog");
		    alert.setHeaderText("Delete Bill");
		    alert.setContentText("Are you sure you want to delete this bill?");

		    ButtonType confirmButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
		    ButtonType cancelButton = new ButtonType("No", ButtonBar.ButtonData.NO);
		    alert.getButtonTypes().setAll(confirmButton, cancelButton);

		    Optional<ButtonType> result = alert.showAndWait();
		    if (result.isPresent() && result.get() == confirmButton) {
		        BillsRepo billsRepo = new BillsRepo();
		        dataGridTable.setItems(FXCollections.observableArrayList(billsRepo.deleteBill(this.id)));
		    }
		});
		
		operations.setMaxWidth(Double.MAX_VALUE);
		operations.setAlignment(Pos.CENTER);
	}
	
	public Bills(int id, int number, String customerName, int invoiceNum, String billDate, double grossTotal, String discount, String salesTax, double netTotal, double amountPaid, String shift, boolean isCredit, boolean isReturn, double profit) 
	{
		this.id = id;
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
		
		HBox delHBox = new HBox();
		ImageView delButton = new ImageView();
		Image delIcon = new Image("file:///C:/Users/ALI/eclipse-workspace/POSImpulse/src/assets/deleteIcon.png");
		delButton.setImage(delIcon);
		delButton.setFitWidth(15);
		delButton.setFitHeight(15);
		ImageView editButton = new ImageView();
		Image editIcon = new Image("file:///C:/Users/ALI/eclipse-workspace/POSImpulse/src/assets/editIcon.png");
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
		    alert.setHeaderText("Delete Bill");
		    alert.setContentText("Are you sure you want to delete this bill?");

		    ButtonType confirmButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
		    ButtonType cancelButton = new ButtonType("No", ButtonBar.ButtonData.NO);
		    alert.getButtonTypes().setAll(confirmButton, cancelButton);

		    Optional<ButtonType> result = alert.showAndWait();
		    if (result.isPresent() && result.get() == confirmButton) {
		        BillsRepo billsRepo = new BillsRepo();
		        billsRepo.deleteBill(this.id);
		        dataGridTable.setItems(FXCollections.observableArrayList(billsRepo.deleteBill(this.id)));
		    }
		});
		
		operations.setMaxWidth(Double.MAX_VALUE);
		operations.setAlignment(Pos.CENTER);
	}
	
	@SuppressWarnings("exports")
	public static void setDataGridTable(TableView<Bills> table, String Title, List<Attribute> Attributes, AnchorPane AnchorPANE) {
        dataGridTable = table;
        //title = Title;
        //attributes = Attributes;
        //anchorPane = AnchorPANE;
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
	
	public void setBillDateAndProfit(String billDate, double profit)
	{
		this.billDate = billDate;
		this.profit = profit;
	}
	
	public void setInvoiceNum(int invoiceNum)
	{
		this.invoiceNum = invoiceNum;
	}
	
	public String getCustomerName() {
        return customerName;
    }

    public int getInvoiceNum() {
        return invoiceNum;
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

    public String getIsReturn() {
        return (isReturn ? "Yes" : "No");
    }
    
    public double getProfit() {
        return profit;
    }
    
    public int getId()
	{
		return id;
	}
	
	public int getNumber()
	{
		return number;
	}
	
	@SuppressWarnings("exports")
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
