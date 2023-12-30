package application.screens.billing;

import com.jfoenix.controls.JFXTextField;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BillCartItem {
	
	@SuppressWarnings("exports")
	public VBox nameStockBox;
	@SuppressWarnings("exports")
	public JFXTextField price, qty, disc;
	@SuppressWarnings("exports")
	public Label netTotal;
	@SuppressWarnings("exports")
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
	
	@SuppressWarnings("exports")
	public VBox getNameStockBox()
	{
		return nameStockBox;
	}
	
	@SuppressWarnings("exports")
	public JFXTextField getPrice()
	{
		return price;
	}
	
	@SuppressWarnings("exports")
	public JFXTextField getQty()
	{
		return qty;
	}
	
	@SuppressWarnings("exports")
	public JFXTextField getDisc()
	{
		return disc;
	}
	
	@SuppressWarnings("exports")
	public Label getNetTotal()
	{
		return netTotal;
	}
	
	@SuppressWarnings("exports")
	public ImageView getDelButton()
	{
		return delButton;
	}
}