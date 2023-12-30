package application.models.entities;

import java.util.Optional;

import application.models.repositories.ExpensesRepo;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class Expenses {
	
	private int id;
	private String name, description;
	private String expenseDate;
	double amount;
	
	private int number;
	private HBox operations;
	
	private static TableView<Expenses> dataGridTable;

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
		operations.getChildren().add(editButton);
		operations.getChildren().add(delHBox);
		
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
		        expensesRepo.deleteExpense(this.id);
		        dataGridTable.setItems(FXCollections.observableArrayList(expensesRepo.deleteExpense(this.id)));
		    }
		});
		
		operations.setMaxWidth(Double.MAX_VALUE);
		operations.setAlignment(Pos.CENTER);
	}
	
	public static void setDataGridTable(TableView<Expenses> table) {
        dataGridTable = table;
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
	
	public int getNumber()
	{
		return number;
	}
	
	public HBox getOperations()
	{
		return operations;
	}
}
