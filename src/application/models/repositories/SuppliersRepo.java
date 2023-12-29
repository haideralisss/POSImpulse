package application.models.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import application.models.entities.Suppliers;
import application.utils.backendUtils.DatabaseConnection;

public class SuppliersRepo {
	
	Connection conn;
	
	public SuppliersRepo()
	{
		conn = DatabaseConnection.connect();
	}
	
	public ArrayList<Suppliers> getAllSuppliers()
	{
		ArrayList<Suppliers> suppliersList = new ArrayList<Suppliers>();
		try(Connection connection = DatabaseConnection.connect())
		{
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM suppliers");
			ResultSet resultSet = statement.executeQuery();
			int count = 1;
			while(resultSet.next())
			{
				suppliersList.add(new Suppliers(
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
		}
		return suppliersList;
	}
	
	public Suppliers getSupplier(int id)
	{
		Suppliers supplier = null;
		try(Connection connection = DatabaseConnection.connect())
		{
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM suppliers WHERE id = ?");
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				supplier = new Suppliers(
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
		}
		return supplier;
	}
	
	public ArrayList<Suppliers> addSupplier(Suppliers supplier)
	{
		try (Connection connection = DatabaseConnection.connect())
		{
			PreparedStatement statement = connection.prepareStatement("INSERT INTO suppliers VALUES (?, ?, ?)");
			statement.setString(1, supplier.getName());
			statement.setString(2, supplier.getContact());
			statement.setString(3, supplier.getAddress());
			statement.executeUpdate();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return getAllSuppliers();
	}
	
	public ArrayList<Suppliers> updateSuppliers(int id, Suppliers updatedSupplier) 
	{
	    try (Connection connection = DatabaseConnection.connect()) 
	    {
	        PreparedStatement statement = connection.prepareStatement(
	                "UPDATE suppliers SET name = ?, contact = ?, address = ? WHERE id = ?");
			statement.setString(1, updatedSupplier.getName());
			statement.setString(2, updatedSupplier.getContact());
			statement.setString(3, updatedSupplier.getAddress());
	        statement.setInt(4, id);
	        statement.executeUpdate();
	    } 
	    catch (SQLException e)
	    {
	        e.printStackTrace();
	    }
	    return getAllSuppliers();
	}

	public ArrayList<Suppliers> deleteSupplier(int id) 
	{
	    try (Connection connection = DatabaseConnection.connect()) 
	    {
	        PreparedStatement statement = connection.prepareStatement("DELETE FROM suppliers WHERE id = ?");
	        statement.setInt(1, id);

	        statement.executeUpdate();
	    }
	    catch (SQLException e)
	    {
	        e.printStackTrace();
	    }
	    return getAllSuppliers();
	}
}