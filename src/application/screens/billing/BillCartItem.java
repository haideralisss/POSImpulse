package application.screens.billing;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;

import application.models.entities.BillProducts;
import javafx.scene.control.Label;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

public class BillCartItem 
{

    @SuppressWarnings("exports")
	public VBox nameStockBox;
    
    @SuppressWarnings("exports")
	public JFXTextField price, qty, disc;
    
    @SuppressWarnings("exports")
	public Label netTotal;
    
    @SuppressWarnings("exports")
	public ImageView delButton;
    
    @SuppressWarnings("exports")
	public JFXCheckBox isReturn;
    
    double purchasePrice, retailPrice, priceValue;
    int productId, stockId, packSize, quantity, totalQuantity, newQuantity;

    private double originalValueOfGrossTotal, originalValueOfNetTotal;

    @SuppressWarnings("exports")
	public BillCartItem(String name, int productId, String stock, Label grossTotalLabel, Label netTotalLabel, 
			List<BillCartItem> list, int stockId, double unitCost, int totalQuantity, int packSize, double purchasePrice, 
			double retailPrice, JFXCheckBox isReturn) 
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
        
        price.setText(String.valueOf(unitCost));
        price.setDisable(true);
        
        this.isReturn = isReturn;
        this.productId = productId;
        this.priceValue = unitCost;

        price.setOnKeyReleased(event -> recalculateTotals(grossTotalLabel, netTotalLabel, list));
        qty.setOnKeyReleased(event -> recalculateTotals(grossTotalLabel, netTotalLabel, list));
        disc.setOnKeyReleased(event -> recalculateTotals(grossTotalLabel, netTotalLabel, list));

        delButton = new ImageView();
        Image delIcon = new Image("file:///C:/Users/ALI/eclipse-workspace/POSImpulse/src/assets/deleteIcon.png");
        delButton.setImage(delIcon);
        delButton.setFitWidth(15);
        delButton.setFitHeight(15);
        delButton.setCursor(Cursor.HAND);

        originalValueOfGrossTotal = 0;
        originalValueOfNetTotal = 0;
        
        this.stockId = stockId;
        this.totalQuantity = totalQuantity;
        this.purchasePrice = purchasePrice;
        this.retailPrice = retailPrice;
        this.packSize = packSize;

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
	public BillCartItem(BillProducts billProduct) 
    {
        nameStockBox = new VBox();
        Label productNameLabel = new Label(billProduct.getProductId());
        nameStockBox.getChildren().add(productNameLabel);

        price = new JFXTextField();
        qty = new JFXTextField();
        disc = new JFXTextField();
        netTotal = new Label("Rs. 0");
        
        price.setText(String.valueOf(billProduct.getPrice()));
        price.setDisable(true);
        
        qty.setText(String.valueOf(billProduct.getQuantity()));
        qty.setDisable(true);

        disc.setText(billProduct.getDiscount());
        disc.setDisable(true);
        
        netTotal.setText("Rs. " + billProduct.getNetTotal());

        originalValueOfGrossTotal = 0;
        originalValueOfNetTotal = 0;

        nameStockBox.getStyleClass().add("nameStockBox");
        price.getStyleClass().add("cartRowInput");
        qty.getStyleClass().add("cartRowInput");
        qty.getStyleClass().add("qtyInput");
        disc.getStyleClass().add("cartRowInput");
        disc.getStyleClass().add("discInput");
        netTotal.getStyleClass().add("netTotalLabel");
    }

    private void recalculateTotals(Label grossTotalLabel, Label netTotalLabel, List<BillCartItem> list) 
    {
        double priceValue = price.getText().isEmpty() ? 0 : Double.parseDouble(price.getText());
        double qtyValue = qty.getText().isEmpty() ? 0 : Double.parseDouble(qty.getText());

        if((qtyValue > totalQuantity) && (!this.isReturn.isSelected()))
        {
        	Alert alert = new Alert(AlertType.ERROR);
        	alert.setTitle("Error");
        	alert.setHeaderText("Error in while entering quantity!");
        	alert.setContentText("Your entered quantity is greater than stock available. Please enter the correct quantity");
        	alert.show();
        	qty.setText("");
        }
        else
        {
        	double totalValue = priceValue * qtyValue;
            
            quantity = qty.getText().isEmpty() ? 0 : Integer.parseInt(qty.getText());
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

                double gValue = totalValue - originalValueOfGrossTotal;
                double nValue = totalValue - originalValueOfNetTotal;

                for (BillCartItem item : list) 
                {
                    gValue += item.originalValueOfGrossTotal;
                    nValue += item.originalValueOfNetTotal;
                }

                grossTotalLabel.setText("Rs. " + gValue);
                netTotalLabel.setText("Rs. " + nValue);
                netTotal.setText("Rs. " + totalValue);

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
	public Label getNetTotal() 
    {
        return netTotal;
    }

    @SuppressWarnings("exports")
	public ImageView getDelButton() 
    {
        return delButton;
    }
    
    public double getProductQuantity()
    {
    	return quantity;
    }
    
    public int getStockId()
    {
    	return stockId;
    }
    
    public int getNewQuantity()
    {
    	return newQuantity;
    }
    
    public int getPackSize()
    {
    	return packSize;
    }
    
    public double getPurcahsePrice()
    {
    	return purchasePrice;
    }

    public double getRetailPrice()
    {
    	return retailPrice;
    }
    
    public int getProductId()
    {
    	return productId;
    }
    
    public int getQuantity()
    {
    	return quantity;
    }
    
    public double getPriceValue()
    {
    	return priceValue;
    }
    
    public String getDiscount()
    {
    	return disc.getText();
    }
}
