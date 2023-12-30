package application.models.entities;

import java.util.Optional;

import application.models.repositories.AccountsRepo;
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

public class Accounts {
	
	private int id;
	private String userName, fullName, phone, password;
	private boolean isAdmin;
	
	private int number;
	private HBox operations;
	
	private static TableView dataGridTable;
	
	public Accounts()
	{
		id = number = 0;
		userName = fullName = phone = password = "";
		isAdmin = false;
	}
	
	public Accounts(int id, int number, String userName, String fullName, String phone, String password, boolean isAdmin)
	{
		this.id = id;
		this.number = number;
		this.userName = userName;
		this.fullName = fullName;
		this.phone = phone;
		this.password = password;
		this.isAdmin = isAdmin;
		
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
		    alert.setHeaderText("Delete Account");
		    alert.setContentText("Are you sure you want to delete this account?");

		    ButtonType confirmButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
		    ButtonType cancelButton = new ButtonType("No", ButtonBar.ButtonData.NO);
		    alert.getButtonTypes().setAll(confirmButton, cancelButton);

		    Optional<ButtonType> result = alert.showAndWait();
		    if (result.isPresent() && result.get() == confirmButton) {
		        AccountsRepo accountsRepo = new AccountsRepo();
		        accountsRepo.deleteAccount(this.id);
		        dataGridTable.setItems(FXCollections.observableArrayList(accountsRepo.deleteAccount(this.id)));
		    }
		});
		
		operations.setMaxWidth(Double.MAX_VALUE);
		operations.setAlignment(Pos.CENTER);
	}
	
	public static void setDataGridTable(TableView table) {
        dataGridTable = table;
    }
	
	public String getUserName()
	{
		return userName;
	}
	
	public String getFullName()
	{
		return fullName;
	}
	
	public String getPhone()
	{
		return phone;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public boolean getIsAdmin()
	{
		return isAdmin;
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