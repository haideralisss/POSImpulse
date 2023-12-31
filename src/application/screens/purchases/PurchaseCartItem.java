package application.screens.purchases;

import com.jfoenix.controls.JFXTextField;

import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PurchaseCartItem {
	
	@SuppressWarnings("exports")
	public VBox nameStockBox;
	@SuppressWarnings("exports")
	public JFXTextField price, qty, disc, salesTax, bonus, batchNum, purchasePrice, retailPrice;
	@SuppressWarnings("exports")
	public Label netTotal;
	@SuppressWarnings("exports")
	public ImageView delButton;
	@SuppressWarnings("exports")
	public DatePicker expiryDate;
	
	public PurchaseCartItem(String name, String stock)
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
		salesTax = new JFXTextField();
		bonus = new JFXTextField();
		batchNum = new JFXTextField();
		purchasePrice = new JFXTextField();
		retailPrice = new JFXTextField();
		expiryDate = new DatePicker();
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
		salesTax.getStyleClass().add("cartRowInput");
		salesTax.getStyleClass().add("salesTaxInput");
		bonus.getStyleClass().add("cartRowInput");
		bonus.getStyleClass().add("bonusInput");
		batchNum.getStyleClass().add("cartRowInput");
		batchNum.getStyleClass().add("batchNumInput");
		expiryDate.getStyleClass().add("expiryDateInput");
		netTotal.getStyleClass().add("netTotalLabel");
		delButton.getStyleClass().add("delButtonIcon");
		retailPrice.getStyleClass().add("cartRowInput");
		retailPrice.getStyleClass().add("priceFields");
		retailPrice.getStyleClass().add("retailPriceInput");
		purchasePrice.getStyleClass().add("cartRowInput");
		purchasePrice.getStyleClass().add("priceFields");
		purchasePrice.getStyleClass().add("purchasePriceInput");
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
	public JFXTextField getSalesTax()
	{
		return salesTax;
	}
	
	@SuppressWarnings("exports")
	public JFXTextField getBonus()
	{
		return bonus;
	}
	
	@SuppressWarnings("exports")
	public JFXTextField getBatchNum()
	{
		return batchNum;
	}
	
	@SuppressWarnings("exports")
	public JFXTextField getPurchasePrice()
	{
		return purchasePrice;
	}
	
	@SuppressWarnings("exports")
	public DatePicker getExpiryDate()
	{
		return expiryDate;
	}
	
	@SuppressWarnings("exports")
	public JFXTextField getRetailPrice()
	{
		return retailPrice;
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