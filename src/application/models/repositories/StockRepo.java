package application.models.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import application.models.entities.Stock;
import application.utils.backendUtils.DatabaseConnection;
import application.utils.backendUtils.NumberFormatter;

public class StockRepo {
	
	public String fetchStockWorth() 
	{
		String stockWorth = "0";
		double totalAmount = 0;
		Connection conn = DatabaseConnection.connect();
		try
        {
            PreparedStatement preparedStatement = conn.prepareStatement(
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
		}
	    finally
	    {
	    	try 
	    	{
				conn.close();
			} 
	    	catch (SQLException e) 
	    	{
				e.printStackTrace();
			}
	    }
		return stockWorth;
	}
	
	public ArrayList<Stock> fetchProductByLowStock() 
	{
        ArrayList<Stock> productsData = new ArrayList<>();
        Connection conn = DatabaseConnection.connect();
        try 
        {
            String query = "SELECT " +
                    "products.name AS productId, " +
                    "SUM(stock.totalQuantity) AS totalQuantity " +
                    "FROM stock " +
                    "JOIN products ON stock.productId = products.id " +
                    "GROUP BY stock.productId " + 
                    "HAVING totalQuantity <= IFNULL(products.packSize, totalQuantity)";
        	

            try (PreparedStatement statement = conn.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) 
            {
                while (resultSet.next()) 
                {
                	Stock stock = new Stock();
                    String productId = resultSet.getString("productId");
                    int totalQuantity = resultSet.getInt("totalQuantity");
                    stock.setProductNameAndTotalQuantity(productId, totalQuantity);
                    productsData.add(stock);
                }
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
	    finally
	    {
	    	try 
	    	{
				conn.close();
			} 
	    	catch (SQLException e) 
	    	{
				e.printStackTrace();
			}
	    }
        return productsData;
    }

}
