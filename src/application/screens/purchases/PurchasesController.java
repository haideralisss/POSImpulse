package application.screens.purchases;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import application.components.datagrid.Attribute;
import application.components.datagrid.DataGridController;
import application.models.repositories.ProductsRepo;
import application.models.repositories.SuppliersRepo;
import application.models.entities.Products;
import application.models.entities.Suppliers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class PurchasesController implements Initializable
{
	
	AnchorPane anchorPane;
	List<Attribute> attributes;
	
	@FXML
	VBox CartVBox;
	
	@SuppressWarnings("exports")
	@FXML
	public ListView<Suppliers> supplierSearchBar;
	
	@SuppressWarnings("exports")
	@FXML
	public ListView<Products> productSearchBar;
	
	@SuppressWarnings("exports")
	@FXML
	public JFXTextField supplierTextField, productSearchField;
	
	SuppliersRepo supplierRepo;
	ProductsRepo productsRepo;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		supplierRepo = new SuppliersRepo();
		productsRepo = new ProductsRepo();
		supplierSearchBar.setStyle("visibility: hidden");
		productSearchBar.setStyle("visibility: hidden;");
	}
	
	@SuppressWarnings("exports")
	public void SetRoute(AnchorPane anchorPane, List<Attribute> attributes)
	{
		this.anchorPane = anchorPane;
		this.attributes = attributes;
		
		FlowPane cartRow = new FlowPane();
		cartRow.getStyleClass().add("oddCartRow");
		FlowPane cartRowFooter = new FlowPane();
		cartRowFooter.getStyleClass().add("cartRowFooter");
		FlowPane cartRow2 = new FlowPane();
		cartRow2.getStyleClass().add("evenCartRow");
		
		PurchaseCartItem pci = new PurchaseCartItem("Panadol", "12");
		cartRow.getStyleClass().add("cartRowWidth");
		cartRow.getChildren().add(pci.getNameStockBox());
		cartRow.getChildren().add(pci.getPrice());
		cartRow.getChildren().add(pci.getQty());
		cartRow.getChildren().add(pci.getDisc());
		cartRow.getChildren().add(pci.getSalesTax());
		cartRow.getChildren().add(pci.getBonus());
		cartRow.getChildren().add(pci.getBatchNum());
		cartRow.getChildren().add(pci.getNetTotal());
		cartRow.getChildren().add(pci.getExpiryDate());
		cartRow.setAlignment(Pos.CENTER_LEFT);
		cartRow.getChildren().add(pci.getDelButton());
		
		cartRowFooter.setAlignment(Pos.CENTER_RIGHT);
		Label purchasePriceLabel = new Label("Purchase Price");
		Label retailPriceLabel = new Label("Retail Price");
		purchasePriceLabel.getStyleClass().add("priceLabels");
		purchasePriceLabel.getStyleClass().add("purchasePriceLabel");
		retailPriceLabel.getStyleClass().add("priceLabels");
		retailPriceLabel.getStyleClass().add("retailPriceLabel");
		cartRowFooter.getChildren().add(purchasePriceLabel);
		cartRowFooter.getChildren().add(pci.getPurchasePrice());
		cartRowFooter.getChildren().add(retailPriceLabel);
		cartRowFooter.getChildren().add(pci.getRetailPrice());
		
		CartVBox.getChildren().add(cartRow);
		CartVBox.getChildren().add(cartRowFooter);
		CartVBox.getChildren().add(cartRow2);
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
			dgController.SetupDataGrid("Purchases", attributes, anchorPane);
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
					Products selectedItem = productSearchBar.getSelectionModel().getSelectedItem();
					productSearchField.setText(selectedItem.getName());
					productSearchBar.setStyle("visibility: hidden;");
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
}