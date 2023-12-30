package application.screens.sidebar;

import java.io.IOException;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import application.components.datagrid.Attribute;
import application.components.datagrid.DataGridController;
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
	
	public DataGridController dgController;
	
	public void dashboardPage() throws IOException
	{
		topBarLabel.setText("Dashboard");
		changePage("screens/dashboard/Dashboard.fxml", null);
	}
	
	public void billingPage() throws IOException
	{
		topBarLabel.setText("Billing");
		List<Attribute> list = Arrays.asList(
                new Attribute("Date", "text", "billDate", "date", true, false, true),
                new Attribute("Invoice No.", "text", "invoiceNum", "integer", true, false, true),
                new Attribute("Customer Name", "text", "customerName", "text", true, true, true),
                new Attribute("Gross Total", "text", "grossTotal", "real", false, true, true),
                new Attribute("Discount", "text", "discount", "text", false, true, true),
                new Attribute("Sales Tax", "text", "salesTax", "text", false, true, true),
                new Attribute("Net Total", "text", "netTotal", "real", false, false, false),
                new Attribute("Amount Paid", "text", "amountPaid", "real", false, false, true),
                new Attribute("Credit", "checkbox", "isCredit", "numeric", false, true, true),
                new Attribute("Return", "checkbox", "isReturn", "numeric", false, false, true),
                new Attribute("Shift", "text", "shift", "text", false, false, true)
        );
		changePage("components/datagrid/DataGrid.fxml", list);
	}
	
	public void purchasePage() throws IOException
	{
		topBarLabel.setText("Purchases");
		List<Attribute> list = Arrays.asList(
                new Attribute("Date", "date", "purchaseDate", "date", true, false, true),
                new Attribute("Invoice No.", "text", "invoiceNum", "text", true, false, true),
                new Attribute("Supplier Name", "text", "supplierId", "integer", false, true, true),
                new Attribute("Supplier Name", "text", "supplierName", "text", true, false, false),
                new Attribute("Gross Total", "text", "grossTotal", "real", false, true, true),
                new Attribute("Discount", "text", "discount", "text", false, true, true),
                new Attribute("Sales Taxes", "text", "salesTax", "text", false, true, true),
                new Attribute("Other Charges", "text", "otherCharges", "real", false, true, true),
                new Attribute("Net Total", "text", "netTotal", "real", false, false, false),
                new Attribute("Amount Paid", "text", "amountPaid", "real", false, false, true),
                new Attribute("Loose", "checkbox", "isLoose", "numeric", false, true, true),
                new Attribute("Return", "checkbox", "isReturn", "numeric", false, false, true),
                new Attribute("Shift", "text", "shift", "text", false, false, true)
        );
		changePage("components/datagrid/DataGrid.fxml", list);
		
	}

	public void companiesPage() throws IOException
	{
		topBarLabel.setText("Companies");
		List<Attribute> list = Arrays.asList(
                new Attribute("Name", "text", "name", "text", true, false, true),
                new Attribute("Contact", "text", "contact", "text", true, false, true),
                new Attribute("Address", "text", "address", "text", true, false, true)
        );
		changePage("components/datagrid/DataGrid.fxml", list);
	}

	public void suppliersPage() throws IOException
	{
		topBarLabel.setText("Suppliers");
		List<Attribute> list = Arrays.asList(
                new Attribute("Name", "text", "name", "text", true, false, true),
                new Attribute("Contact", "text", "contact", "text", true, false, true),
                new Attribute("Address", "text", "address", "text", true, false, true)
        );
		changePage("components/datagrid/DataGrid.fxml", list);
	}

	public void productsPage() throws IOException
	{
		topBarLabel.setText("Products");
		List<Attribute> list = Arrays.asList(
                new Attribute("Name", "text", "name", "text", true, false, true),
                new Attribute("Pack Size", "text", "packSize", "integer", false, false, true),
                new Attribute("Purchase Price", "text", "purchasePrice", "real", false, false, true),
                new Attribute("Retail Price", "text", "retailPrice", "real", false, false, true),
                new Attribute("Company Name", "text", "companyId", "integer", false, true, true),
                new Attribute("Company Name", "text", "companyName", "text", true, false, false)
        );
		changePage("components/datagrid/DataGrid.fxml", list);
	}

	public void stockPage() throws IOException
	{
		topBarLabel.setText("Stock");
		List<Attribute> list = Arrays.asList(
				new Attribute("Product Name", "text", "productId", "integer", false, true, true),
                new Attribute("Product Name", "text", "productName", "text", true, false, false),
                new Attribute("Quantity", "text", "totalQuantity", "integer", false, false, true),
                new Attribute("Unit Cost", "text", "unitCost", "real", false, false, true),
                new Attribute("Total Price", "text", "totalPrice", "real", false, false, false)
        );
		changePage("components/datagrid/DataGrid.fxml", list);
	}

	public void expensesPage() throws IOException
	{
		topBarLabel.setText("Expenses");
		List<Attribute> list = Arrays.asList(
                new Attribute("Date", "date", "expenseDate", "date", true, false, true),
                new Attribute("Name", "text", "name", "text", true, false, true),
                new Attribute("Description", "text", "description", "text", false, false, true),
                new Attribute("Amount", "text", "amount", "real", false, false, true)
        );
		changePage("components/datagrid/DataGrid.fxml", list);
	}

	public void reportsPage() throws IOException
	{
		topBarLabel.setText("Reports");
		changePage("screens/reports/Reports.fxml", null);
	}

	public void adminpanelPage() throws IOException
	{
		topBarLabel.setText("Admin Panel");
		List<Attribute> list = Arrays.asList(
				new Attribute("Username", "text", "userName", "text", true, false, true),
				new Attribute("Full Name", "text", "fullName", "text", true, false, true),
				new Attribute("Phone", "text", "phone", "text", true, false, true),
				new Attribute("Password", "password", "password", "text", false, true, true),
				new Attribute("Admin", "checkbox", "isAdmin", "numeric", false, false, true)
		);
		changePage("components/datagrid/DataGrid.fxml", list);
	}
	
	public void profilePage() throws IOException
	{
		topBarLabel.setText("Profile");
		changePage("screens/profile/Profile.fxml", null);
	}
	
	public void closeProgram()
	{
		Platform.exit();
	}
	
	public void changePage(String path, List<Attribute> list)
	{
		try
		{
		anchorPane.getChildren().clear();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/" + path));
		nextAnchorPane = (AnchorPane) loader.load();
		anchorPane.getChildren().add(nextAnchorPane);
		AnchorPane.setLeftAnchor(nextAnchorPane, 0.0);
	    nextAnchorPane.toFront();
		
		DataGridController dgController;
		
		if(!path.contains("reports") && !path.contains("dashboard") && !path.contains("profile"))
		{
			dgController = loader.getController();
			dgController.SetupDataGrid((topBarLabel.getText() == "Admin Panel" ? "Accounts" : topBarLabel.getText()), list, anchorPane);
		}
		}
		catch(IOException e)
		{
			System.out.println(e.getCause());
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		changePage("screens/dashboard/Dashboard.fxml", null);
	}
}