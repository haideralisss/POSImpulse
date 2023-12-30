package application.models.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import application.models.entities.Companies;
import application.utils.backendUtils.DatabaseConnection;

public class CompaniesRepo {
	
	public CompaniesRepo()
	{

	}
	
	public ArrayList<Companies> getAllCompanies()
	{
		ArrayList<Companies> companiesList = new ArrayList<Companies>();
		Connection connection = DatabaseConnection.connect();
		try
		{
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM companies");
			ResultSet resultSet = statement.executeQuery();
			int count = 1;
			while(resultSet.next())
			{
				companiesList.add(new Companies(
						resultSet.getInt("id"),
						count,
						resultSet.getString("name"),
						resultSet.getString("contact"),
						resultSet.getString("address")
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
		return companiesList;
	}
	
	public Companies getCompany(int id)
	{
		Companies company = null;
		Connection connection = DatabaseConnection.connect();
		try
		{
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM companies WHERE id = ?");
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				company = new Companies(
						resultSet.getInt("id"),
						0,
						resultSet.getString("name"),
						resultSet.getString("contact"),
						resultSet.getString("address")
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
		return company;
	}
	
	public ArrayList<Companies> addCompany(Companies company)
	{
		Connection connection = DatabaseConnection.connect();
		try
		{
			PreparedStatement statement = connection.prepareStatement("INSERT INTO companies (name, contact, address) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, company.getName());
			statement.setString(2, company.getContact());
			statement.setString(3, company.getAddress());
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
		return getAllCompanies();
	}
	
	public ArrayList<Companies> updateCompany(int id, Companies updatedCompany) 
	{
		Connection connection = DatabaseConnection.connect();
		try
		{
	        PreparedStatement statement = connection.prepareStatement(
	                "UPDATE companies SET name = ?, contact = ?, address = ? WHERE id = ?");
			statement.setString(1, updatedCompany.getName());
			statement.setString(2, updatedCompany.getContact());
			statement.setString(3, updatedCompany.getAddress());
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
	    return getAllCompanies();
	}

	public ArrayList<Companies> deleteCompany(int id) 
	{
		Connection connection = DatabaseConnection.connect();
		try
		{
	        PreparedStatement statement = connection.prepareStatement("DELETE FROM companies WHERE id = ?");
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
	    return getAllCompanies();
	}
}
