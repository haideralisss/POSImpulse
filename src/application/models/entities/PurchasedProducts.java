package application.models.entities;

public class PurchasedProducts {
	
	private int productId, purchaseId, quantity, bonus;
	private double unitCost, netTotal;
	private String discount, salesTax, batchNum, productName;
	
	public PurchasedProducts(int productId, String productName, int purchaseId, int quantity, int bonus, double unitCost, String discount, String salesTax, double netTotal, String batchNum)
	{
		this.productId = productId;
		this.purchaseId = purchaseId;
		this.quantity = quantity;
		this.bonus = bonus;
		this.unitCost = unitCost;
		this.discount = discount;
		this.salesTax = salesTax;
		this.netTotal = netTotal;
		this.batchNum = batchNum;
	}

    public int getProductId() {
        return productId;
    }

    public int getPurchaseId() {
        return purchaseId;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getBonus() {
        return bonus;
    }

    public double getUnitCost() {
        return unitCost;
    }

    public String getDiscount() {
        return discount;
    }

    public String getSalesTax() {
        return salesTax;
    }

    public double getNetTotal() {
        return netTotal;
    }

    public String getBatchNum() {
        return batchNum;
    }
    
    public String getProductName() {
    	return productName;
    }
}