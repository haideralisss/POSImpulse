package application.models.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import application.screens.billing.BillCartItem;
import application.utils.backendUtils.DatabaseConnection;

public class BillProductsRepo 
{

	public void addNewBillProducts(ArrayList<BillCartItem> items, String billId, String netTotal)
	{
		Connection connection = DatabaseConnection.connect();
		
		try
		{
			for(BillCartItem item : items)
			{
				PreparedStatement statement = connection.prepareStatement("INSERT INTO billProducts (billId, productId, "
						+ "quantity, price, discount, netTotal) "
						+ "VALUES "
						+ "(?, ?, ?, ?, ?, ?)");
				statement.setString(1, billId);
				statement.setString(2, String.valueOf(item.getProductId()));
				statement.setString(3, String.valueOf(item.getQuantity()));
				statement.setString(4, String.valueOf(item.getPriceValue()));
				statement.setString(5, item.getDiscount());
				statement.setString(6, netTotal);
				statement.executeUpdate();
				System.out.println("haider     " + billId);
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try 
			{	
				connection.close();
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
	}
	
}