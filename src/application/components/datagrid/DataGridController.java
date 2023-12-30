package application.components.datagrid;
import java.io.IOException;
import java.util.List;

import com.jfoenix.controls.JFXButton;

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
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

public class DataGridController 
{
	@FXML
	public Label title;
	
	@FXML
	public FlowPane buttonsFlowPane;
	
	@FXML
	public TableView dataGridTable;
	
	@FXML 
	public ComboBox<String> category;
	
	@FXML
	public ImageView titleIcon;
	
	List<Attribute> attributes;
	public AnchorPane anchorPane;
	
	public void SetupDataGrid(String title, List<Attribute> attributes, AnchorPane anchorPane)
	{
		this.anchorPane = anchorPane;
		this.title.setText(title);
		titleIcon.setImage(new Image(getClass().getResource("/assets/" + title.toLowerCase() + "Icon.png").toExternalForm()));
		
		SetUpButtons();
		SetUpTable();
		dataGridTable.setSelectionModel(null);
		
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
	
	public void SetUpTable()
	{	
		if(title.getText() == "Billing")
		{
			BillsRepo billsRepo = new BillsRepo();
	        ObservableList<Bills> billsList = FXCollections.observableArrayList(billsRepo.getAllBills());
	        dataGridTable.setItems(billsList);
		}
		else if(title.getText() == "Purchases")
		{
			PurchasesRepo purchasesRepo = new PurchasesRepo();
	        ObservableList<Purchases> purchasesList = FXCollections.observableArrayList(purchasesRepo.getAllPurchases());
	        dataGridTable.setItems(purchasesList);
		}
		else if(title.getText() == "Companies")
		{
			CompaniesRepo companiesRepo = new CompaniesRepo();
	        ObservableList<Companies> companiesList = FXCollections.observableArrayList(companiesRepo.getAllCompanies());
	        dataGridTable.setItems(companiesList);
		}
		else if(title.getText() == "Suppliers")
		{
			SuppliersRepo suppliersRepo = new SuppliersRepo();
	        ObservableList<Suppliers> suppliersList = FXCollections.observableArrayList(suppliersRepo.getAllSuppliers());
	        dataGridTable.setItems(suppliersList);
		}
		else if(title.getText() == "Products")
		{
			ProductsRepo productsRepo = new ProductsRepo();
	        ObservableList<Products> productsList = FXCollections.observableArrayList(productsRepo.getAllProducts());
	        dataGridTable.setItems(productsList);
		}
		else if(title.getText() == "Stock")
		{
			StockRepo stockRepo = new StockRepo();
	        ObservableList<Stock> stockList = FXCollections.observableArrayList(stockRepo.getAllStock());
	        dataGridTable.setItems(stockList);
		}
		else if(title.getText() == "Expenses")
		{
			ExpensesRepo expensesRepo = new ExpensesRepo();
	        ObservableList<Expenses> expensesList = FXCollections.observableArrayList(expensesRepo.getAllExpenses());
	        dataGridTable.setItems(expensesList);
		}
		else if(title.getText() == "Accounts")
		{
			AccountsRepo accountsRepo = new AccountsRepo();
	        ObservableList<Accounts> accountsList = FXCollections.observableArrayList(accountsRepo.getAllAccounts());
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
			ifController.SetupInputForm(title.getText(), attributes, anchorPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void OpenCart(String file)
	{
		try {
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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}