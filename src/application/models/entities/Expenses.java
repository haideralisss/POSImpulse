package application.models.entities;

public class Expenses {
	
	String name, description, expenseDate;
	double amount;

	public Expenses(String name, String description, double amount)
	{
		this.name = name;
		this.description = description;
		this.amount = amount;
	}
	
}
