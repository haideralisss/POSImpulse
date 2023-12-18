package application.screens.billing;

import java.io.IOException;
import java.util.List;

import application.components.datagrid.Attribute;
import application.components.datagrid.DataGridController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class BillingController {
	
	AnchorPane anchorPane;
	List<Attribute> attributes;
	
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
			dgController.SetupDataGrid("Billing", attributes, anchorPane);
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
}
