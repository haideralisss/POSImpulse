package application.models.entities;

import java.io.IOException;
import java.util.List;

import application.components.datagrid.Attribute;
import application.screens.purchases.PurchasesController;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class Purchases {
	
	private String invoiceNum, salesTax, discount, shift, supplierName, purchaseDate;
	private int supplierId, id;
	private double grossTotal, otherCharges, netTotal, amountPaid;
	private boolean isReturn, isLoose;
	
	private int number;
	private HBox operations;
	
	@SuppressWarnings("unused")
	private static TableView<Purchases> dataGridTable;
	@SuppressWarnings("unused")
	private static String title;
	private static List<Attribute> attributes;
	private static AnchorPane anchorPane;
	
	public Purchases()
	{
		id = number = supplierId = 0;
		invoiceNum = salesTax = discount = shift = supplierName = purchaseDate = "";
		grossTotal = otherCharges = netTotal = amountPaid = 0;
		isReturn = isLoose = false;
	}
	
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
		
		HBox editHBox = new HBox();
		Button button = new Button();
		button.setText("View");
		button.setMinWidth(50);
		button.setMaxWidth(50);
		button.setMaxHeight(15);
		button.setStyle("-fx-background-color: #385cfc; -fx-text-fill: #fff; -fx-font-size: 10px;  -fx-border-radius: 5;"
				+ "    -fx-background-radius: 5;"
				+ "    -fx-cursor: hand;");
		operations = new HBox();
		operations.getChildren().add(editHBox);
		
		editHBox.setMaxWidth(Double.MAX_VALUE);
		editHBox.setAlignment(Pos.CENTER);
		editHBox.getChildren().add(button);
		editHBox.setCursor(Cursor.HAND);
		
		button.setOnAction(event -> {
			try
			{
				anchorPane.getChildren().clear();
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/screens/purchases/Purchases.fxml"));
				AnchorPane nextAnchorPane;
				nextAnchorPane = (AnchorPane) loader.load();
				anchorPane.getChildren().add(nextAnchorPane);
				AnchorPane.setLeftAnchor(nextAnchorPane, 0.0);
			    nextAnchorPane.toFront();
			    
			    PurchasesController purchaseController;
			    purchaseController = loader.getController();
			    purchaseController.SetRoute(nextAnchorPane, attributes, supplierId, id, invoiceNum, purchaseDate, discount, salesTax,
			    		String.valueOf(otherCharges), String.valueOf(grossTotal), String.valueOf(netTotal), String.valueOf(amountPaid),
			    		isLoose, isReturn, true);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		});
		
		operations.setMaxWidth(Double.MAX_VALUE);
		operations.setAlignment(Pos.CENTER);
	}
	
	@SuppressWarnings("exports")
	public static void setDataGridTable(TableView<Purchases> table, String Title, List<Attribute> Attributes, AnchorPane AnchorPANE) {
        dataGridTable = table;
        title = Title;
        attributes = Attributes;
        anchorPane = AnchorPANE;
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
	
	public String getIsReturn()
	{
		return (isReturn ? "Yes" : "No");
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
}