package application.models.entities;

import java.time.LocalDate;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class Expenses {
	
	String name, description;
	String expenseDate;
	double amount;
	
	private int number;
	private HBox operations;

	public Expenses(int number, String expenseDate, String name, String description, double amount)
	{
		this.number = number;
		this.expenseDate = expenseDate;
		this.name = name;
		this.description = description;
		this.amount = amount;
		
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
		operations.getChildren().add(delButton);
		
		operations.setMaxWidth(Double.MAX_VALUE);
		operations.setAlignment(Pos.CENTER);
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
