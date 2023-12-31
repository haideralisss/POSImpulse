package application.models.entities;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import application.components.datagrid.Attribute;
import application.components.inputform.InputFormController;
import application.models.repositories.ExpensesRepo;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class Expenses {
	
	private int id;
	private String name, description;
	private String expenseDate;
	double amount;
	
	private int number;
	private HBox operations;
	
	private static TableView<Expenses> dataGridTable;
	private static String title;
	private static List<Attribute> attributes;
	private static AnchorPane anchorPane;
	
	public Expenses()
	{
		id = number = 0;
		name = description = expenseDate = "";
		amount = 0;
	}
	
	public Expenses(int id, int number, String expenseDate, String name, String description, double amount)
	{
		this.id = id;
		this.number = number;
		this.expenseDate = expenseDate;
		this.name = name;
		this.description = description;
		this.amount = amount;
		
		HBox delHBox = new HBox();
		HBox editHBox = new HBox();
		ImageView delButton = new ImageView();
		Image delIcon = new Image("file:///C:/Users/AbdulWali/eclipse-workspace/POSImpulse/src/assets/deleteIcon.png");
		delButton.setImage(delIcon);
		delButton.setFitWidth(15);
		delButton.setFitHeight(15);
		ImageView editButton = new ImageView();
		Image editIcon = new Image("file:///C:/Users/AbdulWali/eclipse-workspace/POSImpulse/src/assets/editIcon.png");
		editButton.setImage(editIcon);
		editButton.setFitWidth(15);
		editButton.setFitHeight(15);
		operations = new HBox();
		operations.getChildren().add(editHBox);
		operations.getChildren().add(delHBox);
		
		editHBox.setMaxWidth(Double.MAX_VALUE);
		editHBox.setAlignment(Pos.CENTER);
		editHBox.getChildren().add(editButton);
		editHBox.setCursor(Cursor.HAND);
		editHBox.setOnMouseClicked(event -> {
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
				ifController.SetupInputForm(title, attributes, anchorPane, this, null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		
		delHBox.getChildren().add(delButton);
		delHBox.setMaxWidth(Double.MAX_VALUE);
		delHBox.setAlignment(Pos.CENTER);
		delHBox.setCursor(Cursor.HAND);
		delHBox.setOnMouseClicked(event -> {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		    alert.setTitle("Confirmation Dialog");
		    alert.setHeaderText("Delete Expense");
		    alert.setContentText("Are you sure you want to delete this expense?");

		    ButtonType confirmButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
		    ButtonType cancelButton = new ButtonType("No", ButtonBar.ButtonData.NO);
		    alert.getButtonTypes().setAll(confirmButton, cancelButton);

		    Optional<ButtonType> result = alert.showAndWait();
		    if (result.isPresent() && result.get() == confirmButton) {
		        ExpensesRepo expensesRepo = new ExpensesRepo();
		        dataGridTable.setItems(FXCollections.observableArrayList(expensesRepo.deleteExpense(this.id)));
		    }
		});
		
		operations.setMaxWidth(Double.MAX_VALUE);
		operations.setAlignment(Pos.CENTER);
	}
	
	@SuppressWarnings("exports")
	public static void setDataGridTable(TableView<Expenses> table, String Title, List<Attribute> Attributes, AnchorPane AnchorPANE) {
        dataGridTable = table;
        title = Title;
        attributes = Attributes;
        anchorPane = AnchorPANE;
    }
	
	public String getExpenseDate() {
        return expenseDate;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }
    
    public int getId()
	{
		return id;
	}
	
	public int getNumber()
	{
		return number;
	}
	
	@SuppressWarnings("exports")
	public HBox getOperations()
	{
		return operations;
	}
}
