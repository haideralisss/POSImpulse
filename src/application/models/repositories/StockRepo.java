package application.models.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import application.models.entities.Stock;
import application.models.entities.Suppliers;
import application.utils.backendUtils.DatabaseConnection;
import application.utils.backendUtils.NumberFormatter;

public class StockRepo {
	
private Connection connection;
	
	public StockRepo()
	{
		connection = DatabaseConnection.connect();
	}
	
	public String fetchStockWorth() 
	{
		String stockWorth = "0";
		double totalAmount = 0;
	    try
	    {
            PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT SUM((p.purchasePrice / p.packSize) * s.totalQuantity) AS totalAmount " +
                             "FROM stock s " +
                             "JOIN products p ON s.productId = p.id");

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) 
            {
                totalAmount = resultSet.getDouble("totalAmount");
                stockWorth = NumberFormatter.format(totalAmount);
            }
        } 
		catch (SQLException e) 
		{
			e.printStackTrace();
		} finally {
	        try {
	            connection.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
		return stockWorth;
	}
	
	public ArrayList<Stock> getAllStock()
	{
		ArrayList<Stock> stockList = new ArrayList<Stock>();
		try
	    {
			String query = "SELECT s.*, p.name AS productName FROM stock s " +
                    "INNER JOIN products p ON s.productId = p.id";
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			int count = 1;
			while(resultSet.next())
			{
				stockList.add(new Stock(
						count,
						resultSet.getInt("productId"),
						resultSet.getString("productName"),
						resultSet.getDouble("unitCost"),
						resultSet.getInt("totalQuantity")
					));
				count++;
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		} finally {
			try {
	            connection.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
		return stockList;
	}
	
	public Stock getStock(int id)
	{
		Stock stock = null;
		try
	    {
			String query = "SELECT s.*, p.name AS productName FROM stock s " +
                    "INNER JOIN products p ON s.productId = p.id " +
					"WHERE id = ?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				stock = new Stock(
						0,
						resultSet.getInt("productId"),
						resultSet.getString("productName"),
						resultSet.getDouble("unitCost"),
						resultSet.getInt("totalQuantity")
					);
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		} finally {
			try {
	            connection.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
		return stock;
	}
	
	public ArrayList<Stock> addStock(Stock stock)
	{
		try
	    {
			PreparedStatement statement = connection.prepareStatement("INSERT INTO stock VALUES (?, ?, ?)");
			statement.setInt(1, stock.getProductId());
			statement.setDouble(2, stock.getUnitCost());
			statement.setInt(3, stock.getTotalQuantity());
			statement.executeUpdate();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		} finally {
			try {
	            connection.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
		return getAllStock();
	}
	
	public ArrayList<Stock> updateStock(int id, Stock updatedStock) 
	{
		try
	    {
	        PreparedStatement statement = connection.prepareStatement(
	                "UPDATE stock SET productId = ?, unitCost = ?, totalQuantity = ? WHERE id = ?");
	        statement.setInt(1, updatedStock.getProductId());
			statement.setDouble(2, updatedStock.getUnitCost());
			statement.setInt(3, updatedStock.getTotalQuantity());
	        statement.setInt(4, id);
	        statement.executeUpdate();
	    } 
	    catch (SQLException e)
	    {
	        e.printStackTrace();
	    } finally {
			try {
	            connection.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return getAllStock();
	}

	public ArrayList<Stock> deleteStock(int id) 
	{
		try
	    {
	        PreparedStatement statement = connection.prepareStatement("DELETE FROM stock WHERE id = ?");
	        statement.setInt(1, id);

	        statement.executeUpdate();
	    }
	    catch (SQLException e)
	    {
	        e.printStackTrace();
	    } finally {
			try {
	            connection.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return getAllStock();
	}

}
