package application.models.repositories;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.utils.backendUtils.DatabaseConnection;
import application.utils.backendUtils.DateFormatter;
import application.utils.backendUtils.NumberFormatter;

public class ExpensesRepo 
{

	public String fetchMonthExpenses() 
	{
		String monthExpenses = "0";
		double totalAmount = 0;
		Connection conn = DatabaseConnection.connect();
        try
        {
        	java.util.Date now = new java.util.Date();

            // Set current month's first and last day
            int currentMonth = now.getMonth() + 1; // SQLite months are 1-based
            int currentYear = now.getYear() + 1900; // Adjust for year offset

            Date sqlFirstDayOfMonth = Date.valueOf(currentYear + "-" + currentMonth + "-01");
            Date sqlLastDayOfMonth = Date.valueOf(currentYear + "-" + currentMonth + "-31");
            String sqlQuery = "SELECT SUM(amount) AS totalAmount FROM expenses " +
                              "WHERE expenseDate>= ? AND expenseDate<= ?";

            try (PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery)) 
            {

            	preparedStatement.setString(1, DateFormatter.formatSqlDate(sqlFirstDayOfMonth));
            	preparedStatement.setString(2, DateFormatter.formatSqlDate(sqlLastDayOfMonth));

                try (ResultSet resultSet = preparedStatement.executeQuery()) 
                {
                    if (resultSet.next()) 
                    {
                        totalAmount = resultSet.getDouble("totalAmount");
                        monthExpenses = NumberFormatter.format(totalAmount);
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
        return monthExpenses;
    }
}
