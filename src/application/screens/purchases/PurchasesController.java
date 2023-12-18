package application.screens.purchases;

import java.io.IOException;
import java.util.List;

import application.components.datagrid.Attribute;
import application.components.datagrid.DataGridController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class PurchasesController {
	
	AnchorPane anchorPane;
	List<Attribute> attributes;
	
	public void SetRoute(AnchorPane anchorPane, List<Attribute> attributes)
	{
		this.anchorPane = anchorPane;
		this.attributes = attributes;
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