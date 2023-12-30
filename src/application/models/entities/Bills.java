package application.models.entities;

public class Bills {
	
	String customerName, billDate, discount, salesTax, shift;
	int invoiceNum;
	double profit, grossTotal, netTotal, amountPaid;
	boolean isCredit, isReturn;
	
	public void setStringData(String customerName, String billDate, String discount, String salesTax, String shift) 
	{
		this.customerName = customerName;
		this.billDate = billDate;
		this.discount = discount;
		this.salesTax = salesTax;
		this.shift = shift;
	}
	
	public void setIntegerData(int invoiceNum)
	{
		this.invoiceNum = invoiceNum;
	}
	
	public void setDoubleData(double profit, double grossTotal, double netTotal, double amountPaid)
	{
		this.profit = profit;
		this.grossTotal = grossTotal;
		this.netTotal = netTotal;
		this.amountPaid = amountPaid;
	}
	
	public void setBooleanData(boolean isCredit, boolean isReturn)
	{
		this.isCredit = isCredit;
		this.isReturn = isReturn;
	}
	
	public void setBillDateAndAmountPaid(String billDate, double amountPaid)
	{
		this.billDate = billDate;
		this.amountPaid = amountPaid;
	}
	
	public void setBillDateAndProfit(String billDate, double profit)
	{
		this.billDate = billDate;
		this.profit = profit;
	}
	
	public String getBillDate()
	{
		return billDate;
	}
	
	public double getAmountPaid()
	{
		return amountPaid;
	}
	
	public double getProfit()
	{
		return profit;
	}
}
