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
import application.models.entities.Bills;
import application.models.entities.Companies;
import application.models.entities.Products;
import application.models.repositories.AccountsRepo;
import application.models.repositories.BillsRepo;
import application.models.repositories.CompaniesRepo;
import application.models.repositories.ProductsRepo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
	
	@FXML
	public Label title;
	
	@SuppressWarnings("exports")
	@FXML
	public FlowPane inputFlowPane;
	
	@FXML
	public ImageView titleIcon;
	
	@FXML
	public VBox anchorVBox, flowVBox;
	
	@FXML
	public AnchorPane inputAnchorPane, anchorPane;
	
	@FXML
	public Label heading;
	
	List<Attribute> attributes;
	private Map<String, Node> inputComponents = new HashMap<>();
	
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
				}
				else if(attribute.getType() == "checkbox")
				{
					FlowPane fPane = new FlowPane();
					Label label = new Label(attribute.getAttribute());
					label.getStyleClass().add("checkLabel");
					fPane.getChildren().add(label);
					fPane.getChildren().add(new JFXCheckBox());
					fPane.getStyleClass().add("fPaneSpacing");
					inputFlowPane.getChildren().add(fPane);
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
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}	
	}
	
	private Object createEntityInstance(Class<?> entityClass) {
        try {
            return entityClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
        return null;
    }

    private void setValueByReflection(Object object, String fieldName, String value) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);

            // Determine the field type and convert the value accordingly
            if (field.getType() == String.class) {
                field.set(object, value);
            } else if (field.getType() == Boolean.TYPE) {
                field.set(object, Boolean.parseBoolean(value));
            } // Add more cases for other data types as needed
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }
    
    private void setValueByReflection(Object object, String fieldName, Object value) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);

            // Determine the field type and convert the value accordingly
            if (field.getType() == String.class) {
                field.set(object, (String) value);
            } else if (field.getType() == Boolean.TYPE) {
                field.set(object, (Boolean) value);
            } else if (field.getType() == LocalDate.class) {
                // Assuming 'value' is a String representation of the date, you may need to parse it
                // Replace the following line with the correct parsing logic based on your date format
                LocalDate dateValue = LocalDate.parse((String) value);
                field.set(object, dateValue);
            } // Add more cases for other data types as needed
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

    
    private Object getComponentValue(Attribute attribute, Node inputComponent) {
        if (attribute.getType().equals("text")) {
            return ((JFXTextField) inputComponent).getText();
        } else if (attribute.getType().equals("checkbox")) {
            return ((JFXCheckBox) inputComponent).isSelected();
        } else if (attribute.getType().equals("password")) {
            return ((JFXPasswordField) inputComponent).getText();
        } else if (attribute.getType().equals("date")) {
            // You may need to handle date parsing based on your date picker component
            // For simplicity, assuming it returns a String here
            return ((DatePicker) inputComponent).getEditor().getText();
        }

        // Handle other types or return null for unsupported types
        return null;
    }
	
    public void SubmitEvent() 
    {
        Object entityInstance = null;

        if (title.getText().equals("Accounts")) {
            entityInstance = createEntityInstance(Accounts.class);
        }

        if (entityInstance != null) {
            for (Attribute attribute : attributes) {
                if (attribute.isInput()) {
                    Node inputComponent = inputComponents.get(attribute.getAttribute());
                    if (inputComponent != null) {
                        Object value = getComponentValue(attribute, inputComponent);
                        setValueByReflection(entityInstance, attribute.getAttribute(), value);
                    }
                }
            }
        }
        
        if(title.getText() == "Accounts")
        {
        	AccountsRepo accountsRepo = new AccountsRepo();
        	accountsRepo.addAccount((Accounts) entityInstance);
        }
    }

}