package application.models.entities;

import java.time.LocalDate;
import java.util.Optional;

import application.models.repositories.ProductsRepo;
import application.models.repositories.PurchasesRepo;
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

public class Purchases {
	
	private String invoiceNum, salesTax, discount, shift, supplierName;
	private String purchaseDate;
	private int supplierId, id;
	private double grossTotal, otherCharges, netTotal, amountPaid;
	private boolean isReturn, isLoose;
	
	private int number;
	private HBox operations;
	
	private static TableView dataGridTable;
	
	public Purchases(int id, int number, int supplierId, String supplierName, String purchaseDate, String invoiceNum, double grossTotal, String salesTax, String discount, double otherCharges, double netTotal, boolean isReturn, boolean isLoose, String shift, double amountPaid)
	{
		this.id = id;
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
		
		delButton.setCursor(Cursor.HAND);
		delButton.setOnMouseClicked(event -> {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		    alert.setTitle("Confirmation Dialog");
		    alert.setHeaderText("Delete Purchase");
		    alert.setContentText("Are you sure you want to delete this purchase?");

		    ButtonType confirmButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
		    ButtonType cancelButton = new ButtonType("No", ButtonBar.ButtonData.NO);
		    alert.getButtonTypes().setAll(confirmButton, cancelButton);

		    Optional<ButtonType> result = alert.showAndWait();
		    if (result.isPresent() && result.get() == confirmButton) {
		        PurchasesRepo purchasesRepo = new PurchasesRepo();
		        purchasesRepo.deletePurchase(this.id);
		        dataGridTable.setItems(FXCollections.observableArrayList(purchasesRepo.deletePurchase(this.id)));
		    }
		});
		
		operations.setMaxWidth(Double.MAX_VALUE);
		operations.setAlignment(Pos.CENTER);
	}
	
	public static void setDataGridTable(TableView table) {
        dataGridTable = table;
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