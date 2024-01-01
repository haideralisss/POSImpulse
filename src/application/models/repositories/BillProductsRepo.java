package application.models.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import application.models.entities.BillProducts;
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
	
	public ArrayList<BillProducts> fetchBillProducts(String billId)
	{
		Connection connection = DatabaseConnection.connect();
		ArrayList<BillProducts> searchData = new ArrayList<>();
		try
		{
			PreparedStatement statement = connection.prepareStatement(
				    "SELECT billProducts.*, products.name " +
				    "FROM billProducts " +
				    "JOIN products ON billProducts.productId = products.id " +
				    "JOIN stock ON billProducts.productId = stock.productId " +
				    "WHERE billProducts.billId = ?");

			statement.setString(1, billId);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				BillProducts billProduct = new BillProducts(
						resultSet.getInt("billId"),
						resultSet.getString("name"),
						resultSet.getInt("quantity"),
						resultSet.getDouble("price"),
						resultSet.getString("discount"),
						resultSet.getDouble("netTotal")
						
				);
				searchData.add(billProduct);
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
		return searchData;
	}
	
}