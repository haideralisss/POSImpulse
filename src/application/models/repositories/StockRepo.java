package application.models.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.utils.backendUtils.DatabaseConnection;
import application.utils.backendUtils.NumberFormatter;

public class StockRepo {
	
	Connection conn;
	
	public StockRepo()
	{
		conn = DatabaseConnection.connect();
	}
	
	public String fetchStockWorth() 
	{
		String stockWorth = "0";
		double totalAmount = 0;
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
		return stockWorth;
	}

}
