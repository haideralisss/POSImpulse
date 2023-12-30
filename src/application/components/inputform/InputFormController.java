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
import application.models.entities.Expenses;
import application.models.entities.Products;
import application.models.entities.Stock;
import application.models.entities.Suppliers;
import application.models.repositories.AccountsRepo;
import application.models.repositories.CompaniesRepo;
import application.models.repositories.ExpensesRepo;
import application.models.repositories.ProductsRepo;
import application.models.repositories.StockRepo;
import application.models.repositories.SuppliersRepo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
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
	
	@SuppressWarnings("exports")
	public void SetupInputForm(String title, List<Attribute> attributes, AnchorPane anchorPane)
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
		} catch (IOException e) {
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

    @SuppressWarnings("unused")
	private void setValueByReflection(Object object, String fieldName, String value) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            if (field.getType() == String.class) {
                field.set(object, value);
            } else if (field.getType() == Boolean.TYPE) {
                field.set(object, Boolean.parseBoolean(value));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                LocalDate dateValue = LocalDate.parse((String) value);
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
            return ((DatePicker) inputComponent).getEditor().getText();
        }
        return null;
    }
	
    public void SubmitEvent() {
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
        	accountsRepo.addAccount((Accounts) entityInstance);
        }
        else if(title.getText().equals("Expenses"))
        {
        	ExpensesRepo expensesRepo = new ExpensesRepo();
        	expensesRepo.addExpense((Expenses) entityInstance);
        }
        else if(title.getText().equals("Products"))
        {
        	ProductsRepo productsRepo = new ProductsRepo();
        	productsRepo.addProduct((Products) entityInstance);
        }
        else if(title.getText().equals("Stock"))
        {
        	StockRepo stockRepo = new StockRepo();
        	stockRepo.addStock((Stock) entityInstance);
        }
        else if(title.getText().equals("Suppliers"))
        {
        	SuppliersRepo suppliersRepo = new SuppliersRepo();
        	suppliersRepo.addSupplier((Suppliers) entityInstance);
        }
        else if(title.getText().equals("Companies"))
        {
        	CompaniesRepo companiesRepo = new CompaniesRepo();
        	companiesRepo.addCompany((Companies) entityInstance);
        }

    	CancelEvent();
    }

}