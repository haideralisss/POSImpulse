package application.screens.purchases;

import java.io.IOException;
import java.util.List;

import application.components.datagrid.Attribute;
import application.components.datagrid.DataGridController;
import application.screens.billing.BillCartItem;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class PurchasesController {
	
	AnchorPane anchorPane;
	List<Attribute> attributes;
	
	@FXML
	VBox CartVBox;
	
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
}