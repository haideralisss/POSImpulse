package application.screens.purchases;

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
import application.models.repositories.ProductsRepo;
import application.models.repositories.PurchasedProductsRepo;
import application.models.repositories.PurchasesRepo;
import application.models.repositories.StockRepo;
import application.models.repositories.SuppliersRepo;
import application.screens.billing.BillCartItem;
import application.models.entities.Products;
import application.models.entities.PurchasedProducts;
import application.models.entities.Purchases;
import application.models.entities.Stock;
import application.models.entities.Suppliers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class PurchasesController implements Initializable
{
	
	AnchorPane anchorPane;
	List<Attribute> attributes;
	
	@FXML
	VBox CartVBox;
	
	@FXML
	Pane cartHeader;
	
	@FXML
	public ListView<Suppliers> supplierSearchBar;
	
	@FXML
	public ListView<Products> productSearchBar;
	
	@SuppressWarnings("exports")
	@FXML
	public JFXTextField supplierTextField, productSearchField, invoiceNum, discount, salesTax, otherCharges, amountPaid;
	
	@SuppressWarnings("exports")
	public JFXButton saveBillButton, savenPrintBillButton;
	
	@SuppressWarnings("exports")
	@FXML
	public Label grossTotalLabel, netTotalLabel;
	
	@SuppressWarnings("exports")
	@FXML
	public JFXCheckBox isReturn, isLoose;
	
	@SuppressWarnings("exports")
	@FXML
	public DatePicker purchaseDate;
	
	PurchasesRepo purchasesRepo;
	StockRepo stockRepo;
	Products product;
	SuppliersRepo supplierRepo;
	ProductsRepo productsRepo;
	PurchasedProductsRepo purchasedProductsRepo;
	Purchases purchases;
	Stock stock;
	int i = 1;
	
	private int supplierId;
	private int cNetTotal = 0;
	
	ArrayList<PurchaseCartItem> purchaseProducts = new ArrayList<>();
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		supplierRepo = new SuppliersRepo();
		productsRepo = new ProductsRepo();
		productsRepo = new ProductsRepo();
		purchasesRepo = new PurchasesRepo();
		purchasedProductsRepo = new PurchasedProductsRepo();
		stockRepo = new StockRepo();
		product = new Products();
		purchases = new Purchases();
		stock = new Stock();
		supplierSearchBar.setStyle("visibility: hidden");
		productSearchBar.setStyle("visibility: hidden;");
		cartHeader.setStyle("visibility: hidden;");
		
		isLoose.setOnAction(event -> {
			purchaseProducts.clear();
			cartHeader.setStyle("visibility: hidden;");
			CartVBox.getChildren().clear();
			CartVBox.getChildren().add(cartHeader);
		});
	}
	
	@SuppressWarnings("exports")
	public void SetRoute(AnchorPane anchorPane, List<Attribute> attributes, int supplierId, int purchaseId,  String invoiceNumVal, String dateVal,
			String discountVal, String salesTaxVal, String otherChargesVal, String grossTotalVal, String netTotalVal, String amountPaidVal, 
			boolean isLooseVal, boolean isReturnVal, boolean isView)
	{
		this.anchorPane = anchorPane;
		this.attributes = attributes;
		invoiceNum.setText(invoiceNumVal);
		purchaseDate.setValue(LocalDate.parse(dateVal));
		discount.setText(discountVal);
		salesTax.setText(salesTaxVal);
		otherCharges.setText(otherChargesVal);
		grossTotalLabel.setText("Rs. " + grossTotalVal);
		netTotalLabel.setText("Rs. " + netTotalVal);
		amountPaid.setText(amountPaidVal);
		supplierTextField.setText(supplierRepo.getSupplier(supplierId).getName());
		isLoose.setSelected(isLooseVal);
		isReturn.setSelected(isReturnVal);
		
		saveBillButton.setDisable(isView);
		savenPrintBillButton.setDisable(isView);
		
		if(isView)
		{
			cartHeader.setStyle("visibility: visible; -fx-background-color: #02182B;");
		}
		
		for(PurchasedProducts product : purchasedProductsRepo.getPurchasedProductFromPurchaseId(purchaseId))
		{
			AnchorPane cartRow = new AnchorPane();
			FlowPane cartRowFooter = new FlowPane();
			cartRowFooter.getStyleClass().add("cartRowFooter");
			if(i % 2 == 0)
				cartRow.getStyleClass().add("evenCartRow");
			else
				cartRow.getStyleClass().add("oddCartRow");	
			PurchaseCartItem pci = new PurchaseCartItem(product);
			cartRow.getStyleClass().add("cartRowWidth");
			cartRow.getChildren().add(pci.getNameStockBox());
			cartRow.getChildren().add(pci.getPrice());
			cartRow.getChildren().add(pci.getQty());
			cartRow.getChildren().add(pci.getDisc());
			cartRow.getChildren().add(pci.getSalesTax());
			cartRow.getChildren().add(pci.getBonus());
			cartRow.getChildren().add(pci.getBatchNum());
			cartRow.getChildren().add(pci.getNetTotal());
			cartRow.getChildren().add(pci.getDelButton());
			CartVBox.getChildren().add(cartRow);
			i++;
		}
	}
	
	public void CancelPurchase()
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
			dgController.SetupDataGrid("Purchases", attributes, anchorPane, null);
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public void searchSupplier()
	{
		if(supplierTextField.getText().length() > 0)
		{
			ArrayList<Suppliers> items = new ArrayList<>();
			items = supplierRepo.fetchIdSupplierName(supplierTextField.getText());
			if(items != null && items.size() > 0)
			{
				supplierSearchBar.setStyle("visibility: visible;");
				supplierSearchBar.getItems().clear();
				supplierSearchBar.getItems().addAll(items);
				supplierSearchBar.setCellFactory(param -> new ListCell<>() {
					@Override
		            protected void updateItem(Suppliers item, boolean empty) {
		                super.updateItem(item, empty);
		                if (empty || item == null) {
		                    setText(null);
		                } else {
		                    setText(item.getName());
		                }
		            }
				});
				
				supplierSearchBar.setOnMouseClicked(e -> {
					Suppliers selectedItem = supplierSearchBar.getSelectionModel().getSelectedItem();
		            supplierTextField.setText(selectedItem.getName());
		            supplierId = selectedItem.getNumber();
		            supplierSearchBar.setStyle("visibility: hidden;");
		        });
			}
			else
			{
				supplierSearchBar.setStyle("visibility: hidden;");
			}
		}
		else
		{
			supplierSearchBar.setStyle("visibility: hidden;");
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
					AnchorPane cartRow = new AnchorPane();
					FlowPane cartRowFooter = new FlowPane();
					cartRowFooter.getStyleClass().add("cartRowFooter");
					if(purchaseProducts.size() % 2 == 0)
						cartRow.getStyleClass().add("evenCartRow");
					else
						cartRow.getStyleClass().add("oddCartRow");
					stock = stockRepo.fetchStockByProductId(String.valueOf(product.getId()));
					if(isReturn.isSelected() && (stock == null || stock.getTotalQuantity() <= 0))
					{
						Alert alert = new Alert(AlertType.ERROR);
			        	alert.setTitle("Error");
			        	alert.setHeaderText("Stock Unavailable");
			        	alert.setContentText("Stock for " + product.getName() + " is unavailable");
			        	alert.show();
					}
					else
					{
						PurchaseCartItem pci = new PurchaseCartItem(product.getName(), product.getId(), String.valueOf(stock != null ? stock.getTotalQuantity() : 0), 
								grossTotalLabel, netTotalLabel, purchaseProducts, stock != null ? stock.getId() : 0, stock != null ? stock.getUnitCost() : 0, 
								(stock != null ? stock.getTotalQuantity() : 0), product.getPackSize(), product.getPurchasePrice(), 
								product.getRetailPrice(), isReturn, isLoose);
						cartRow.getStyleClass().add("cartRowWidth");
						cartRow.getChildren().add(pci.getNameStockBox());
						cartRow.getChildren().add(pci.getPrice());
						cartRow.getChildren().add(pci.getQty());
						cartRow.getChildren().add(pci.getDisc());
						cartRow.getChildren().add(pci.getSalesTax());
						cartRow.getChildren().add(pci.getBonus());
						cartRow.getChildren().add(pci.getBatchNum());
						cartRow.getChildren().add(pci.getNetTotal());
						cartRow.getChildren().add(pci.getDelButton());
						CartVBox.getChildren().add(cartRow);
						purchaseProducts.add(pci);
						
						pci.getDelButton().setOnMouseClicked(event -> {
							purchaseProducts.remove(pci);
					        CartVBox.getChildren().remove(cartRow);
					        CartVBox.getChildren().remove(cartRowFooter);
					        if(purchaseProducts.size() == 0)
								cartHeader.setStyle("visibility: hidden; -fx-background-color: #02182B;");
					    });
						
						if(purchaseProducts.size() != 0)
							cartHeader.setStyle("visibility: visible; -fx-background-color: #02182B;");
						
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

	    netTotalLabel.setText("Rs. " + String.format("%.2f", nValue));
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
			netTotalLabel.setText("Rs. " + String.format("%.2f", nValue));
		}
		else if (!salesTax.getText().contains("%") && salesTax.getText().length() > 0)
		{
			nValue += Double.parseDouble(salesTax.getText());
			netTotalLabel.setText("Rs. " + String.format("%.2f", nValue));
		}
		else
		{
			netTotalLabel.setText(String.valueOf(grossTotalLabel.getText()));
			changeDiscount();
		}
	}

	public void changeOtherCharges() {
	    double gValue = getNumberOnly(grossTotalLabel.getText());
	    double nValue = getNumberOnly(netTotalLabel.getText());

	    if (otherCharges.getText().length() > 0) {
	        nValue += Double.parseDouble(otherCharges.getText());
	        netTotalLabel.setText("Rs. " + String.format("%.2f", nValue));
	    } else {
	        netTotalLabel.setText("Rs. " + String.format("%.2f", gValue));
	        changeDiscount();
	    }
	}
	
	public double getNumberOnly(String str) 
	{
	    str = str.replace("Rs.", "");
	    String numericString = str.replaceAll("[^0-9.]", "");
	    return Double.parseDouble(numericString);
	}

	public void savePurchase()
	{
		int purchaseId = purchasesRepo.addPurchase(supplierId, LocalDate.now().toString(), invoiceNum.getText(), getNumberOnly(grossTotalLabel.getText()), 
				salesTax.getText(), discount.getText(), Double.valueOf(otherCharges.getText() != "" ? otherCharges.getText() : "0"), 
				getNumberOnly(netTotalLabel.getText()), isReturn.isSelected(), isLoose.isSelected(), "haider", Double.valueOf(amountPaid.getText()));
		purchasedProductsRepo.addPurchasedProduct(purchaseProducts, String.valueOf(purchaseId));
		stockRepo.updateStocksAfterPurchase(purchaseProducts);
		CancelPurchase();
	}
}