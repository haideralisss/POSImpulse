package application.components.inputform;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import application.components.datagrid.Attribute;
import application.components.datagrid.DataGridController;
import application.models.entities.Accounts;
import application.models.entities.Companies;
import application.models.entities.Products;
import application.models.repositories.AccountsRepo;
import application.models.repositories.CompaniesRepo;
import application.models.repositories.ProductsRepo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import application.models.entities.Expenses;
import application.models.entities.Stock;
import application.models.entities.Suppliers;
import application.models.repositories.ExpensesRepo;
import application.models.repositories.StockRepo;
import application.models.repositories.SuppliersRepo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class InputFormController {
	
	@SuppressWarnings("exports")
	@FXML
	public Label title;
	
	@SuppressWarnings("exports")
	@FXML
	public FlowPane inputFlowPane;
	
	@SuppressWarnings("exports")
	@FXML
	public ImageView titleIcon;
	
	@SuppressWarnings("exports")
	@FXML
	public VBox anchorVBox, flowVBox;
	
	@SuppressWarnings("exports")
	@FXML
	public AnchorPane inputAnchorPane, anchorPane;
	
	@SuppressWarnings("exports")
	@FXML
	public Label heading;
	
	List<Attribute> attributes;
	private Map<String, Node> inputComponents = new HashMap<>();
	
	private Object updateObject;
	
	@SuppressWarnings("exports")
	public void SetupInputForm(String title, List<Attribute> attributes, AnchorPane anchorPane, Object obj)
	{
		this.title.setText(title);
		this.anchorPane = anchorPane;
		this.attributes = attributes;
		titleIcon.setImage(new Image(getClass().getResource("/assets/" + title.toLowerCase() + "Icon.png").toExternalForm()));
		titleIcon.setFitWidth(30);
		titleIcon.setFitHeight(30);
		
		for(Attribute attribute : attributes) {
			if(attribute.isInput())
			{
				if(attribute.getType() == "text")
				{
					VBox vBox = new VBox();
					Label label = new Label(attribute.getAttribute());
					label.getStyleClass().add("inputLabel");
					vBox.getChildren().add(label);
					JFXTextField textField = new JFXTextField();
					textField.getStyleClass().add("inputField");
					textField.setPromptText(attribute.getAttribute());
					vBox.getChildren().add(textField);
					if(attribute.getAttribute().equals("Company Name"))
					{
						ListView<Companies> listView = new ListView<>();
					    listView.setPrefSize(200, 150);
					    vBox.getChildren().add(listView);
					    listView.setStyle("visibility: hidden;");
						textField.setOnKeyReleased(event -> {
							if(textField.getText().length() > 0)
							{
								CompaniesRepo companiesRepo = new CompaniesRepo();
								ObservableList<Companies> items = null;
								for(Companies company : companiesRepo.fetchIdByCompanyName(textField.getText()))
								{
									items = FXCollections.observableArrayList(
							                company
							        );
								}
								if(items != null && items.size() > 0)
								{
									listView.setStyle("visibility: visible;");
									listView.setItems(items);
									listView.setCellFactory(param -> new ListCell<>() {
										@Override
							            protected void updateItem(Companies item, boolean empty) {
							                super.updateItem(item, empty);
							                if (empty || item == null) {
							                    setText(null);
							                } else {
							                    setText(item.getName());
							                }
							            }
									});
									
									listView.setOnMouseClicked(e -> {
							            Companies selectedItem = listView.getSelectionModel().getSelectedItem();
							            textField.setText(selectedItem.getName());
							            listView.setStyle("visibility: hidden;");
							        });
								}
								else
								{
								    listView.setStyle("visibility: hidden;");
								}
							}
							else
							{
							    listView.setStyle("visibility: hidden;");
							}
						});
						
					}
					else if(attribute.getAttribute().equals("Product Name"))
					{
						ListView<Products> listView = new ListView<>();
					    listView.setPrefSize(200, 150);
					    vBox.getChildren().add(listView);
					    listView.setStyle("visibility: hidden;");
						textField.setOnKeyReleased(event -> {
							if(textField.getText().length() > 0)
							{
								ProductsRepo productsRepo = new ProductsRepo();
								ObservableList<Products> items = null;
								for(Products product : productsRepo.fetchIdProductName(textField.getText()))
								{
									items = FXCollections.observableArrayList(
							                product
							        );
								}
								if(items != null && items.size() > 0)
								{
									listView.setStyle("visibility: visible;");
									listView.setItems(items);
									listView.setCellFactory(param -> new ListCell<>() {
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
									
									listView.setOnMouseClicked(e -> {
										Products selectedItem = listView.getSelectionModel().getSelectedItem();
							            textField.setText(selectedItem.getName());
							            listView.setStyle("visibility: hidden;");
							        });
								}
								else
								{
								    listView.setStyle("visibility: hidden;");
								}
							}
							else
							{
							    listView.setStyle("visibility: hidden;");
							}
						});
					}
					inputFlowPane.getChildren().add(vBox);	
					inputComponents.put(attribute.getDbAttribute(), textField);
				}
				else if(attribute.getType() == "checkbox")
				{
					FlowPane fPane = new FlowPane();
					Label label = new Label(attribute.getAttribute());
					label.getStyleClass().add("checkLabel");
					fPane.getChildren().add(label);
					JFXCheckBox checkbox = new JFXCheckBox();
					fPane.getChildren().add(checkbox);
					fPane.getStyleClass().add("fPaneSpacing");
					inputFlowPane.getChildren().add(fPane);
					inputComponents.put(attribute.getDbAttribute(), checkbox);
				}
				else if(attribute.getType() == "password")
				{
					VBox vBox = new VBox();
					Label label = new Label(attribute.getAttribute());
					label.getStyleClass().add("inputLabel");
					vBox.getChildren().add(label);
					JFXPasswordField passwordField = new JFXPasswordField();
					passwordField.getStyleClass().add("inputField");
					passwordField.setPromptText(attribute.getAttribute());
					vBox.getChildren().add(passwordField);
					inputFlowPane.getChildren().add(vBox);	
					inputComponents.put(attribute.getDbAttribute(), passwordField);
				}
				else if(attribute.getType() == "date")
				{
					VBox vBox = new VBox();
					Label label = new Label(attribute.getAttribute());
					label.getStyleClass().add("inputLabel");
					vBox.getChildren().add(label);
					DatePicker datePicker = new DatePicker();
					datePicker.getStyleClass().add("inputField");
					datePicker.setPromptText(attribute.getAttribute());
					vBox.getChildren().add(datePicker);
					inputFlowPane.getChildren().add(vBox);
					inputComponents.put(attribute.getDbAttribute(), datePicker);
				}
			}
		}
		
		if(obj != null)
		{
			this.heading.setText("Updation Form");
	        populateFieldsFromObject(obj);
	        this.updateObject = obj;
		}
	}
	
	public void CancelEvent()
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
			dgController.SetupDataGrid((title.getText() == "Admin Panel" ? "Accounts" : title.getText()), attributes, anchorPane);
		}
		catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	private Object createEntityInstance(Class<?> entityClass) {
        try {
            return entityClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    private void setValueByReflection(Object object, String fieldName, Object value) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
        	
            if (field.getType() == String.class) {
                field.set(object, (String) value);
            } else if (field.getType() == Boolean.TYPE) {
                field.set(object, (Boolean) value);
            } else if (field.getType() == LocalDate.class) {
                LocalDate dateValue = (LocalDate) value;
                field.set(object, dateValue);
            } else if (field.getType() == Double.TYPE) {
                field.set(object, (Double) value);
            } else if (field.getType() == Integer.TYPE) {
            	 field.set(object, Integer.parseInt((String) value));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    private Object getComponentValue(Attribute attribute, Node inputComponent) {
        if (attribute.getType().equals("text")) {
        	if(attribute.getDbType().equals("real"))
        	{
        		return Double.parseDouble(((JFXTextField) inputComponent).getText());
        	}
        	else
        		return ((JFXTextField) inputComponent).getText();
        } else if (attribute.getType().equals("checkbox")) {
            return ((JFXCheckBox) inputComponent).isSelected();
        } else if (attribute.getType().equals("password")) {
            return ((JFXPasswordField) inputComponent).getText();
        } else if (attribute.getType().equals("date")) {
            return ((DatePicker) inputComponent).getValue().toString();
        }
        return null;
    }
	
    private void populateFieldsFromObject(Object obj) {
        for (Attribute attribute : attributes) {
            if (attribute.isInput()) {
                Node inputComponent = inputComponents.get(attribute.getDbAttribute());
                if (inputComponent != null) {
                    try {
                        Field field = obj.getClass().getDeclaredField(attribute.getDbAttribute());
                        field.setAccessible(true);
                        Object value = field.get(obj);
                        setComponentValue(attribute, inputComponent, value);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    
    private void setComponentValue(Attribute attribute, Node inputComponent, Object value) {
        if (attribute.getType().equals("text")) {
            ((JFXTextField) inputComponent).setText(value != null ? value.toString() : "");
        } else if (attribute.getType().equals("checkbox")) {
            ((JFXCheckBox) inputComponent).setSelected((Boolean) value);
        } else if (attribute.getType().equals("password")) {
            ((JFXPasswordField) inputComponent).setText(value != null ? value.toString() : "");
        } else if (attribute.getType().equals("date")) {
            ((DatePicker) inputComponent).getEditor().setText(value != null ? value.toString() : "");
        }
    }
    public void SubmitEvent() 
    {
        Object entityInstance = null;

        if (title.getText().equals("Accounts")) {
            entityInstance = createEntityInstance(Accounts.class);
        }
        else if(title.getText().equals("Expenses"))
        {
        	entityInstance = createEntityInstance(Expenses.class);
        }
        else if(title.getText().equals("Products"))
        {
        	entityInstance = createEntityInstance(Products.class);
        }
        else if(title.getText().equals("Stock"))
        {
        	entityInstance = createEntityInstance(Stock.class);
        }
        else if(title.getText().equals("Suppliers"))
        {
        	entityInstance = createEntityInstance(Suppliers.class);
        }
        else if(title.getText().equals("Companies"))
        {
        	entityInstance = createEntityInstance(Companies.class);
        }

        for (Attribute attribute : attributes) {
        	if (attribute.isInput()) {
        		Node inputComponent = inputComponents.get(attribute.getDbAttribute());
        		if (inputComponent != null) {
        			Object value = getComponentValue(attribute, inputComponent);
        			setValueByReflection(entityInstance, attribute.getDbAttribute(), value);
        			
        			if (value == null || (value instanceof String && ((String) value).isEmpty())) {
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Validation Error");
                        alert.setHeaderText(null);
                        alert.setContentText("Please fill in all the fields.");
                        alert.showAndWait();
                        return;
                    }
                }
            }
        }
        
        if(title.getText().equals("Accounts"))
        {
        	AccountsRepo accountsRepo = new AccountsRepo();
        	if(updateObject == null)
        		accountsRepo.addAccount((Accounts) entityInstance);
        	else
        		accountsRepo.updateAccount(((Accounts) updateObject).getId(), (Accounts) entityInstance);
        }
        else if(title.getText().equals("Expenses"))
        {
        	ExpensesRepo expensesRepo = new ExpensesRepo();
        	if(updateObject == null)
        		expensesRepo.addExpense((Expenses) entityInstance);
        	else
        		expensesRepo.updateExpense(((Expenses) updateObject).getId(), (Expenses) entityInstance);
        }
        else if(title.getText().equals("Products"))
        {
        	ProductsRepo productsRepo = new ProductsRepo();
        	if(updateObject == null)
        		productsRepo.addProduct((Products) entityInstance);
        	else
        		productsRepo.updateProduct(((Products) updateObject).getId(), (Products) entityInstance);
        }
        else if(title.getText().equals("Stock"))
        {
        	StockRepo stockRepo = new StockRepo();
        	if(updateObject == null)
        		stockRepo.addStock((Stock) entityInstance);
        	else
        		stockRepo.updateStock(((Stock) updateObject).getId(), (Stock) entityInstance);
        }
        else if(title.getText().equals("Suppliers"))
        {
        	SuppliersRepo suppliersRepo = new SuppliersRepo();
        	if(updateObject == null)
        		suppliersRepo.addSupplier((Suppliers) entityInstance);
        	else
        		suppliersRepo.updateSupplier(((Suppliers) updateObject).getId(), (Suppliers) entityInstance);
        }
        else if(title.getText().equals("Companies"))
        {
        	CompaniesRepo companiesRepo = new CompaniesRepo();
        	if(updateObject == null)
        		companiesRepo.addCompany((Companies) entityInstance);
        	else
        		companiesRepo.updateCompany(((Companies) updateObject).getId(), (Companies) entityInstance);
        }

    	CancelEvent();
    }

}