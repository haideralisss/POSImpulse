package application.models.entities;

public class Stock {
	
	String productName;
	double unitCost;
	int totalQuantity;

	Stock(String productName, double unitCost, int totalQuantity)
	{
		this.productName = productName;
		this.unitCost = unitCost;
		this.totalQuantity = totalQuantity;
	}
	
}
