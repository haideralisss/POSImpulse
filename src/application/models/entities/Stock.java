package application.models.entities;

public class Stock {
	
	String productName;
	double unitCost;
	int totalQuantity;
	
	public Stock()
	{
		productName = "";
		unitCost = 0;
		totalQuantity = 0;
	}

	public Stock(String productName, double unitCost, int totalQuantity)
	{
		this.productName = productName;
		this.unitCost = unitCost;
		this.totalQuantity = totalQuantity;
	}
	
	public void setProductNameAndTotalQuantity(String productName, int totalQuantity)
	{
		this.productName = productName;
		this.totalQuantity = totalQuantity;
	}
	
	public String getProductName()
	{
		return productName;
	}
	
	public double unitCost()
	{
		return unitCost;
	}
	
	public int getTotalQuantity()
	{
		return totalQuantity;
	}
	
}
