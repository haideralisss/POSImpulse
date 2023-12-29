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
                new Attribute("Date", "text", "date", "Date", true, false, true),
                new Attribute("Invoice No.", "text", "invoiceNum", "Number", true, false, true),
                new Attribute("Customer Name", "text", "customerName", "String", true, false, true),
                new Attribute("Gross Total", "text", "grossTotal", "Number", false, false, true),
                new Attribute("Discount", "text", "discount", "Number", false, true, true),
                new Attribute("Sales Tax", "text", "salesTax", "Number", false, true, true),
                new Attribute("Net Total", "text", "netTotal", "Number", false, false, false),
                new Attribute("Amount Paid", "text", "amountPaid", "Number", false, false, true),
                new Attribute("Credit", "checkbox", "isCredit", "Boolean", false, false, true),
                new Attribute("Return", "checkbox", "isReturn", "Boolean", false, false, true),
                new Attribute("Shift", "text", "shift", "String", false, false, true)
        );
		changePage("components/datagrid/DataGrid.fxml", list);
	}
	
	public void purchasePage() throws IOException
	{
		topBarLabel.setText("Purchases");
		List<Attribute> list = Arrays.asList(
                new Attribute("Date", "date", "date", "Date", true, false, true),
                new Attribute("Invoice No.", "text", "invoiceNum", "Number", true, false, true),
                new Attribute("Supplier Name", "text", "supplierName", "Number", true, false, true),
                new Attribute("Gross Total", "text", "grossTotal", "Number", false, false, true),
                new Attribute("Discount", "text", "discount", "Number", false, true, true),
                new Attribute("Sales Taxes", "text", "salesTax", "Number", false, true, true),
                new Attribute("Other Charges", "text", "otherCharges", "Number", false, true, true),
                new Attribute("Net Total", "text", "netTotal", "Number", false, false, false),
                new Attribute("Amount Paid", "text", "amountPaid", "Number", false, false, true),
                new Attribute("Loose", "checkbox", "isLoose", "Boolean", false, false, true),
                new Attribute("Return", "checkbox", "isReturn", "Boolean", false, false, true),
                new Attribute("Shift", "text", "shift", "String", false, false, true)
        );
		changePage("components/datagrid/DataGrid.fxml", list);
		
	}

	public void companiesPage() throws IOException
	{
		topBarLabel.setText("Companies");
		List<Attribute> list = Arrays.asList(
                new Attribute("Name", "text", "name", "String", true, false, true),
                new Attribute("Contact", "text", "contact", "String", true, false, true),
                new Attribute("Address", "text", "address", "String", true, false, true)
        );
		changePage("components/datagrid/DataGrid.fxml", list);
	}

	public void suppliersPage() throws IOException
	{
		topBarLabel.setText("Suppliers");
		List<Attribute> list = Arrays.asList(
                new Attribute("Name", "text", "name", "String", true, false, true),
                new Attribute("Contact", "text", "contact", "String", true, false, true),
                new Attribute("Address", "text", "address", "String", true, false, true)
        );
		changePage("components/datagrid/DataGrid.fxml", list);
	}

	public void productsPage() throws IOException
	{
		topBarLabel.setText("Products");
		List<Attribute> list = Arrays.asList(
                new Attribute("Name", "text", "name", "String", true, false, true),
                new Attribute("Pack Size", "text", "packSize", "Number", false, false, true),
                new Attribute("Purchase Price", "text", "purchasePrice", "Number", false, false, true),
                new Attribute("Retail Price", "text", "retailPrice", "Number", false, false, true),
                new Attribute("Company Name", "text", "companyId", "Number", true, false, true, true, "companyId", "companies")
        );
		changePage("components/datagrid/DataGrid.fxml", list);
	}

	public void stockPage() throws IOException
	{
		topBarLabel.setText("Stock");
		List<Attribute> list = Arrays.asList(
                new Attribute("Product Name", "text", "productId", "String", true, false, true, true, "productId", "products"),
                new Attribute("Quantity", "text", "totalQuantity", "Number", false, false, true),
                new Attribute("Unit Cost", "text", "unitCost", "Number", false, false, true),
                new Attribute("Total Price", "text", "totalPrice", "Number", false, false, false),
                new Attribute("Expiry Date", "date", "expiryDate", "Date", true, false, true)
        );
		changePage("components/datagrid/DataGrid.fxml", list);
	}

	public void expensesPage() throws IOException
	{
		topBarLabel.setText("Expenses");
		List<Attribute> list = Arrays.asList(
                new Attribute("Date", "date", "expenseDate", "Date", true, false, true),
                new Attribute("Name", "text", "name", "String", true, false, true),
                new Attribute("Description", "text", "description", "String", false, false, true),
                new Attribute("Amount", "text", "amount", "Number", false, false, true)
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
				new Attribute("Username", "text", "userName", "String", true, false, true),
				new Attribute("Full Name", "text", "fullName", "String", true, false, true),
				new Attribute("Phone", "text", "phone", "String", true, false, true),
				new Attribute("Password", "password", "password", "String", false, true, true),
				new Attribute("Admin", "checkbox", "isAdmin", "Boolean", false, false, true)
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