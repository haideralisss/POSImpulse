package application.models.entities;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class Accounts {
	
	private String userName, fullName, phone, password;
	private boolean isAdmin;
	
	private int number;
	private HBox operations;
	
	public Accounts()
	{
		number = 0;
		userName = fullName = phone = password = "";
		isAdmin = false;
	}
	
	public Accounts(int number, String userName, String fullName, String phone, String password, boolean isAdmin)
	{
		this.number = number;
		this.userName = userName;
		this.fullName = fullName;
		this.phone = phone;
		this.password = password;
		this.isAdmin = isAdmin;
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