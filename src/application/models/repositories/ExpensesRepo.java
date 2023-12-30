package application.models.repositories;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import application.models.entities.Bills;
import application.models.entities.Expenses;
import application.models.entities.Products;
import application.models.entities.Suppliers;
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
			PreparedStatement statement = connection.prepareStatement("INSERT INTO expenses VALUES (?, ?, ?, ?)");
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
	
	public ArrayList<Expenses> fetchByExpensesName(String expenseName) 
	{
        ArrayList<Expenses> searchData = new ArrayList<>();
        Connection connection = DatabaseConnection.connect();
        int count = 1;
        try
        {
            String query = "SELECT * FROM expenses WHERE name LIKE ? COLLATE NOCASE LIMIT 10";

            try (PreparedStatement statement = connection.prepareStatement(query)) 
            {
                statement.setString(1, "%" + expenseName + "%");

                try (ResultSet resultSet = statement.executeQuery()) 
                {
                    while (resultSet.next()) 
                    {
                        Expenses expense = new Expenses(
                        		count,
                                resultSet.getString("expenseDate"),
                                resultSet.getString("name"),
                                resultSet.getString("description"),
                                resultSet.getDouble("amount")
                        );
                        searchData.add(expense);
                        count++;
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
	            connection.close();
	        } 
	    	catch (SQLException e) 
	    	{
	            e.printStackTrace();
	        }
	    }
        return searchData;
    }
	
	public ArrayList<Expenses> fetchExpensesByDate(LocalDate date) 
	{
		ArrayList<Expenses> searchData = new ArrayList<>();
		Connection connection = DatabaseConnection.connect();
		int count = 1;
        try
        {
            String query = "SELECT * FROM expenses WHERE expenseDate= ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, DateFormatter.formatDate(date));

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) 
            {
            	Expenses expense = new Expenses(
                		count,
                        resultSet.getString("expenseDate"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getDouble("amount")
                );
            	searchData.add(expense);
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
