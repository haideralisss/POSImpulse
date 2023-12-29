package application.models.repositories;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import application.utils.backendUtils.DatabaseConnection;
import application.utils.backendUtils.*;

public class BillsRepo 
{
	
	Connection conn;
	
	public BillsRepo()
	{
		conn = DatabaseConnection.connect();
	}
	
	public String fetchTodaySales()
	{
		String todaySales = "0";
		double totalAmount = 0.0;
		
	    try
	    {
	        LocalDate now = LocalDate.now();
	        
	        String query = "SELECT COALESCE(SUM(CASE WHEN isReturn = 0 AND isCredit = 0 THEN amountPaid ELSE 0 END), 0) - " +
	                       "COALESCE(SUM(CASE WHEN isReturn = 1 THEN amountPaid ELSE 0 END), 0) AS totalAmount " +
	                       "FROM bills WHERE billDate = ? AND isCredit != 1";

	        try (PreparedStatement statement = conn.prepareStatement(query)) 
	        {
	            statement.setString(1, DateFormatter.formatDate(now));

	            try (ResultSet resultSet = statement.executeQuery()) 
	            {
	                if (resultSet.next()) 
	                {
	                    totalAmount = resultSet.getDouble("totalAmount");
	                    todaySales = NumberFormatter.format(totalAmount);
	                }
	            }
	        }
	    } 
	    catch (SQLException e) 
	    {
	        e.printStackTrace();
	    }
	    return todaySales;
	}
	
	public String fetchMonthSales() 
	{
		String monthSales = "0";
        double totalAmount = 0.0;
        try (Connection connection = DatabaseConnection.connect()) 
        {
            // Get the current date
            java.util.Date now = new java.util.Date();

            // Set current month's first and last day
            int currentMonth = now.getMonth() + 1; // SQLite months are 1-based
            int currentYear = now.getYear() + 1900; // Adjust for year offset

            Date sqlFirstDayOfMonth = Date.valueOf(currentYear + "-" + currentMonth + "-01");
            Date sqlLastDayOfMonth = Date.valueOf(currentYear + "-" + currentMonth + "-31");

            // Execute the SQL query
            String query = "SELECT " +
                    "COALESCE(SUM(CASE WHEN isReturn = 0 THEN amountPaid ELSE 0 END), 0) - " +
                    "COALESCE(SUM(CASE WHEN isReturn = 1 THEN amountPaid ELSE 0 END), 0) AS totalAmount " +
                    "FROM bills " +
                    "WHERE billDate >= ? AND billDate <= ? AND isCredit != 1";

            try (PreparedStatement statement = connection.prepareStatement(query)) 
            {
                statement.setString(1, DateFormatter.formatSqlDate(sqlFirstDayOfMonth));
                statement.setString(2, DateFormatter.formatSqlDate(sqlLastDayOfMonth));

                try (ResultSet resultSet = statement.executeQuery()) 
                {
                    if (resultSet.next()) 
                    {
                        totalAmount = resultSet.getDouble("totalAmount");
                        monthSales = NumberFormatter.format(totalAmount);
                    }
                }
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        return monthSales;
    }
}
