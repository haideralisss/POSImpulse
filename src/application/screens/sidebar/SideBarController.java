package application.screens.sidebar;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class SideBarController implements Initializable {
	
	@SuppressWarnings("exports")
	@FXML
	public AnchorPane anchorPane, nextAnchorPane;
	
	@SuppressWarnings("exports")
	@FXML
	public Label topBarLabel;
	
	public void dashboardPage() throws IOException
	{
		topBarLabel.setText("Dashboard");
		changePage("dashboard", "Dashboard.fxml");
	}
	
	public void billingPage() throws IOException
	{
		topBarLabel.setText("Billing");
		changePage("billing", "Billing.fxml");
	}
	
	public void purchasePage() throws IOException
	{
		topBarLabel.setText("Purchases");
		changePage("purchases", "Purchases.fxml");
	}

	public void companiesPage() throws IOException
	{
		topBarLabel.setText("Companies");
		changePage("companies", "Companies.fxml");
	}

	public void suppliersPage() throws IOException
	{
		topBarLabel.setText("Suppliers");
		changePage("suppliers", "Suppliers.fxml");
	}

	public void productsPage() throws IOException
	{
		topBarLabel.setText("Products");
		changePage("products", "Products.fxml");
	}

	public void stockPage() throws IOException
	{
		topBarLabel.setText("Stock");
		changePage("stock", "Stock.fxml");
	}

	public void expensesPage() throws IOException
	{
		topBarLabel.setText("Expenses");
		changePage("expenses", "Expenses.fxml");
	}

	public void reportsPage() throws IOException
	{
		topBarLabel.setText("Reports");
		changePage("reports", "Reports.fxml");
	}

	public void adminpanelPage() throws IOException
	{
		topBarLabel.setText("Admin Panel");
		changePage("adminpanel", "Adminpanel.fxml");
	}
	
	public void closeProgram()
	{
		Platform.exit();
	}
	
	public void changePage(String folder, String file) throws IOException
	{
		anchorPane.getChildren().clear();
		nextAnchorPane = (AnchorPane) FXMLLoader.load(getClass().getResource("/application/screens/" + folder + "/" + file));
		anchorPane.getChildren().add(nextAnchorPane);
		AnchorPane.setLeftAnchor(nextAnchorPane, 0.0);
	    nextAnchorPane.toFront();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		try {
			changePage("dashboard", "Dashboard.fxml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}