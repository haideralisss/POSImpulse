package application.screens.billing;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import application.components.datagrid.Attribute;
import application.components.datagrid.DataGridController;
import application.models.entities.Products;
import application.models.repositories.ProductsRepo;
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
	public JFXTextField productSearchField, customerName, discount, salesTax;
	
	@SuppressWarnings("exports")
	@FXML
	public Label grossTotalLabel, netTotalLabel;
	
	ProductsRepo productsRepo;
	Products product;
	
	ArrayList<BillCartItem> billProducts = new ArrayList<>();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		productsRepo = new ProductsRepo();
		product = new Products();
		productSearchBar.setStyle("visibility: hidden;");
		customerName.setText("Cash Sale");
		
	}
	
	@SuppressWarnings("exports")
	public void SetRoute(AnchorPane anchorPane, List<Attribute> attributes)
	{
		this.anchorPane = anchorPane;
		this.attributes = attributes;
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
					BillCartItem bci = new BillCartItem(product.getName(), "12", grossTotalLabel, netTotalLabel, billProducts);
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
		if(discount.getText().length() > 0)
		{
			double gValue = getNumberOnly(grossTotalLabel.getText());
			double nValue = getNumberOnly(netTotalLabel.getText());
			nValue = (gValue - Double.parseDouble(discount.getText()));
			netTotalLabel.setText(String.valueOf(nValue));
		}
		else
		{
			netTotalLabel.setText(String.valueOf(grossTotalLabel.getText()));
		}
	}
	
	public void changeSalesTax()
	{
		if(salesTax.getText().length() > 0)
		{
			double nValue = getNumberOnly(grossTotalLabel.getText());
			nValue += Double.parseDouble(salesTax.getText());
			netTotalLabel.setText(String.valueOf(nValue));
		}
		else
		{
			netTotalLabel.setText(String.valueOf(grossTotalLabel.getText()));
		}
	}
	
	public double getNumberOnly(String str) 
	{
	    str = str.replace("Rs.", "");
	    String numericString = str.replaceAll("[^0-9.]", "");
	    return Double.parseDouble(numericString);
	}
}
