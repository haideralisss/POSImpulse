package application.screens.billing;

import com.jfoenix.controls.JFXTextField;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class BillCartItem {
	
	public VBox nameStockBox;
	public JFXTextField price, qty, disc;
	public Label netTotal;
	public ImageView delButton;
	
	public BillCartItem(String name, String stock)
	{
		nameStockBox = new VBox();
		Label productNameLabel = new Label(name);
		nameStockBox.getChildren().add(productNameLabel);
		Label stockHeading = new Label("Stock: ");
		stockHeading.setStyle("-fx-font-weight: bold");
		Label stockLabel = new Label(stock);
		HBox stockHBox = new HBox();
		stockHBox.getChildren().add(stockHeading);
		stockHBox.getChildren().add(stockLabel);
		nameStockBox.getChildren().add(stockHBox);
		
		price = new JFXTextField();
		qty = new JFXTextField();
		disc = new JFXTextField();
		netTotal = new Label("Rs. 0");
		
		delButton = new ImageView();
		Image delIcon = new Image("file:///C:/Users/AbdulWali/eclipse-workspace/POSImpulse/src/assets/deleteIcon.png");
		delButton.setImage(delIcon);
		delButton.setFitWidth(15);
		delButton.setFitHeight(15);
		
		nameStockBox.getStyleClass().add("nameStockBox");
		price.getStyleClass().add("cartRowInput");
		qty.getStyleClass().add("cartRowInput");
		qty.getStyleClass().add("qtyInput");
		disc.getStyleClass().add("cartRowInput");
		disc.getStyleClass().add("discInput");
		netTotal.getStyleClass().add("netTotalLabel");
		delButton.getStyleClass().add("delButtonIcon");
	}
	
	public VBox getNameStockBox()
	{
		return nameStockBox;
	}
	
	public JFXTextField getPrice()
	{
		return price;
	}
	
	public JFXTextField getQty()
	{
		return qty;
	}
	
	public JFXTextField getDisc()
	{
		return disc;
	}
	
	public Label getNetTotal()
	{
		return netTotal;
	}
	
	public ImageView getDelButton()
	{
		return delButton;
	}
}