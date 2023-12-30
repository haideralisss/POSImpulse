package application.models.repositories;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import application.utils.backendUtils.DatabaseConnection;
import application.utils.backendUtils.*;

import application.models.entities.Bills;

public class BillsRepo 
{
	
	public String fetchTodaySales()
	{
		String todaySales = "0";
		double totalAmount = 0.0;
		Connection conn = DatabaseConnection.connect();
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
	    return todaySales;
	}
	
	public String fetchMonthSales() 
	{
		String monthSales = "0";
        double totalAmount = 0.0;
        Connection conn = DatabaseConnection.connect();
        try
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

            try (PreparedStatement statement = conn.prepareStatement(query)) 
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
        return monthSales;
    }
	
	public ArrayList<Bills> fetchMonthSalesReport() 
	{
        ArrayList<Bills> monthSalesList = new ArrayList<>();
        Connection conn = DatabaseConnection.connect();
        try
        {
        	java.util.Date now = new java.util.Date();

            // Set current month's first and last day
            int currentMonth = now.getMonth() + 1; // SQLite months are 1-based
            int currentYear = now.getYear() + 1900; // Adjust for year offset

            Date sqlFirstDayOfMonth = Date.valueOf(currentYear + "-" + currentMonth + "-01");
            Date sqlLastDayOfMonth = Date.valueOf(currentYear + "-" + currentMonth + "-31");
        	
             PreparedStatement preparedStatement = conn.prepareStatement(
                     "SELECT strftime('%Y-%m-%d', billDate) AS date, " +
                             "SUM(CASE WHEN isReturn = 0 THEN amountPaid ELSE 0 END) AS totalAmount, " +
                             "SUM(CASE WHEN isReturn = 1 THEN amountPaid ELSE 0 END) AS totalSubtractedAmount " +
                             "FROM bills " +
                             "WHERE billDate>=? AND billDate<= ? AND isCredit != 1 " +
                             "GROUP BY strftime('%Y-%m-%d', billDate) " +
                             "ORDER BY date");

             preparedStatement.setString(1, DateFormatter.formatSqlDate(sqlFirstDayOfMonth));
             preparedStatement.setString(2, DateFormatter.formatSqlDate(sqlLastDayOfMonth));

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) 
            {
            	Bills bills = new Bills();
                String date = resultSet.getString("date");
                double totalAmount = resultSet.getDouble("totalAmount");
                double totalSubtractedAmount = resultSet.getDouble("totalSubtractedAmount");
                double netAmount = totalAmount - totalSubtractedAmount;
                bills.setBillDateAndAmountPaid(date, netAmount);
                
                monthSalesList.add(bills);
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
        return monthSalesList;
    }
	
	public ArrayList<Bills> fetchDailyProfit() 
	{
        ArrayList<Bills> dailyProfit = new ArrayList<>();
        Connection conn = DatabaseConnection.connect();
        try 
        {
        	java.util.Date now = new java.util.Date();

            // Set current month's first and last day
            int currentMonth = now.getMonth() + 1; // SQLite months are 1-based
            int currentYear = now.getYear() + 1900; // Adjust for year offset

            Date sqlFirstDayOfMonth = Date.valueOf(currentYear + "-" + currentMonth + "-01");
            Date sqlLastDayOfMonth = Date.valueOf(currentYear + "-" + currentMonth + "-31");
            
            try (PreparedStatement preparedStatement = conn.prepareStatement(
                    "SELECT billDate, " +
                            "SUM(CASE WHEN isReturn = 0 THEN profit ELSE 0 END) - " +
                            "SUM(CASE WHEN isReturn = 1 THEN profit ELSE 0 END) AS totalProfit " +
                            "FROM bills " +
                            "WHERE billDate >= ? AND billDate<= ? AND isCredit != 1 " +
                            "GROUP BY billDate " +
                            "ORDER BY billDate ASC")) 
            {

                preparedStatement.setString(1, DateFormatter.formatSqlDate(sqlFirstDayOfMonth));
                preparedStatement.setString(2, DateFormatter.formatSqlDate(sqlLastDayOfMonth));

                try (ResultSet resultSet = preparedStatement.executeQuery()) 
                {
                    while (resultSet.next()) 
                    {
                    	Bills bill = new Bills();
                        String date = resultSet.getString("billDate");
                        double totalProfit = resultSet.getDouble("totalProfit");

                        bill.setBillDateAndProfit(date, totalProfit);
                        dailyProfit.add(bill);
                    }
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
        return dailyProfit;
    }
}
