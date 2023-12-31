package application.screens.billing;

import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.Label;
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

    private double originalValueOfGrossTotal, originalValueOfNetTotal;

    @SuppressWarnings("exports")
	public BillCartItem(String name, String stock, Label grossTotalLabel, Label netTotalLabel, List<BillCartItem> list) 
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

        price.setOnKeyReleased(event -> recalculateTotals(grossTotalLabel, netTotalLabel, list));
        qty.setOnKeyReleased(event -> recalculateTotals(grossTotalLabel, netTotalLabel, list));
        disc.setOnKeyReleased(event -> recalculateTotals(grossTotalLabel, netTotalLabel, list));

        delButton = new ImageView();
        Image delIcon = new Image("file:///C:/Users/ALI/eclipse-workspace/POSImpulse/src/assets/deleteIcon.png");
        delButton.setImage(delIcon);
        delButton.setFitWidth(15);
        delButton.setFitHeight(15);

        originalValueOfGrossTotal = 0;
        originalValueOfNetTotal = 0;

        nameStockBox.getStyleClass().add("nameStockBox");
        price.getStyleClass().add("cartRowInput");
        qty.getStyleClass().add("cartRowInput");
        qty.getStyleClass().add("qtyInput");
        disc.getStyleClass().add("cartRowInput");
        disc.getStyleClass().add("discInput");
        netTotal.getStyleClass().add("netTotalLabel");
        delButton.getStyleClass().add("delButtonIcon");
    }

    private void recalculateTotals(Label grossTotalLabel, Label netTotalLabel, List<BillCartItem> list) 
    {
        double priceValue = price.getText().isEmpty() ? 0 : Double.parseDouble(price.getText());
        double qtyValue = qty.getText().isEmpty() ? 0 : Double.parseDouble(qty.getText());

        double totalValue = priceValue * qtyValue;

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
}
