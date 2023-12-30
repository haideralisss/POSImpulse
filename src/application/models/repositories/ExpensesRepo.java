package application.models.repositories;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import application.models.entities.Expenses;
import application.utils.backendUtils.DatabaseConnection;
import application.utils.backendUtils.DateFormatter;
import application.utils.backendUtils.NumberFormatter;

public class ExpensesRepo {
	
	public ExpensesRepo()
	{

	}

	@SuppressWarnings("deprecation")
	public String fetchMonthExpenses() 
	{
		String monthExpenses = "0";
		double totalAmount = 0;
		Connection connection = DatabaseConnection.connect();
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

            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) 
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
        } finally {
        	try {
	            connection.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
        return monthExpenses;
    }
	
	public ArrayList<Expenses> getAllExpenses()
	{
		ArrayList<Expenses> expensesList = new ArrayList<Expenses>();
		Connection connection = DatabaseConnection.connect();
		try
		{
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM expenses");
			ResultSet resultSet = statement.executeQuery();
			int count = 1;
			while(resultSet.next())
			{
				expensesList.add(new Expenses(
						resultSet.getInt("id"),
						count,
						resultSet.getObject("expenseDate").toString(),
						resultSet.getString("name"),
						resultSet.getString("description"),
						resultSet.getDouble("amount")
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
		return expensesList;
	}
	
	public Expenses getExpense(int id)
	{
		Expenses expense = null;
		Connection connection = DatabaseConnection.connect();
		try
		{
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM expenses WHERE id = ?");
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				expense = new Expenses(
						resultSet.getInt("id"),
						0,
						resultSet.getObject("expenseDate").toString(),
						resultSet.getString("name"),
						resultSet.getString("description"),
						resultSet.getDouble("amount")
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
		return expense;
	}
	
	public ArrayList<Expenses> addExpense(Expenses expense)
	{
		Connection connection = DatabaseConnection.connect();
		try
		{
			PreparedStatement statement = connection.prepareStatement("INSERT INTO expenses (expenseDate, name, description, amount) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			statement.setObject(1, expense.getExpenseDate());
			statement.setString(2, expense.getName());
			statement.setString(3, expense.getDescription());
			statement.setDouble(4, expense.getAmount());
			statement.executeUpdate();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return getAllExpenses();
	}
	
	public ArrayList<Expenses> updateExpense(int id, Expenses updatedExpense) 
	{
		Connection connection = DatabaseConnection.connect();
		try
		{
	        PreparedStatement statement = connection.prepareStatement(
	                "UPDATE expenses SET expenseDate = ?, name = ?, description = ?, amount = ? WHERE id = ?");
	        statement.setObject(1, updatedExpense.getExpenseDate());
			statement.setString(2, updatedExpense.getName());
			statement.setString(3, updatedExpense.getDescription());
			statement.setDouble(4, updatedExpense.getAmount());
	        statement.setInt(5, id);
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
	    return getAllExpenses();
	}

	public ArrayList<Expenses> deleteExpense(int id) 
	{
		Connection connection = DatabaseConnection.connect();
		try
		{
	        PreparedStatement statement = connection.prepareStatement("DELETE FROM expenses WHERE id = ?");
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
	    return getAllExpenses();
	}
}
