package application.screens.billing;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;

import application.components.datagrid.Attribute;
import application.components.datagrid.DataGridController;
import application.models.entities.BillProducts;
import application.models.entities.Bills;
import application.models.entities.Products;
import application.models.entities.Stock;
import application.models.repositories.BillProductsRepo;
import application.models.repositories.BillsRepo;
import application.models.repositories.ProductsRepo;
import application.models.repositories.StockRepo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class BillingController implements Initializable
{
	
	AnchorPane anchorPane;
	List<Attribute> attributes;
	
	@FXML
	VBox CartVBox;
	
	@FXML
	public ListView<Products> productSearchBar;
	
	@SuppressWarnings("exports")
	@FXML
	public JFXTextField productSearchField, customerName, discount, salesTax, amountCollected, amountPaid;
	
	@SuppressWarnings("exports")
	@FXML
	public Label grossTotalLabel, netTotalLabel, balance;
	
	@SuppressWarnings("exports")
	public JFXCheckBox isCredit, isReturn;
	
	@SuppressWarnings("exports")
	public JFXButton saveBillButton, savenPrintBillButton;
	
	ProductsRepo productsRepo;
	BillsRepo billsRepo;
	BillProductsRepo billsProductsRepo;
	StockRepo stockRepo;
	Products product;
	Bills bills;
	Stock stock;
	
	private double profit;
	
	ArrayList<BillCartItem> billProducts = new ArrayList<>();
	
	public void setBillingScreen(AnchorPane anchorPane)
	{
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		productsRepo = new ProductsRepo();
		billsRepo = new BillsRepo();
		billsProductsRepo = new BillProductsRepo();
		stockRepo = new StockRepo();
		product = new Products();
		bills = new Bills();
		stock = new Stock();
		productSearchBar.setStyle("visibility: hidden;");
		customerName.setText("Cash Sale");
		
	}
	
	@SuppressWarnings("exports")
	public void SetRoute(AnchorPane anchorPane, List<Attribute> attributes, String customerNameValue,
			String discountValue, String salesTaxValue, double grossTotalValue, double netTotalValue, double amountPaidValue,
			boolean isCreditValue, boolean isReturnValue, boolean isView, int billId)
	{
		this.anchorPane = anchorPane;
		this.attributes = attributes;
		if(isView)
		{
			customerName.setText(customerNameValue);
		}
		discount.setText(discountValue);
		salesTax.setText(salesTaxValue);
		grossTotalLabel.setText("Rs. " + grossTotalValue);
		netTotalLabel.setText("Rs. " + netTotalValue);
		amountPaid.setText(String.valueOf(amountPaidValue));
		isCredit.setSelected(isCreditValue);
		isReturn.setSelected(isReturnValue);
		saveBillButton.setDisable(isView);
		savenPrintBillButton.setDisable(isView);
		productSearchField.setDisable(isView);
		if(isView)
		{
			for(BillProducts billProduct : billsProductsRepo.fetchBillProducts(String.valueOf(billId)))
			{
				FlowPane cartRow = new FlowPane();
				cartRow.getStyleClass().add("oddCartRow");
				BillCartItem bci = new BillCartItem(billProduct);
				cartRow.getStyleClass().add("cartRowWidth");
				cartRow.getChildren().add(bci.getNameStockBox());
				cartRow.getChildren().add(bci.getPrice());
				cartRow.getChildren().add(bci.getQty());
				cartRow.getChildren().add(bci.getDisc());
				cartRow.getChildren().add(bci.getNetTotal());
				cartRow.setAlignment(Pos.CENTER_LEFT);
				cartRow.getChildren().add(bci.getDelButton());
				CartVBox.getChildren().add(cartRow);
			}
		}
	}
	
	public void CancelBill()
	{
		try {
			anchorPane.getChildren().clear();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/components/datagrid/DataGrid.fxml"));
			AnchorPane nextAnchorPane;
			nextAnchorPane = (AnchorPane) loader.load();
			anchorPane.getChildren().add(nextAnchorPane);
			AnchorPane.setLeftAnchor(nextAnchorPane, 0.0);
		    nextAnchorPane.toFront();
		    DataGridController dgController = loader.getController();
			dgController.SetupDataGrid("Billing", attributes, anchorPane, null);
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public void searchProduct()
	{
		if(productSearchField.getText().length() > 0)
		{
			ArrayList<Products> items = new ArrayList<>();
			items = productsRepo.fetchByProductName(productSearchField.getText());
			if(items != null && items.size() > 0)
			{
				productSearchBar.setStyle("visibility: visible;");
				productSearchBar.getItems().clear();
				productSearchBar.getItems().addAll(items);
				productSearchBar.setCellFactory(param -> new ListCell<>() {
					@Override
		            protected void updateItem(Products item, boolean empty) {
		                super.updateItem(item, empty);
		                if (empty || item == null) {
		                    setText(null);
		                } else {
		                    setText(item.getName());
		                }
		            }
				});
				
				productSearchBar.setOnMouseClicked(e -> {
					product = productSearchBar.getSelectionModel().getSelectedItem();
					FlowPane cartRow = new FlowPane();
					cartRow.getStyleClass().add("oddCartRow");
					stock = stockRepo.fetchStockByProductId(String.valueOf(product.getId()));
					if(stock == null || stock.getTotalQuantity() <= 0)
					{
						Alert alert = new Alert(AlertType.ERROR);
			        	alert.setTitle("Error");
			        	alert.setHeaderText("Stock Unavailable");
			        	alert.setContentText("Stock for " + product.getName() + " is unavailable");
			        	alert.show();
					}
					else
					{
						BillCartItem bci = new BillCartItem(product.getName(), product.getId(), String.valueOf(stock.getTotalQuantity()), 
								grossTotalLabel, netTotalLabel, billProducts, stock.getId(), stock.getUnitCost(), stock.getTotalQuantity(), 
								product.getPackSize(), product.getPurchasePrice(), product.getRetailPrice(), isReturn);
						cartRow.getStyleClass().add("cartRowWidth");
						cartRow.getChildren().add(bci.getNameStockBox());
						cartRow.getChildren().add(bci.getPrice());
						cartRow.getChildren().add(bci.getQty());
						cartRow.getChildren().add(bci.getDisc());
						cartRow.getChildren().add(bci.getNetTotal());
						cartRow.setAlignment(Pos.CENTER_LEFT);
						cartRow.getChildren().add(bci.getDelButton());
						CartVBox.getChildren().add(cartRow);
						billProducts.add(bci);
						productSearchBar.setStyle("visibility: hidden;");
					}
		        });
			}
			else
			{
				productSearchBar.setStyle("visibility: hidden;");
			}
		}
		else
		{
			productSearchBar.setStyle("visibility: hidden;");
		}
	}
	
	public void changeDiscount() 
	{
	    double gValue = getNumberOnly(grossTotalLabel.getText());
	    double nValue;

	    if (discount.getText().contains("%") && discount.getText().length() > 0) 
	    {
	        double calcDiscount = getNumberOnly(discount.getText()) / 100;
	        double totalValue = gValue * calcDiscount;
	        nValue = gValue - totalValue;

	        if (salesTax.getText().contains("%") && salesTax.getText().length() > 0) 
	        {
	            double calcTax = getNumberOnly(salesTax.getText()) / 100;
	            double taxValue = gValue * calcTax;
	            nValue += taxValue;
	        } 
	        else if (!salesTax.getText().contains("%") && salesTax.getText().length() > 0) 
	        {
	            nValue += Double.parseDouble(salesTax.getText());
	        }
	    } 
	    else if (!discount.getText().contains("%") && discount.getText().length() > 0) 
	    {
	        double discountValue = Double.parseDouble(discount.getText());
	        nValue = gValue - discountValue;

	        if (salesTax.getText().contains("%") && salesTax.getText().length() > 0) 
	        {
	            double calcTax = getNumberOnly(salesTax.getText()) / 100;
	            double taxValue = gValue * calcTax;
	            nValue += taxValue;
	        } 
	        else if (!salesTax.getText().contains("%") && salesTax.getText().length() > 0) 
	        {
	            nValue += Double.parseDouble(salesTax.getText());
	        }
	    } else 
	    {
	        nValue = gValue;

	        if (salesTax.getText().contains("%") && salesTax.getText().length() > 0) 
	        {
	            double calcTax = getNumberOnly(salesTax.getText()) / 100;
	            double taxValue = gValue * calcTax;
	            nValue += taxValue;
	        } 
	        else if (!salesTax.getText().contains("%") && salesTax.getText().length() > 0) 
	        {
	            nValue += Double.parseDouble(salesTax.getText());
	        }
	    }

	    netTotalLabel.setText("Rs. " + String.valueOf(nValue));
	}

	
	public void changeSalesTax()
	{
		double gValue = getNumberOnly(grossTotalLabel.getText());
		double nValue = getNumberOnly(netTotalLabel.getText());
		if(salesTax.getText().contains("%") && salesTax.getText().length() > 0)
		{
			double calcTax = getNumberOnly(salesTax.getText()) / 100;
            double totalValue = gValue * calcTax;
            nValue += totalValue;
			netTotalLabel.setText("Rs. " + String.valueOf(nValue));
		}
		else if (!salesTax.getText().contains("%") && salesTax.getText().length() > 0)
		{
			nValue += Double.parseDouble(salesTax.getText());
			netTotalLabel.setText("Rs. " + String.valueOf(nValue));
		}
		else
		{
			netTotalLabel.setText(String.valueOf(grossTotalLabel.getText()));
			changeDiscount();
		}
	}
	
	public double getNumberOnly(String str) 
	{
	    str = str.replace("Rs.", "");
	    String numericString = str.replaceAll("[^0-9.]", "");
	    return Double.parseDouble(numericString);
	}
	
	public void calculateNewBalance()
	{
		double amountCollectedValue = amountCollected.getText().isEmpty() ? 0 : Double.parseDouble(amountCollected.getText());
		double newBalance = (amountCollectedValue - (getNumberOnly(netTotalLabel.getText())));
		balance.setText("Rs. " + (newBalance < 0 ? "0" : newBalance));
	}
	
	public void calculateProfit()
	{
		for(BillCartItem item : billProducts)
		{
			profit += ((item.getRetailPrice() / item.getPackSize()) - (item.getPurcahsePrice() / item.getPackSize())) * item.getProductQuantity();
		}
		DecimalFormat decimalFormat = new DecimalFormat("#.##");
		String formattedValue = decimalFormat.format(profit);
		profit = Double.parseDouble(formattedValue);
	}
	
	public boolean checkQuantityField()
	{
		for(BillCartItem item : billProducts)
		{
			if(item.getQty().getText().isEmpty())
			{
				return false;
			}
		}
		return true;
	}

	public void saveBill()
	{
		if(amountPaid.getText().isEmpty() || amountPaid.getText().contains("0.0") || amountPaid.getText().contains("0"))
		{
			Alert alert = new Alert(AlertType.ERROR);
        	alert.setTitle("Error");
        	alert.setHeaderText("Error in amount paid field!");
        	alert.setContentText("Amount paid is either not entered or it's value is zero!");
        	alert.show();
		}
		else if (billProducts.size() <= 0)
		{
			Alert alert = new Alert(AlertType.ERROR);
        	alert.setTitle("Error");
        	alert.setHeaderText("No item found in the cart!");
        	alert.setContentText("Please enter some items in the cart first!");
        	alert.show();
		}
		else if(!checkQuantityField())
		{
			Alert alert = new Alert(AlertType.ERROR);
        	alert.setTitle("Error");
        	alert.setHeaderText("Quantity Field is empty!");
        	alert.setContentText("Please check the quantity of every product as one of the quantity is not entered for a product.");
        	alert.show();
		}
		else
		{
			int key = 0;
			calculateProfit();
			key = billsRepo.insertBill(customerName.getText(), LocalDate.now().toString(), getNumberOnly(grossTotalLabel.getText()), 
					discount.getText(), salesTax.getText(), getNumberOnly(netTotalLabel.getText()), getNumberOnly(amountPaid.getText()),
					"haider", isCredit.isSelected(), isReturn.isSelected(), profit);
			stockRepo.updateStocksAfterBill(billProducts);
			billsProductsRepo.addNewBillProducts(billProducts, String.valueOf(key), String.valueOf(getNumberOnly(netTotalLabel.getText())));
			CancelBill();
		}
	}
}
