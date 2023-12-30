package application.models.entities;

public class BillProducts {
	
	private int productId, billId, quantity;
	private double price, netTotal;
	private String discount;
	
	public BillProducts(int billId, int productId, int quantity, double price, String discount, double netTotal)
	{
		this.billId = billId;
		this.productId = productId;
		this.quantity = quantity;
		this.price = price;
		this.discount = discount;
		this.netTotal = netTotal;
	}

	public int getBillId() {
        return billId;
    }

    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public String getDiscount() {
        return discount;
    }

    public double getNetTotal() {
        return netTotal;
    }
}