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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BillingController implements Initializable
{
	
	AnchorPane anchorPane;
	List<Attribute> attributes;
	
	@FXML
	VBox CartVBox;
	
	@SuppressWarnings("exports")
	@FXML
	public ListView<Products> productSearchBar;
	
	@SuppressWarnings("exports")
	@FXML
	public JFXTextField productSearchField;
	
	ProductsRepo productsRepo;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		productsRepo = new ProductsRepo();
		productSearchBar.setStyle("visibility: hidden;");
	}
	
	@SuppressWarnings("exports")
	public void SetRoute(AnchorPane anchorPane, List<Attribute> attributes)
	{
		this.anchorPane = anchorPane;
		this.attributes = attributes;
		
		FlowPane cartRow = new FlowPane();
		cartRow.getStyleClass().add("oddCartRow");
		FlowPane cartRow2 = new FlowPane();
		cartRow2.getStyleClass().add("evenCartRow");
		
		BillCartItem bci = new BillCartItem("Panadol", "12");
		cartRow.getStyleClass().add("cartRowWidth");
		cartRow.getChildren().add(bci.getNameStockBox());
		cartRow.getChildren().add(bci.getPrice());
		cartRow.getChildren().add(bci.getQty());
		cartRow.getChildren().add(bci.getDisc());
		cartRow.getChildren().add(bci.getNetTotal());
		cartRow.setAlignment(Pos.CENTER_LEFT);
		cartRow.getChildren().add(bci.getDelButton());
		
		BillCartItem bci2 = new BillCartItem("Panadol", "12");
		cartRow2.getStyleClass().add("cartRowWidth");
		cartRow2.getChildren().add(bci2.getNameStockBox());
		cartRow2.getChildren().add(bci2.getPrice());
		cartRow2.getChildren().add(bci2.getQty());
		cartRow2.getChildren().add(bci2.getDisc());
		cartRow2.getChildren().add(bci2.getNetTotal());
		cartRow2.setAlignment(Pos.CENTER_LEFT);
		cartRow2.getChildren().add(bci.getDelButton());
		
		CartVBox.getChildren().add(cartRow);
		CartVBox.getChildren().add(cartRow2);
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
			dgController.SetupDataGrid("Billing", attributes, anchorPane);
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
