package application.screens.billing;

import java.io.IOException;
import java.util.List;

import application.components.datagrid.Attribute;
import application.components.datagrid.DataGridController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class BillingController {
	
	AnchorPane anchorPane;
	List<Attribute> attributes;
	
	@FXML
	VBox CartVBox;
	
	
	public void SetRoute(@SuppressWarnings("exports") AnchorPane anchorPane, List<Attribute> attributes)
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
}
