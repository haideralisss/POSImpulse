package application.models.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import application.models.entities.Accounts;
import application.utils.backendUtils.*;

public class AccountsRepo {
	
	Connection conn;
	
	public AccountsRepo()
	{
		conn = DatabaseConnection.connect();
	}
	
	public ArrayList<Accounts> getAllAccounts()
	{
		ArrayList<Accounts> accountsList = new ArrayList<Accounts>();
		try(Connection connection = DatabaseConnection.connect())
		{
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM accounts");
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				accountsList.add(new Accounts(
						resultSet.getString("username"),
						resultSet.getString("fullname"),
						resultSet.getString("phone"),
						resultSet.getString("password"),
						resultSet.getBoolean("isAdmin")
						));
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return accountsList;
	}
	
	public ArrayList<Accounts> addAccount(Accounts account)
	{
		try (Connection connection = DatabaseConnection.connect())
		{
			PreparedStatement statement = connection.prepareStatement("INSERT INTO accounts VALUES (?, ?, ?, ?, ?)");
			statement.setString(1, account.getUserName());
			statement.setString(2, account.getFullName());
			statement.setString(3, account.getPhone());
			statement.setString(4, account.getPassword());
			statement.setBoolean(5, account.getIsAdmin());
			statement.executeUpdate();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return getAllAccounts();
	}
	
	public ArrayList<Accounts> updateAccount(int id, Accounts updatedAccount) 
	{
	    try (Connection connection = DatabaseConnection.connect()) 
	    {
	        PreparedStatement statement = connection.prepareStatement(
	                "UPDATE accounts SET fullname = ?, phone = ?, password = ?, isAdmin = ? WHERE id = ?");
	        statement.setString(1, updatedAccount.getFullName());
	        statement.setString(2, updatedAccount.getPhone());
	        statement.setString(3, updatedAccount.getPassword());
	        statement.setBoolean(4, updatedAccount.getIsAdmin());
	        statement.setInt(5, id);
	        statement.executeUpdate();
	    } 
	    catch (SQLException e)
	    {
	        e.printStackTrace();
	    }
	    return getAllAccounts();
	}

	public ArrayList<Accounts> deleteAccount(int id) 
	{
	    try (Connection connection = DatabaseConnection.connect()) 
	    {
	        PreparedStatement statement = connection.prepareStatement("DELETE FROM accounts WHERE id = ?");
	        statement.setInt(1, id);

	        statement.executeUpdate();
	    }
	    catch (SQLException e)
	    {
	        e.printStackTrace();
	    }
	    return getAllAccounts();
	}
	
	public boolean verifyUser(String username, String password) 
	{
	    boolean checkFlag = true;
	    try 
	    {
	        String query = "SELECT username, password FROM accounts WHERE username=? AND password=?";
	        PreparedStatement statement = conn.prepareStatement(query);
	        
	        statement.setString(1, username);
	        statement.setString(2, password);
	        
	        ResultSet resultSet = statement.executeQuery();
	        
	        if (!resultSet.isBeforeFirst() && resultSet.getRow() == 0) 
	        {
	            checkFlag = false;
	        }
	    } 
	    catch (SQLException e) 
	    {
	        e.printStackTrace();
	    }
	    return checkFlag;
	}
}