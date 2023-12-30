package application.models.repositories;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.time.format.DateTimeFormatter;

import application.utils.backendUtils.DatabaseConnection;
import application.models.entities.Bills;
import application.models.entities.Products;
import application.utils.backendUtils.*;

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
	
	public ArrayList<Bills> getAllBills()
	{
		ArrayList<Bills> billsList = new ArrayList<Bills>();
		
		try
		{
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM bills");
			ResultSet resultSet = statement.executeQuery();
			int count = 1;
			while(resultSet.next())
			{
				billsList.add(new Bills(
						count,
						resultSet.getString("customerName"),
						resultSet.getInt("invoiceNum"),
						resultSet.getObject("billDate").toString(),
						resultSet.getDouble("grossTotal"),
						resultSet.getString("discount"),
						resultSet.getString("salesTax"),
						resultSet.getDouble("netTotal"),
						resultSet.getDouble("amountPaid"),
						resultSet.getString("shift"),
						resultSet.getBoolean("isCredit"),
						resultSet.getBoolean("isReturn"),
						resultSet.getDouble("profit")
					));
				count++;
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally 
	    {
			try {
	            connection.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
		return billsList;
	}
	
	public Bills getBill(int id) {
		Bills bill = null;
		
		try
		{
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM bills WHERE id = ?");
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			int count = 1;
			while(resultSet.next())
			{
				bill = new Bills(
						count,
						resultSet.getString("customerName"),
						resultSet.getInt("invoiceNum"),
						resultSet.getObject("billDate").toString(),
						resultSet.getDouble("grossTotal"),
						resultSet.getString("discount"),
						resultSet.getString("salesTax"),
						resultSet.getDouble("netTotal"),
						resultSet.getDouble("amountPaid"),
						resultSet.getString("shift"),
						resultSet.getBoolean("isCredit"),
						resultSet.getBoolean("isReturn"),
						resultSet.getDouble("profit")
					);
				count++;
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally 
	    {
			try {
	            connection.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
		return bill;
	}
	
	public ArrayList<Bills> addBill(Bills bill)
	{
		try
		{
			PreparedStatement statement = connection.prepareStatement("INSERT INTO bills VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			statement.setString(1, bill.getCustomerName());
			statement.setInt(2, bill.getInvoiceNum());
			statement.setObject(3, bill.getBillDate());
			statement.setDouble(4, bill.getGrossTotal());
			statement.setString(5, bill.getDiscount());
			statement.setString(6, bill.getSalesTax());
			statement.setDouble(7, bill.getNetTotal());
			statement.setDouble(8, bill.getAmountPaid());
			statement.setString(9, bill.getShift());
			statement.setBoolean(10, bill.getIsCredit());
			statement.setBoolean(11, bill.getIsReturn());
			statement.setDouble(12, bill.getProfit());
			statement.executeUpdate();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally 
	    {
			try {
	            connection.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
		return getAllBills();
	}
	
	public ArrayList<Bills> updateBill(int id, Bills updatedBill) 
	{
		try
		{
	        PreparedStatement statement = connection.prepareStatement(
	                "UPDATE bills SET customerName = ?, invoiceNum = ?, billDate = ?, grossTotal = ?, discount = ?, salesTax = ?, netTotal = ?, amountPaid = ?, shift = ?, isCredit = ?, isReturn = ?, profit = ? WHERE id = ?");
	        statement.setString(1, updatedBill.getCustomerName());
			statement.setInt(2, updatedBill.getInvoiceNum());
			statement.setObject(3, updatedBill.getBillDate());
			statement.setDouble(4, updatedBill.getGrossTotal());
			statement.setString(5, updatedBill.getDiscount());
			statement.setString(6, updatedBill.getSalesTax());
			statement.setDouble(7, updatedBill.getNetTotal());
			statement.setDouble(8, updatedBill.getAmountPaid());
			statement.setString(9, updatedBill.getShift());
			statement.setBoolean(10, updatedBill.getIsCredit());
			statement.setBoolean(11, updatedBill.getIsReturn());
			statement.setDouble(12, updatedBill.getProfit());
			statement.setInt(13, id);
	        statement.executeUpdate();
	    } 
	    catch (SQLException e)
	    {
	        e.printStackTrace();
	    }
	    finally 
	    {
	    	try {
	            connection.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return getAllBills();
	}

	public ArrayList<Bills> deleteBills(int id) 
	{
		try
		{
	    	
	        PreparedStatement statement = connection.prepareStatement("DELETE FROM bills WHERE id = ?");
	        statement.setInt(1, id);

	        statement.executeUpdate();
	    }
	    catch (SQLException e)
	    {
	        e.printStackTrace();
	    }
	    finally 
	    {
	    	try {
	            connection.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return getAllBills();
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