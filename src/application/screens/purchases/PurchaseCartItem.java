package application.screens.purchases;

import java.util.ArrayList;
import java.util.List;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
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
	public JFXCheckBox isReturn;
	@SuppressWarnings("exports")
	public HBox delHBox;
	
	double purchasePriceVal, retailPriceVal;
    int stockId, packSize, quantity, totalQuantity, newQuantity, bonusVal;
    private String batchNumVal;
	
	private double originalValueOfGrossTotal, originalValueOfNetTotal;
	
	@SuppressWarnings("exports")
	public PurchaseCartItem(String name, String stock, Label grossTotalLabel, Label netTotalLabel, ArrayList<PurchaseCartItem> list,
			int stockId, double unitCost, int totalQuantity, int packSize, double purchasePriceValue, double retailPriceValue, 
			JFXCheckBox isReturn, JFXCheckBox isLoose)
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
		netTotal = new Label("Rs. 0");

		price.setText(String.format("%.2f", purchasePriceValue / (isLoose.isSelected() ? packSize : 1)));
		
        this.isReturn = isReturn;
        
        price.setOnKeyReleased(event -> recalculateTotals(grossTotalLabel, netTotalLabel, list, isLoose.isSelected(), packSize));
        qty.setOnKeyReleased(event -> recalculateTotals(grossTotalLabel, netTotalLabel, list, isLoose.isSelected(), packSize));
        disc.setOnKeyReleased(event -> recalculateTotals(grossTotalLabel, netTotalLabel, list, isLoose.isSelected(), packSize));
        salesTax.setOnKeyReleased(event -> recalculateTotals(grossTotalLabel, netTotalLabel, list, isLoose.isSelected(), packSize));
        bonus.setOnKeyReleased(event -> recalculateTotals(grossTotalLabel, netTotalLabel, list, isLoose.isSelected(),  packSize));
        batchNum.setOnKeyReleased(event -> recalculateTotals(grossTotalLabel, netTotalLabel, list, isLoose.isSelected(), packSize));
		
		delButton = new ImageView();
		Image delIcon = new Image("file:///C:/Users/AbdulWali/eclipse-workspace/POSImpulse/src/assets/deleteIcon.png");
		delButton.setImage(delIcon);
		delButton.setFitWidth(15);
		delButton.setFitHeight(15);
		delHBox = new HBox();
		delHBox.getChildren().add(delButton);
		delHBox.setMaxWidth(15);
		delHBox.setAlignment(Pos.CENTER);
		delHBox.setCursor(Cursor.HAND);
		
		nameStockBox.getStyleClass().add("nameStockBox");
		price.getStyleClass().add("cartRowInput");
		price.getStyleClass().add("priceInput");
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
		netTotal.getStyleClass().add("netTotalLabel");
		delHBox.getStyleClass().add("delButtonIcon");
		retailPrice.getStyleClass().add("cartRowInput");
		retailPrice.getStyleClass().add("priceFields");
		retailPrice.getStyleClass().add("retailPriceInput");
		purchasePrice.getStyleClass().add("cartRowInput");
		purchasePrice.getStyleClass().add("priceFields");
		purchasePrice.getStyleClass().add("purchasePriceInput");
	}
	
	private void recalculateTotals(Label grossTotalLabel, Label netTotalLabel, List<PurchaseCartItem> list, boolean isLoose, int packSize) 
    {
        double priceValue = price.getText().isEmpty() ? 0 : Double.parseDouble(price.getText());
        int qtyValue = qty.getText().isEmpty() ? 0 : (Integer.parseInt(qty.getText()));

        double totalValue = priceValue * qtyValue;
        batchNumVal = batchNum.getText();
        bonusVal = bonus.getText().isEmpty() ? 0 : (Integer.parseInt(bonus.getText()));
        
        quantity = (qty.getText().isEmpty() ? 0 : (Integer.parseInt(qty.getText()))	+ (bonus.getText().isEmpty() ? 0 : (Integer.parseInt(bonus.getText()))));
        
        if(isReturn.isSelected())
        {
        	newQuantity = totalQuantity + quantity;
        }
        else
        {
        	newQuantity = totalQuantity - quantity;
        }

        if(totalValue >= 0)
        {
        	if (disc.getText().contains("%") && disc.getText().length() > 0) 
            {
                double discount = getNumberOnly(disc.getText()) / 100;
                totalValue -= totalValue * discount;
            }
            else if (!disc.getText().contains("%") && disc.getText().length() > 0) 
            {
                totalValue -= Double.parseDouble(disc.getText());
            }
        	
        	if (salesTax.getText().contains("%") && salesTax.getText().length() > 0) 
            {
                double salesTaxVal = getNumberOnly(salesTax.getText()) / 100;
                totalValue += totalValue * salesTaxVal;
            }
            else if (!salesTax.getText().contains("%") && salesTax.getText().length() > 0) 
            {
                totalValue += Double.parseDouble(salesTax.getText());
            }

            double gValue = totalValue - originalValueOfGrossTotal;
            double nValue = totalValue - originalValueOfNetTotal;

            for (PurchaseCartItem item : list) 
            {
                gValue += item.originalValueOfGrossTotal;
                nValue += item.originalValueOfNetTotal;
            }

            grossTotalLabel.setText("Rs. " + String.format("%.2f", gValue));
            netTotalLabel.setText("Rs. " + String.format("%.2f", nValue));
            netTotal.setText("Rs. " + String.format("%.2f", totalValue));

            originalValueOfGrossTotal = totalValue;
            originalValueOfNetTotal = totalValue;
        }
        else
        {
        	Alert alert = new Alert(AlertType.ERROR);
        	alert.setTitle("Error");
        	alert.setHeaderText("Error in calculating values!");
        	alert.setContentText("Total values being calculated are less than 0. We cannot edit the Gross Total and Net Total.");
        	alert.show();
        }
    }


    public double getNumberOnly(String str) 
    {
        str = str.replace("Rs.", "");
        String numericString = str.replaceAll("[^0-9.]", "");
        return Double.parseDouble(numericString);
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
	public HBox getDelButton()
	{
		return delHBox;
	}
}