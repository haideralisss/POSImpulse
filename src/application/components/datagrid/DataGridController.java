package application.components.datagrid;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import application.components.inputform.InputFormController;
import application.models.entities.Accounts;
import application.models.entities.Bills;
import application.models.entities.Companies;
import application.models.entities.Expenses;
import application.models.entities.Products;
import application.models.entities.Purchases;
import application.models.entities.Stock;
import application.models.entities.Suppliers;
import application.models.repositories.AccountsRepo;
import application.models.repositories.BillsRepo;
import application.models.repositories.CompaniesRepo;
import application.models.repositories.ExpensesRepo;
import application.models.repositories.ProductsRepo;
import application.models.repositories.PurchasesRepo;
import application.models.repositories.StockRepo;
import application.models.repositories.SuppliersRepo;
import application.screens.billing.BillingController;
import application.screens.purchases.PurchasesController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

public class DataGridController implements Initializable
{
	@SuppressWarnings("exports")
	@FXML
	public Label title;
	
	@SuppressWarnings("exports")
	@FXML
	public FlowPane buttonsFlowPane;
	
	@SuppressWarnings({ "exports", "rawtypes" })
	@FXML
	public TableView dataGridTable;
	
	@FXML 
	public ComboBox<String> category;
	
	@SuppressWarnings("exports")
	@FXML
	public ImageView titleIcon;
	
	@SuppressWarnings("exports")
	public AnchorPane anchorPane;
	
	@SuppressWarnings({ "exports", "unchecked" })
	@FXML
	public JFXTextField searchBar;
	
	@SuppressWarnings("exports")
	@FXML
	public DatePicker datePicker;
	
	List<Attribute> attributes;
	
	BillsRepo billsRepo = new BillsRepo();
	PurchasesRepo purchasesRepo = new PurchasesRepo();
	CompaniesRepo companiesRepo = new CompaniesRepo();
	SuppliersRepo suppliersRepo = new SuppliersRepo();
	ProductsRepo productsRepo = new ProductsRepo();
	StockRepo stockRepo = new StockRepo();
	ExpensesRepo expensesRepo = new ExpensesRepo();

	ObservableList<Bills> billsList;
	ObservableList<Purchases> purchasesList;
	ObservableList<Companies> companiesList;
	ObservableList<Suppliers> suppliersList;
	ObservableList<Products> productsList;
	ObservableList<Stock> stockList;
	ObservableList<Expenses> expensesList;
	ObservableList<Accounts> accountsList;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		datePicker.setStyle("visibility: visible");
	}
	
	@SuppressWarnings({ "unchecked", "exports" })
	public void SetupDataGrid(String title, List<Attribute> attributes, AnchorPane anchorPane)
	{
		this.anchorPane = anchorPane;
		this.title.setText(title);
		titleIcon.setImage(new Image(getClass().getResource("/assets/" + title.toLowerCase() + "Icon.png").toExternalForm()));
		
		TableColumn<String, String> firstCol = new TableColumn<>("#");
		firstCol.setCellValueFactory(new PropertyValueFactory<>("Number"));
        firstCol.getStyleClass().add("startCorner");
        dataGridTable.getColumns().add(firstCol);
		
		if(attributes != null)
		{
			this.attributes = attributes;
			for (Attribute attribute : attributes) 
			{
	            if(!attribute.isHide())
	            {
	            	TableColumn<String, String> column = new TableColumn<>(attribute.getAttribute());
	            	column.setCellValueFactory(new PropertyValueFactory<>(attribute.getDbAttribute()));
		            column.getStyleClass().add("columnHeader");
		            dataGridTable.getColumns().add(column);
		            
		            if(attribute.isSearch())
		            {
		            	category.getItems().add(attribute.getAttribute());
		            }
		            
	            }
	        }
		}
		
		TableColumn<String, String> endCol = new TableColumn<>("Operations");
		endCol.setCellValueFactory(new PropertyValueFactory<>("Operations"));
        endCol.getStyleClass().add("endCorner");
        dataGridTable.getColumns().add(endCol);
		
		category.setValue(category.getItems().get(0));
		
		SetUpButtons();
		SetUpTable();
		dataGridTable.setSelectionModel(null);
	}
	
	public void SetUpButtons()
	{
		JFXButton addBtn = new JFXButton();
		addBtn.getStyleClass().add("blueButton");
		addBtn.getStyleClass().add("smallButton");
		
		if(title.getText() == "Purchases")
		{
			addBtn.setText("New Purchase");
			addBtn.setOnAction(event -> OpenCart("Purchases"));
		}
		else if(title.getText() == "Billing")
		{
			addBtn.setText("New Bill");
			addBtn.setOnAction(event -> OpenCart("Billing"));
		}
		else
		{
			if(title.getText() == "Accounts")
			{
				addBtn.setText("New Account");
				JFXButton backupBtn = new JFXButton();
				backupBtn.setText("Backup Data");
				backupBtn.getStyleClass().add("blueButton");
				backupBtn.getStyleClass().add("smallButton");
				buttonsFlowPane.getChildren().add(backupBtn);
			}
			else
			{
				addBtn.setText("Add");
			}
			addBtn.setOnAction(event -> OpenInputForm());
		}
		buttonsFlowPane.getChildren().add(addBtn);
	}
	
	@SuppressWarnings("unchecked")
	public void SetUpTable()
	{	
		if(title.getText() == "Billing")
		{
			Bills.setDataGridTable(dataGridTable, title.getText(), attributes, anchorPane);
			BillsRepo billsRepo = new BillsRepo();
	        billsList = FXCollections.observableArrayList(billsRepo.getAllBills());
	        dataGridTable.setItems(billsList);
		}
		else if(title.getText() == "Purchases")
		{
			Purchases.setDataGridTable(dataGridTable, title.getText(), attributes, anchorPane);
			PurchasesRepo purchasesRepo = new PurchasesRepo();
	        purchasesList = FXCollections.observableArrayList(purchasesRepo.getAllPurchases());
	        dataGridTable.setItems(purchasesList);
		}
		else if(title.getText() == "Companies")
		{
			Companies.setDataGridTable(dataGridTable, title.getText(), attributes, anchorPane);
			CompaniesRepo companiesRepo = new CompaniesRepo();
	        companiesList = FXCollections.observableArrayList(companiesRepo.getAllCompanies());
	        dataGridTable.setItems(companiesList);
		}
		else if(title.getText() == "Suppliers")
		{
			Suppliers.setDataGridTable(dataGridTable, title.getText(), attributes, anchorPane);
			SuppliersRepo suppliersRepo = new SuppliersRepo();
	        suppliersList = FXCollections.observableArrayList(suppliersRepo.getAllSuppliers());
	        dataGridTable.setItems(suppliersList);
		}
		else if(title.getText() == "Products")
		{
			Products.setDataGridTable(dataGridTable, title.getText(), attributes, anchorPane);
			ProductsRepo productsRepo = new ProductsRepo();
			productsList = FXCollections.observableArrayList(productsRepo.getAllProducts());
	        dataGridTable.setItems(productsList);
		}
		else if(title.getText() == "Stock")
		{
			Stock.setDataGridTable(dataGridTable, title.getText(), attributes, anchorPane);
			StockRepo stockRepo = new StockRepo();
	        stockList = FXCollections.observableArrayList(stockRepo.getAllStock());
	        dataGridTable.setItems(stockList);
		}
		else if(title.getText() == "Expenses")
		{
			Expenses.setDataGridTable(dataGridTable, title.getText(), attributes, anchorPane);
			ExpensesRepo expensesRepo = new ExpensesRepo();
	        expensesList = FXCollections.observableArrayList(expensesRepo.getAllExpenses());
	        dataGridTable.setItems(expensesList);
		}
		else if(title.getText() == "Accounts")
		{
			Accounts.setDataGridTable(dataGridTable, title.getText(), attributes, anchorPane);
			AccountsRepo accountsRepo = new AccountsRepo();
	        accountsList = FXCollections.observableArrayList(accountsRepo.getAllAccounts());
	        dataGridTable.setItems(accountsList);
		}
	}
	
	public void OpenInputForm()
	{
		try {
			anchorPane.getChildren().clear();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/components/inputform/InputForm.fxml"));
			AnchorPane nextAnchorPane;
			nextAnchorPane = (AnchorPane) loader.load();
			anchorPane.getChildren().add(nextAnchorPane);
			AnchorPane.setLeftAnchor(nextAnchorPane, 0.0);
		    nextAnchorPane.toFront();
			
			InputFormController ifController;
			ifController = loader.getController();
			ifController.SetupInputForm(title.getText(), attributes, anchorPane, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void OpenCart(String file)
	{
		try 
		{
			anchorPane.getChildren().clear();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/screens/" + file.toLowerCase() + "/" + file + ".fxml"));
			AnchorPane nextAnchorPane;
			nextAnchorPane = (AnchorPane) loader.load();
			anchorPane.getChildren().add(nextAnchorPane);
			AnchorPane.setLeftAnchor(nextAnchorPane, 0.0);
		    nextAnchorPane.toFront();
		    
		    if(title.getText() == "Billing")
		    {
		    	BillingController bController;
				bController = loader.getController();
				bController.SetRoute(anchorPane, attributes);
		    }
		    else if(title.getText() == "Purchases")
		    {
		    	PurchasesController pController;
				pController = loader.getController();
				pController.SetRoute(anchorPane, attributes);
		    }
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void categoryChange()
	{
		if(category.getValue().equals("Date"))
		{
			datePicker.setStyle("visibility: visible;");
		}
		else
		{
			datePicker.setStyle("visibility: hidden");
			if(title.getText() == "Billing")
			{
		        dataGridTable.setItems(billsList);
			}
			else if(title.getText() == "Purchases")
			{
		        dataGridTable.setItems(purchasesList);
			}
			else if(title.getText() == "Companies")
			{
		        dataGridTable.setItems(companiesList);
			}
			else if(title.getText() == "Suppliers")
			{
		        dataGridTable.setItems(suppliersList);
			}
			else if(title.getText() == "Products")
			{
		        dataGridTable.setItems(productsList);
			}
			else if(title.getText() == "Stock")
			{
		        dataGridTable.setItems(stockList);
			}
			else if(title.getText() == "Expenses")
			{
		        dataGridTable.setItems(expensesList);
			}
			else if(title.getText() == "Accounts")
			{
		        dataGridTable.setItems(accountsList);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public void fetchBillsByInvoiceNumber(String invoiceNumber)
	{
		ArrayList<Bills> list = new ArrayList<>();
		list = billsRepo.fetchBillByInvoiceNumber(Integer.parseInt(searchBar.getText()));
		ObservableList<Bills> billsList = FXCollections.observableArrayList(list);
		dataGridTable.setItems(billsList);
	}
	
	@SuppressWarnings("unchecked")
	public void fetchPurchasesByInvoiceNumber(String invoiceNumber)
	{
		ArrayList<Purchases> list = new ArrayList<>();
		list = purchasesRepo.fetchPurchasesByInvoiceNumber(invoiceNumber);
		ObservableList<Purchases> purchasesList = FXCollections.observableArrayList(list);
		dataGridTable.setItems(purchasesList);
	}
	
	@SuppressWarnings("unchecked")
	public void fetchCompaniesByName(String companyName)
	{
		ArrayList<Companies> list = new ArrayList<>();
		list = companiesRepo.fetchByCompanyName(companyName);
		ObservableList<Companies> companiesList = FXCollections.observableArrayList(list);
		dataGridTable.setItems(companiesList);
	}
	
	@SuppressWarnings("unchecked")
	public void fetchSuppliersByName(String supplierName)
	{
		ArrayList<Suppliers> list = new ArrayList<>();
		list = suppliersRepo.fetchBySupplierName(supplierName);
		ObservableList<Suppliers> suppliersList = FXCollections.observableArrayList(list);
		dataGridTable.setItems(suppliersList);
	}

	@SuppressWarnings("unchecked")
	public void fetchProductsByName(String productName)
	{
		ArrayList<Products> list = new ArrayList<>();
		list = productsRepo.fetchByProductName(productName);
		ObservableList<Products> productsList = FXCollections.observableArrayList(list);
		dataGridTable.setItems(productsList);
	}
	
	@SuppressWarnings("unchecked")
	public void fetchExpenseByName(String expenseName)
	{
		ArrayList<Expenses> list = new ArrayList<>();
		list = expensesRepo.fetchByExpensesName(expenseName);
		ObservableList<Expenses> expensesList = FXCollections.observableArrayList(list);
		dataGridTable.setItems(expensesList);
	}
	
	@SuppressWarnings("unchecked")
	public void fetchStockByProductName(String expenseName)
	{
		ArrayList<Stock> list = new ArrayList<>();
		list = stockRepo.fetchStockByProductName(expenseName);
		ObservableList<Stock> stockList = FXCollections.observableArrayList(list);
		dataGridTable.setItems(stockList);
	}
	
	@SuppressWarnings({ "unchecked" })
	public void fetchDataFromDatabase()
	{
		if(title.getText().equals("Billing"))
		{
			if(searchBar.getText().length() > 0)
			{
				if(category.getValue().contains("Invoice No."))
				{
					fetchBillsByInvoiceNumber(searchBar.getText());
				}
			}
			else
			{
				dataGridTable.setItems(billsList);
			}
		}
		else if(title.getText().equals("Purchases"))
		{
			if(searchBar.getText().length() > 0)
			{
				if(category.getValue().contains("Invoice No."))
				{
					fetchPurchasesByInvoiceNumber(searchBar.getText());
				}
			}
			else
			{
				dataGridTable.setItems(purchasesList);
			}
		}
		else if(title.getText().equals("Companies"))
		{
			if(searchBar.getText().length() > 0)
			{
				if(category.getValue().contains("Name"))
				{
					fetchCompaniesByName(searchBar.getText());
				}
			}
			else
			{
				dataGridTable.setItems(companiesList);
			}
		}
		else if(title.getText().equals("Suppliers"))
		{
			if(searchBar.getText().length() > 0)
			{
				if(category.getValue().contains("Name"))
				{
					fetchSuppliersByName(searchBar.getText());
				}
			}
			else
			{
				dataGridTable.setItems(suppliersList);
			}
		}
		else if(title.getText().equals("Products"))
		{
			if(searchBar.getText().length() > 0)
			{
				if(category.getValue().contains("Name"))
				{
					fetchProductsByName(searchBar.getText());
				}
			}
			else
			{
				dataGridTable.setItems(productsList);
			}
		}
		else if(title.getText().equals("Expenses"))
		{
			if(searchBar.getText().length() > 0)
			{
				if(category.getValue().contains("Name"))
				{
					fetchExpenseByName(searchBar.getText());
				}
			}
			else
			{
				dataGridTable.setItems(expensesList);
			}
		}
		else if(title.getText().equals("Stock"))
		{
			if(searchBar.getText().length() > 0)
			{
				if(category.getValue().contains("Product Name"))
				{
					fetchStockByProductName(searchBar.getText());
				}
			}
			else
			{
				dataGridTable.setItems(stockList);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public void fetchDataByDate()
	{
		if(title.getText().equals("Billing"))
		{
			ArrayList<Bills> list = new ArrayList<>();
			list = billsRepo.fetchBillByDate(datePicker.getValue());
			ObservableList<Bills> billsList = FXCollections.observableArrayList(list);
			dataGridTable.setItems(billsList);
		}
		else if(title.getText().equals("Purchases"))
		{
			ArrayList<Purchases> list = new ArrayList<>();
			list = purchasesRepo.fetchPurchaseByDate(datePicker.getValue());
			ObservableList<Purchases> purchasesList = FXCollections.observableArrayList(list);
			dataGridTable.setItems(purchasesList);
		}
		else if(title.getText().equals("Expenses"))
		{
			ArrayList<Expenses> list = new ArrayList<>();
			list = expensesRepo.fetchExpensesByDate(datePicker.getValue());
			ObservableList<Expenses> expensesList = FXCollections.observableArrayList(list);
			dataGridTable.setItems(expensesList);
		}
	}
}